package com;

import com.engine.Window;
import com.ui.MenuScene;

/**
 * Main class.
 * This is the main class for the game.
 */
public final class Main {

    private Main() {
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Window window = new Window();
        window.setScene(new MenuScene());
        window.run();
    }
}
