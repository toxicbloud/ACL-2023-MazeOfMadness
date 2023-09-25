package com.ui;

import com.badlogic.gdx.Game;

/**
 * Window class.
 * This is the window class for the game.
 */
public class Window extends Game {
    /**
     * Create the game.
     * This is called once on startup.
     */
    @Override
    public void create() {
        System.out.println("Hello, world!");
    }

    /**
     * Render the game.
     * This is called once per frame.
     */
    @Override
    public void render() {
        System.out.println("Rendering...");
        super.render();
    }

    /**
     * Dispose of the game.
     * This is called once on shutdown.
     */
    @Override
    public void dispose() {
        System.out.println("Goodbye, world!");
    }
}
