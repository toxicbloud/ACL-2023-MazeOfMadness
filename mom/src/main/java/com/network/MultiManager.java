package com.network;

import com.engine.Window;
import com.game.Entity;
import com.game.Game;
import com.game.Maze;
import com.game.Score;
import com.game.WorldItem;
import com.game.controllers.NetworkEntityController;
import com.game.generators.MazeFactory;
import com.game.generators.MonsterSpawner;
import com.game.generators.PotionSpawner;
import com.game.generators.WeaponSpawner;
import com.game.monsters.Monster;
import com.game.tiles.Next;
import com.game.tiles.NextNetwork;
import com.game.tiles.Tile;

/**
 * Multiplayer manager.
 */
public class MultiManager {
    /** Register response validator. */
    private static final DataValidator REGISTER_RQT_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.REGISTER_RQT;
    };

    /** Maze data request validator. */
    private static final DataValidator MAZE_DATA_RQT_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.MAZE_RQT;
    };

    /** Register response validator. */
    private static final DataValidator REGISTER_RSP_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.REGISTER_RSP;
    };

    /** Maze length response validator. */
    private static final DataValidator MAZE_LGH_RSP_VALIDATOR = (data, infos) -> {
        return data.length > 2 && data[0] == NetworkDialogs.MAZE_LGH;
    };

    /** Maze data response validator. */
    private static final DataValidator MAZE_DATA_RSP_VALIDATOR = (data, infos) -> {
        return data.length > 2 && data[0] == NetworkDialogs.MAZE_ADD;
    };

    /** Game start response validator. */
    private static final DataValidator GAME_STR_RSP_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.GAME_STR;
    };

    /** Client ready response validator. */
    private static final DataValidator GAME_RDY_RSP_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.GAME_RDY;
    };

    /** Network server. */
    private NetworkServer server;

    /** Network client. */
    private NetworkClient client;

    /** Network game maze. */
    private Maze maze;

    /** Network maze builder. */
    private NetworkMazeBuilder builder;

    /** Is the client listening for data. */
    private boolean listeningForData;

    /** Log listener for message logging. */
    private LogListener logListener;

    /** Is the server game started. */
    private boolean serverGameStarted;

    /** Number of clients ready for game. */
    private int nbClientsReady;

    /** Number of clients having all required data. */
    private int nbClientsOk;

    /**
     * Create a new MultiManager.
     */
    public MultiManager() {
        this.server = null;
        this.client = null;
    }

    /**
     * Update the network server or client.
     */
    public void update() {
        if (this.server != null) {
            this.server.update();
        }
        if (this.client != null) {
            this.client.update();
        }
    }

    /**
     * Create a new server.
     * @param ip Server ip.
     * @param port Server port.
     * @param score Score.
     */
    public void createServer(String ip, int port, Score score) {
        this.server = new NetworkServer(port);
        maze = MazeFactory.createMaze();
        Tile[] tiles = maze.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] instanceof Next) {
                tiles[i] = new NextNetwork(tiles[i].getPosition(), this.server);
            }
        }
        maze.setTiles(tiles);
        MonsterSpawner.spawnMonsters(this.maze);
        PotionSpawner.spawnPotion(this.maze);
        serverGameStarted = false;
        this.setupServerBehaviours(true, score);
    }

    /**
     * Create a new server.
     * @param ip Server ip.
     * @param port Server port.
     */
    public void createServer(String ip, int port) {
        this.server = new NetworkServer(port);
        maze = MazeFactory.createMaze();
        Tile[] tiles = maze.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] instanceof Next) {
                tiles[i] = new NextNetwork(tiles[i].getPosition(), this.server);
            }
        }
        maze.setTiles(tiles);
        MonsterSpawner.spawnMonsters(this.maze);
        PotionSpawner.spawnPotion(this.maze);
        WeaponSpawner.spawnWeapons(this.maze);
        serverGameStarted = false;
        this.setupServerBehaviours(false, null);
    }

    /**
     * Create a new client.
     * @param ip Server ip.
     * @param port Server port.
     */
    public void createClient(String ip, int port) {
        this.client = new NetworkClient(ip, port);
        this.setupClientBehaviours();
        this.registerToServer();
    }

    /**
     * Start the server game.
     */
    public void startServerGame() {
        serverGameStarted = true;
        this.createAndLaunchGame(new Score());
    }

    /**
     * Start the server game.
     * @param score Score.
     */
    public void startServerGame(Score score) {
        serverGameStarted = true;
        this.createAndLaunchGame(score);
    }

    /**
     * Registers to the server as new client.
     * @return True if the registration was sent, false otherwise.
     */
    private boolean registerToServer() {
        log("Registering ...", false);
        if (!this.client.sendData(new byte[]{NetworkDialogs.REGISTER_RQT})) {
            log("Registering ... Error", true);
        }
        return true;
    }

    /**
     * Setup the client behaviours.
     * (add listeners, validators, etc.)
     */
    private void setupClientBehaviours() {
        builder = new NetworkMazeBuilder(this.client);

        this.client.when(
            REGISTER_RSP_VALIDATOR,
            (data, infos) -> {
                int id = NetworkDialogs.getIntValue(data, 1);
                client.setId(id);
                System.out.println("Client id: " + id);
                log("Registering ... OK", true);
                log("Getting maze data ...", false);
                if (!this.client.sendData(new byte[]{NetworkDialogs.MAZE_RQT})) {
                    log("Getting maze data ... Error", true);
                } else {
                    listeningForData = true;
                }
                return true;
            }
        );

        this.client.when(
            MAZE_LGH_RSP_VALIDATOR,
            (data, infos) -> {
                builder.setDataLength(NetworkDialogs.getIntValue(data, 1));
                return true;
            }
        );
        this.client.when(
            MAZE_DATA_RSP_VALIDATOR,
            (data, infos) -> {
                int entityId = NetworkDialogs.getIntValue(data, 1);
                Entity e = NetworkDialogs.getEntityFromData(data, 1 + 2);
                if (e == null) {
                    System.out.println("Error getting entity from data");
                    return true;
                }
                e.setId(entityId);
                builder.addEntity(e);
                int nbEntities = builder.getEntitiesNumber();
                int totalNb = builder.getDataLength();
                int percent = nbEntities * NetworkDialogs.HUNDRED / totalNb;
                log("Getting maze data ... " + percent + "%", true);

                if (nbEntities == totalNb) {
                    listeningForData = false;
                    log("Getting maze data ... OK", true);
                    log("Waiting for game to start ...", false);
                    client.sendData(new byte[]{NetworkDialogs.GAME_OK});
                    return true;
                }
                return false;
            }
        );

        this.client.when(
            GAME_STR_RSP_VALIDATOR,
            (data, infos) -> {
                maze = builder.build();
                for (Monster m : maze.getMonsters()) {
                    new NetworkEntityController(m, client);
                }
                for (WorldItem i : maze.getItems()) {
                    new NetworkEntityController(i, client);
                }
                Game.getInstance().loadFrom(maze);
                Window.getInstance().setScene(new GameSceneClient(maze, client));
                return true;
            }
        );
    }

    /**
     * Setup the server behaviours.
     * (add listeners, validators, etc.)
     * @param launchWhenReady Launch the game when all clients are ready.
     * @param score Score.
     */
    private void setupServerBehaviours(boolean launchWhenReady, Score score) {
        if (this.server == null) {
            return;
        }

        // Register request
        this.server.when(
            REGISTER_RQT_VALIDATOR,
            (data, infos) -> {
                byte[] res = new byte[]{NetworkDialogs.REGISTER_RSP, 0, 0};
                int clientId = server.getNextClientId();
                infos.setId(clientId);
                NetworkDialogs.encodeIntValue(clientId, res, 1);
                server.sendData(res, infos);
                return true;
            }
        );

        // Maze data request
        this.server.when(
            MAZE_DATA_RQT_VALIDATOR,
            (data, infos) -> {
                if (serverGameStarted) {
                    return true;
                }

                int mazeDataLength =
                    maze.getTiles().length
                    + maze.getMonsters().length
                    + maze.getItems().length;
                byte[] mazeDataNbPaquets = new byte[]{NetworkDialogs.MAZE_LGH, 0, 0};
                NetworkDialogs.encodeIntValue(mazeDataLength, mazeDataNbPaquets, 1);
                server.sendData(mazeDataNbPaquets, infos);

                for (Tile t : maze.getTiles()) {
                    byte[] tileData = NetworkDialogs.encodeTileValue(t, 1 + 2);
                    tileData[0] = NetworkDialogs.MAZE_ADD;
                    NetworkDialogs.encodeIntValue(t.getId(), tileData, 1);
                    server.sendData(tileData, infos);
                }
                for (Monster m : maze.getMonsters()) {
                    byte[] monsterData = NetworkDialogs.encodeMonsterValue(m, 1 + 2);
                    monsterData[0] = NetworkDialogs.MAZE_ADD;
                    NetworkDialogs.encodeIntValue(m.getId(), monsterData, 1);
                    server.sendData(monsterData, infos);
                }
                for (WorldItem i : maze.getItems()) {
                    byte[] itemData = NetworkDialogs.encodeItemValue(i, 1 + 2);
                    itemData[0] = NetworkDialogs.MAZE_ADD;
                    NetworkDialogs.encodeIntValue(i.getId(), itemData, 1);
                    server.sendData(itemData, infos);
                }
                return false;
            }
        );

        if (launchWhenReady) {
            nbClientsOk = 0;
            this.server.when((data, infos) -> {
                return data[0] == NetworkDialogs.GAME_OK;
            }, (data, infos) -> {
                if (++nbClientsOk >= server.getClients().size()) {
                    this.startServerGame(score);
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * Create and start a network game (as server).
     * @param score Score.
     */
    private void createAndLaunchGame(Score score) {
        if (this.server == null) {
            return;
        }

        Game.getInstance().loadFrom(maze);
        Game.getInstance().getScore().apply(score);
        server.sendData(new byte[]{NetworkDialogs.GAME_STR});

        nbClientsReady = 0;
        server.when(
            GAME_RDY_RSP_VALIDATOR,
            (data, infos) -> {
                if (++nbClientsReady < server.getClients().size()) {
                    return false;
                }

                byte[] scoreData = NetworkDialogs.encodeScoreValue(Game.getInstance().getScore(), 1);
                scoreData[0] = NetworkDialogs.GAME_SCR;
                server.sendData(scoreData);
                Window.getInstance().setScene(new GameSceneServer(maze, server));
                return true;
            }
        );
    }

    /**
     * Log a message.
     * @param msg Message to log.
     * @param clearLastLine Clear the last line.
     */
    private void log(String msg, boolean clearLastLine) {
        if (this.logListener != null) {
            this.logListener.onLogging(msg, clearLastLine);
        }
    }

    /**
     * Set the log listener.
     * @param listener Log listener.
     */
    public void addLogListener(LogListener listener) {
        this.logListener = listener;
    }
}
