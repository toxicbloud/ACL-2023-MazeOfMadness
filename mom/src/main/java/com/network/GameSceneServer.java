package com.network;

import com.game.Maze;
import com.game.Player;
import com.game.monsters.Monster;
import com.renderer.GameScene;

import java.util.List;

/**
 * Game scene for the server.
 */
public class GameSceneServer extends GameScene {
    /** Network server. */
    private NetworkServer server;

    /** Client players list. */
    private Player[] players;

    /** Maze used in the game. */
    private Maze maze;

    /**
     * Constructor.
     * @param maze Maze.
     * @param server Network server.
     */
    public GameSceneServer(Maze maze, NetworkServer server) {
        super(maze);
        this.server = server;
        this.maze = maze;

        this.spawnAllPlayers();
    }

    private void spawnAllPlayers() {
        List<NetworkInfos> clients = server.getClients();
        Player player = new Player(maze.getSpawnPoint());
        byte[] playerData = NetworkDialogs.encodePlayerValue(player, 2);
        playerData[0] = NetworkDialogs.MAZE_ADD;
        playerData[1] = 0;
        server.sendData(playerData);

        players = new Player[clients.size()];
        for (int i = 0; i < players.length; i++) {
            player = new Player(maze.getSpawnPoint());
            players[i] = player;
            // new NetworkPlayerController(player, clients.get(i).getId(), server);
            maze.addEntity(players[i]);
            playerData = NetworkDialogs.encodePlayerValue(player, 2);
            playerData[0] = NetworkDialogs.MAZE_ADD;
            playerData[1] = (byte) clients.get(i).getId();
            server.sendData(playerData);
        }
    }

    @Override
    public void update() {
        super.update();

        for (Monster m : maze.getMonsters()) {
            if (m.hasBeenUpdated()) {
                byte[] entityData = NetworkDialogs.encodeMonsterValue(m, 1 + 2);
                entityData[0] = NetworkDialogs.MAZE_UPD;
                NetworkDialogs.encodeIntValue(m.getId(), entityData, 1);
                server.sendData(entityData);
            }
            if (m.hasBeenDestroyed()) {
                byte[] entityData = new byte[1 + 2];
                entityData[0] = NetworkDialogs.MAZE_REM;
                NetworkDialogs.encodeIntValue(m.getId(), entityData, 1);
                server.sendData(entityData);
            }
        }

        server.update();
    }
}
