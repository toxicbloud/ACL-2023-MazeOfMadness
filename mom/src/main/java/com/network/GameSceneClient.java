package com.network;

import com.engine.Window;
import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.Score;
import com.game.controllers.NetworkPlayerController;
import com.game.controllers.SyncedPlayerController;
import com.renderer.GameScene;
import com.ui.EndScene;

/**
 * Game scene for the server.
 */
public class GameSceneClient extends GameScene {
    /** Network client. */
    private NetworkClient client;

    /** Maze used in the game. */
    private Maze maze;

    /**
     * Constructor.
     * @param maze Maze.
     * @param client Network client.
     */
    public GameSceneClient(Maze maze, NetworkClient client) {
        super(maze);
        this.client = client;
        this.maze = maze;

        this.setupClientBehaviours();

        client.sendData(new byte[]{NetworkDialogs.GAME_RDY}); // ready to game
    }

    private void setupClientBehaviours() {
        client.when((data, infos) -> {
            return data[0] == NetworkDialogs.MAZE_ADD && data[1 + 2] == NetworkDialogs.ENTITY_PLR;
        }, (data, infos) -> {
            Player player = NetworkDialogs.getPlayerFromData(data, 2 + 1);
            maze.addEntity(player);
            int clientID = NetworkDialogs.getIntValue(data, 1);
            if (clientID == client.getId()) {
                Game.getInstance().setPlayer(player);
                new SyncedPlayerController(player, client.getId(), client);
            } else {
                new NetworkPlayerController(player, clientID, client);
            }
            return false;
        });

        client.when((data, infos) -> {
            return data[0] == NetworkDialogs.GAME_END;
        }, (data, infos) -> {
            client.shutdown();
            Window.getInstance().setScene(new EndScene(Game.getInstance().end(), false));
            return true;
        });

        client.when((data, infos) -> {
            return data[0] == NetworkDialogs.GAME_SCR;
        }, (data, infos) -> {
            Score score = NetworkDialogs.getScoreFromData(data, 1);
            Game.getInstance().getScore().apply(score);
            return false;
        });
    }

    @Override
    public void update() {
        client.update();
        super.update();
        client.update();

        Player player = Game.getInstance().getPlayer();
        if (player != null && player.hasBeenUpdated()) {
            byte[] data = NetworkDialogs.encodePlayerValue(player, 1 + 2);
            data[0] = NetworkDialogs.PLR_UPD;
            NetworkDialogs.encodeIntValue(client.getId(), data, 1);
            client.sendData(data);
        }
        client.update();
    }

    @Override
    protected void onExitCalled() {
        client.sendData(new byte[]{NetworkDialogs.GAME_END});
    }
}
