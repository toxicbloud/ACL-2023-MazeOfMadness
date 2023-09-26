package com.ui;

import com.badlogic.gdx.math.Vector2;
import com.engine.Evolvable;

/**
 * @author Antonin Rousseau
 */
public abstract class Element implements Evolvable {
    private Vector2 position;
    private Vector2 size;
    private boolean shouldRender;
    private boolean active;
    private boolean hovered;
    private boolean pressed;

    /**
     * Default constructor.
     */
    protected Element() {
    }

    /**
     * Constructor.
     *
     * @param position Position of the element.
     *
     * @param size     Size of the element.
     */
    protected Element(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
        this.shouldRender = true;
        this.active = true;
        this.hovered = false;
        this.pressed = false;
    }

    /**
     * Constructor.
     *
     * @param position Position of the element.
     *
     * @param size     Size of the element.
     *
     * @param render   Should the element be rendered.
     */
    protected Element(Vector2 position, Vector2 size, boolean render) {
        this(position, size);
        this.shouldRender = render;
    }

    /**
     * Constructor.
     *
     * @param position Position of the element.
     * @param size     Size of the element.
     * @param render   Should the element be rendered.
     * @param active   Is the element active.
     */
    protected Element(Vector2 position, Vector2 size, boolean render, boolean active) {
        this(position, size, render);
        this.active = active;
    }

    /**
     * hovered state setter.
     *
     * @param hovered true if the element is hovered, false otherwise
     */
    protected void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    /**
     * pressed state setter.
     *
     * @param pressed true if the element is pressed, false otherwise
     */
    protected void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     *
     * @return true if the element was hovered, false otherwise
     */
    public boolean getHovered() {
        return this.hovered;
    }

    /**
     *
     * @return true if the element was pressed, false otherwise
     */
    public boolean getPressed() {
        return this.pressed;
    }

    /**
     * Get the position of the element.
     */
    public Vector2 getPosition() {
        return this.position;
    }

    /**
     * Get the size of the element.
     */
    public Vector2 getSize() {
        return this.size;
    }

    /**
     * Does the element need to be re-rendered.
     */
    public boolean needsRender() {
        return this.shouldRender;
    }

    /**
     * Is the element active.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Set the position of the element.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * update the element : logic.
     */
    @Override
    public abstract void update();

    /**
     * render the element : graphics.
     */
    @Override
    public abstract void render();

    /**
     * Called by his scene when the element is hovered.
     *
     * @param state true when entered, false when exited
     */
    public abstract void onHovered(boolean state);

    /**
     * Called by his scene when the element is clicked.
     *
     * @param state true when pressed, false when released
     */
    public abstract void onPressed(boolean state);
}
