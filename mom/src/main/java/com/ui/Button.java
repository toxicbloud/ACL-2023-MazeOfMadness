package com.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.engine.Window;
import com.engine.utils.Vector2;

import java.util.List;

/**
 * 2D UI Button class.
 */
public class Button extends Element {
    /**
     * The button listeners to invoke.
     */
    private List<ButtonListener> listeners;

    /**
     * Default constructor.
     */
    public Button() {
        super();
    }

    /**
     * Constructor with position and size.
     *
     * @param position The position.
     * @param size     The size.
     */
    public Button(Vector2 position, Vector2 size) {
        super(position, size);
    }

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
        ShapeRenderer renderer = new ShapeRenderer();
        // renderer.setProjectionMatrix(new OrthographicCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        // renderer.setColor(1, 1, 1, 1);
        if (this.getHovered()) {
            renderer.setColor(1, 0, 0, 1);
        } else {
            renderer.setColor(1, 1, 1, 1);
        }
        Window window = Window.getInstance();
        float windowHeight = window.getHeight();
        float windowWidth = window.getWidth();
        // renderer.rect(this.getPosition().x * windowWidth, this.getPosition().y *
        // windowHeight,
        // this.getSize().x, this.getSize().y);
        renderer.rect(getPosition().x * windowWidth - getSize().x / 2, windowHeight - getPosition().y,
                getSize().x, -getSize().y);
        renderer.end();
    }

    /**
     * Hovered handler.
     *
     * @param state The state.
     */
    @Override
    public void onHovered(boolean state) {
        this.setHovered(state);
        // TODO
    }

    /**
     * Pressed handler.
     *
     * @param state The state.
     */
    @Override
    public void onPressed(boolean state) {
        this.setPressed(state);
        if (state) {
            listeners.forEach(ButtonListener::onPressed);
        } else {
            listeners.forEach(ButtonListener::onReleased);
        }
    }

    /**
     * Add a button listener.
     *
     * @param listener The listener.
     */
    public void addListener(ButtonListener listener) {
        listeners.add(listener);
    }
}
