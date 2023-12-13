package com.network;

import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.renderer.GameScene;

/**
 * Game scene for the server.
 */
public class GameSceneClient extends GameScene {
    /** Network client. */
    private NetworkClient client;

    /**
     * Constructor.
     * @param maze Maze.
     * @param client Network client.
     */
    public GameSceneClient(Maze maze, NetworkClient client) {
        super(maze);
        this.client = client;

        client.when((data, infos) -> {
            return data[0] == NetworkDialogs.MAZE_ADD && data[2] == NetworkDialogs.ENTITY_PLR;
        }, (data, infos) -> {
            Player player = NetworkDialogs.getPlayerFromData(data, 2 + 1);
            maze.addEntity(player);
            int clientID = data[1];
            if (clientID == client.getId()) {
                Game.getInstance().setPlayer(player);
            }
            return false;
        });

        client.sendData(new byte[]{NetworkDialogs.GAME_RDY}); // ready to game
    }

    @Override
    public void update() {
        super.update();
        client.update();
    }
}
