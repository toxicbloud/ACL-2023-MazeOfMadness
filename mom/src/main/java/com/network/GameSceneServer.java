package com.network;

import com.engine.Window;
import com.game.Entity;
import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.NetworkPlayerController;
import com.renderer.GameScene;
import com.ui.EndScene;

import java.util.ArrayList;
import java.util.Arrays;
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

        server.when((data, infos) -> {
            return data[0] == NetworkDialogs.GAME_END;
        }, (data, infos) -> {
            onExitCalled();
            Window.getInstance().setScene(new EndScene(Game.getInstance().end(), false));
            return false;
        });

        Game.getInstance().getScore().addPropertyChangeListener("points", evt -> {
            byte[] data = NetworkDialogs.encodeScoreValue(Game.getInstance().getScore(), 1);
            data[0] = NetworkDialogs.GAME_SCR;
            server.sendData(data);
        });
    }

    @Override
    public void update() {
        super.update();

        List<Entity> updatable = new ArrayList<>();
        updatable.addAll(Arrays.asList(maze.getMonsters()));
        updatable.addAll(Arrays.asList(maze.getItems()));

        for (Entity e : updatable) {
            if (e.hasBeenUpdated()) {
                byte[] entityData = NetworkDialogs.encodeEntityValue(e, 1 + 2);
                entityData[0] = NetworkDialogs.MAZE_UPD;
                NetworkDialogs.encodeIntValue(e.getId(), entityData, 1);
                server.sendData(entityData);
            }
            if (e.hasBeenDestroyed()) {
                byte[] entityData = new byte[1 + 2];
                entityData[0] = NetworkDialogs.MAZE_REM;
                NetworkDialogs.encodeIntValue(e.getId(), entityData, 1);
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

    @Override
    protected void onExitCalled() {
        server.sendData(new byte[]{NetworkDialogs.GAME_END});
        server.shutdown();
    }
}
