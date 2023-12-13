package com.network;

import com.engine.Window;
import com.game.Entity;
import com.game.Game;
import com.game.Maze;
import com.game.WorldItem;
import com.game.generators.MazeFactory;
import com.game.monsters.Monster;
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

    /** Game start time to lte ll clients spawn map and all before continuing. */
    private static final int GAME_WAIT_TIME = 500;

    /** Network server. */
    private NetworkServer server;

    /** Network client. */
    private NetworkClient client;

    /** Network game maze. */
    private Maze maze;

    /** Network maze builder. */
    private NetworkMazeBuilder builder = new NetworkMazeBuilder();

    /** Is the client listening for data. */
    private boolean listeningForData;

    /** Log listener for message logging. */
    private LogListener logListener;

    /** Is the server game started. */
    private boolean serverGameStarted;

    /** Number of clients ready for game. */
    private int nbClientsReady;

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
     */
    public void createServer(String ip, int port) {
        this.server = new NetworkServer(port);
        maze = MazeFactory.createMaze();
        serverGameStarted = false;
        this.setupServerBehaviours();
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
        this.createAndLaunchGame();
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
                Entity e = NetworkDialogs.getEntityFromData(data, 1);
                if (e == null) {
                    System.out.println("Error getting entity from data");
                    return true;
                }
                builder.addEntity(e);
                int nbEntities = builder.getEntitiesNumber();
                int totalNb = builder.getDataLength();
                int percent = nbEntities * NetworkDialogs.HUNDRED / totalNb;
                log("Getting maze data ... " + percent + "%", true);

                if (nbEntities == totalNb) {
                    listeningForData = false;
                    log("Getting maze data ... OK", true);
                    log("Waiting for game to start ...", false);
                    return true;
                }
                return false;
            }
        );

        this.client.when(
            GAME_STR_RSP_VALIDATOR,
            (data, infos) -> {
                maze = builder.build();
                Game.getInstance().loadFrom(maze);
                Window.getInstance().setScene(new GameSceneClient(maze, client));
                return true;
            }
        );
    }

    /**
     * Setup the server behaviours.
     * (add listeners, validators, etc.)
     */
    private void setupServerBehaviours() {
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
                    byte[] tileData = NetworkDialogs.encodeTileValue(t, 1);
                    tileData[0] = NetworkDialogs.MAZE_ADD;
                    server.sendData(tileData, infos);
                }
                for (Monster m : maze.getMonsters()) {
                    byte[] monsterData = NetworkDialogs.encodeMonsterValue(m, 1);
                    monsterData[0] = NetworkDialogs.MAZE_ADD;
                    server.sendData(monsterData, infos);
                }
                for (WorldItem i : maze.getItems()) {
                    byte[] itemData = NetworkDialogs.encodeItemValue(i, 1);
                    itemData[0] = NetworkDialogs.MAZE_ADD;
                    server.sendData(itemData, infos);
                }
                return false;
            }
        );
    }

    /**
     * Create and start a network game (as server).
     */
    private void createAndLaunchGame() {
        if (this.server == null) {
            return;
        }

        Game.getInstance().loadFrom(maze);
        server.sendData(new byte[]{NetworkDialogs.GAME_STR});

        nbClientsReady = 0;
        server.when(
            GAME_RDY_RSP_VALIDATOR,
            (data, infos) -> {
                if (++nbClientsReady < server.getClients().size()) {
                    return false;
                }

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
