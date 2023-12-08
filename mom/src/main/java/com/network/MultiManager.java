package com.network;

import com.game.Item;
import com.game.Maze;
import com.game.generators.MazeFactory;
import com.game.monsters.Monster;
import com.game.tiles.Tile;

/**
 * Multiplayer manager.
 */
public class MultiManager {
    /** Register validator. */
    private static final DataValidator REGISTER_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.REGISTER_RSP;
    };

    /** Maze data validator. */
    private static final DataValidator MAZE_DATA_VALIDATOR = (data, infos) -> {
        return data.length > 0 && data[0] == NetworkDialogs.MAZE_RQT;
    };

    /** Network server. */
    private NetworkServer server;

    /** Network client. */
    private NetworkClient client;

    /** Network game maze. */
    private Maze maze;

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
        this.setupServerBehaviours();
    }

    /**
     * Create a new client.
     * @param ip Server ip.
     * @param port Server port.
     */
    public void createClient(String ip, int port) {
        this.client = new NetworkClient(ip, port);
    }

    /**
     * Start the server game.
     */
    public void startServerGame() {
        this.createAndLaunchGame();
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
            REGISTER_VALIDATOR,
            (data, infos) -> {
                server.sendData(new byte[]{NetworkDialogs.REGISTER_RSP}, infos);
            }
        );

        // Maze data request
        this.server.when(
            MAZE_DATA_VALIDATOR,
            (data, infos) -> {
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
                for (Item i : maze.getItems()) {
                    byte[] itemData = NetworkDialogs.encodeItemValue(i, 1);
                    itemData[0] = NetworkDialogs.MAZE_ADD;
                    server.sendData(itemData, infos);
                }
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
    }
}
