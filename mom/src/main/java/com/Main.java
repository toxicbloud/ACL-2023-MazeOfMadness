package com;

import com.engine.Window;
import com.ui.MenuScene;

/**
 * Main class.
 * This is the main class for the game.
 */
public final class Main {

    /**
     * Button width.
     */
    public static final int BUTTON_WIDTH = 100;

    /**
     * Button height.
     */
    public static final int BUTTON_HEIGHT = 100;

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
