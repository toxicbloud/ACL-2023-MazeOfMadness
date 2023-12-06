package com.network;

import com.game.Maze;
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
    }

    @Override
    public void update() {
        super.update();
    }
}
