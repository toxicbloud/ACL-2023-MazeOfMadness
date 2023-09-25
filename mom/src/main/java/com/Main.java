package com;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.engine.Window;

/**
 * Main class.
 * This is the main class for the game.
 */
public final class Main {
    static final int WIDTH = 1280;
    static final int HEIGHT = 720;

    private Main() {}

    /**
     * Main method.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Maze Of Madness");
        config.setWindowedMode(Main.WIDTH, Main.HEIGHT);
        config.useVsync(true);
        config.setResizable(false);

        new Lwjgl3Application(new Window(), config);
    }
}
