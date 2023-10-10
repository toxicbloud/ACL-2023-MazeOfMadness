package com.ui;

/**
 * Button listener interface.
 */
public interface ButtonListener {
    /**
     * Called when the button is pressed.
     */
    void onPressed();

    /**
     * Called when the button is released.
     */
    void onReleased();

    /**
     * Called when the button is hovered.
     */
    void onHovered();
}
