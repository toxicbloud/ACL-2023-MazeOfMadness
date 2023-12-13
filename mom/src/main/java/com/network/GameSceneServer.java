package com.network;

import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.NetworkPlayerController;
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
        this.setupServerBehaviors();
    }

    private void spawnAllPlayers() {
        List<NetworkInfos> clients = server.getClients();
        Player player = new Player(maze.getSpawnPoint());
        byte[] playerData = NetworkDialogs.encodePlayerValue(player, 1 + 2);
        playerData[0] = NetworkDialogs.MAZE_ADD;
        NetworkDialogs.encodeIntValue(0, playerData, 1);
        server.sendData(playerData);

        players = new Player[clients.size()];
        for (int i = 0; i < players.length; i++) {
            int clientId = clients.get(i).getId();
            player = new Player(maze.getSpawnPoint());
            new NetworkPlayerController(player, clientId, server);
            players[i] = player;
            maze.addEntity(players[i]);

            playerData = NetworkDialogs.encodePlayerValue(player, 1 + 2);
            playerData[0] = NetworkDialogs.MAZE_ADD;
            NetworkDialogs.encodeIntValue(clientId, playerData, 1);
            server.sendData(playerData);
        }
    }

    private void setupServerBehaviors() {
        server.when((data, infos) -> {
            return data[0] == NetworkDialogs.PLR_UPD;
        }, (data, infos) -> {
            server.sendData(data);
            return false;
        });
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

        Player player = Game.getInstance().getPlayer();
        if (player != null && player.hasBeenUpdated()) {
            byte[] data = NetworkDialogs.encodePlayerValue(player, 1 + 2);
            data[0] = NetworkDialogs.PLR_UPD;
            NetworkDialogs.encodeIntValue(server.getId(), data, 1);
            server.sendData(data);
        }

        server.update();
    }
}
