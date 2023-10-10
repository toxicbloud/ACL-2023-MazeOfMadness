package com.ui;

import com.badlogic.gdx.graphics.Color;
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
     * The base color.
     */
    private Color baseColor;
    /**
     * The hover color.
     */
    private Color hoverColor;

    /**
     * Default constructor.
     */
    public Button() {
        super();
        listeners = new java.util.ArrayList<>();
    }

    /**
     * Constructor with position, size, base color and hover color.
     *
     * @param position   position in the window (0.0f - 1.0f)
     * @param size       size in the window (0.0f - 1.0f)
     * @param baseColor  base color
     * @param hoverColor hover color
     */
    public Button(Vector2 position, Vector2 size, Color baseColor, Color hoverColor) {
        super(position, size);
        this.listeners = new java.util.ArrayList<>();
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;
    }

    /**
     * Constructor with position and size.
     *
     * @param position The position.
     * @param size     The size.
     */
    public Button(Vector2 position, Vector2 size) {
        super(position, size);
        this.listeners = new java.util.ArrayList<>();
        this.baseColor = Color.WHITE;
        this.hoverColor = Color.GRAY;
    }

    /**
     * Update the button logic.
     */
    @Override
    public void update() {
    }

    /**
     * Render the button.
     */
    @Override
    public void render() {
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (this.getHovered()) {
            renderer.setColor(hoverColor);
        } else {
            renderer.setColor(baseColor);
        }
        Window window = Window.getInstance();
        float windowHeight = window.getHeight();
        float windowWidth = window.getWidth();
        renderer.rect(getPosition().x * windowWidth - getSize().x / 2, windowHeight - getPosition().y * windowHeight,
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
        if (state) {
            listeners.forEach(ButtonListener::onHovered);
        }
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

    /**
     * Set the base color.
     *
     * @param baseColor base color.
     */
    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    /**
     * Set the hover color.
     *
     * @param hoverColor hover color.
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    /**
     * Get the base color.
     *
     * @return base color.
     */
    public Color getBaseColor() {
        return baseColor;
    }

    /**
     * Get the hover color.
     *
     * @return hover color.
     */
    public Color getHoverColor() {
        return hoverColor;
    }

    @Override
    void create() {
    }
}
