package com.ui;

/**
 * 2D UI Button class.
 */
public class Button extends Element {

    /**
     * Update the button logic.
     */
    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * Render the button.
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    /**
     * Hovered handler.
     */
    @Override
    public void onHovered(boolean state) {
        this.setHovered(state);
        // TODO
    }

    /**
     * Pressed handler.
     */
    @Override
    public void onPressed(boolean state) {
        this.setPressed(state);
        // TODO
    }

}
