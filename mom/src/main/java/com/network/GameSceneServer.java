package com.network;

import com.game.Maze;
import com.renderer.GameScene;

/**
 * Game scene for the server.
 */
public class GameSceneServer extends GameScene {
    /** Network server. */
    private NetworkServer server;

    /**
     * Constructor.
     * @param maze Maze.
     */
    public GameSceneServer(Maze maze, NetworkServer server) {
        super(maze);
        this.server = server;
    }

    @Override
    public void update() {
        super.update();
        server.update();
    }
}
