package com.ui;

import com.engine.Evolvable;
import com.engine.Window;
import com.engine.utils.Vector2;

/**
 * @author Antonin Rousseau
 */
public abstract class Element implements Evolvable {
    /** Element position. */
    private Vector2 position;
    /** Element size. */
    private Vector2 size;
    /** Should the element be rendered. */
    private boolean shouldRender;
    /** Is the element active. */
    private boolean active;
    /** Is the element hovered. */
    private boolean hovered;
    /** Is the element pressed. */
    private boolean pressed;

    /**
     * Default element constructor.
     */
    protected Element() {
    }

    /**
     * Element constructor.
     *
     * @param position Position of the element.
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
     * @param size     Size of the element.
     * @param render   Should the element be rendered.
     */
    protected Element(Vector2 position, Vector2 size, boolean render) {
        this(position, size);
        this.shouldRender = render;
    }

    /**
     * Element constructor.
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
     * Get the hovered state of the element.
     *
     * @return true if the element was hovered, false otherwise
     */
    public boolean getHovered() {
        return this.hovered;
    }

    /**
     * Get the pressed state of the element.
     *
     * @return true if the element was pressed, false otherwise
     */
    public boolean getPressed() {
        return this.pressed;
    }

    /**
     * Get the position of the element.
     *
     * @return The position of the element.
     */
    public Vector2 getPosition() {
        return this.position;
    }

    /**
     * Get the size of the element.
     *
     * @return The size of the element.
     */
    public Vector2 getSize() {
        Window window = Window.getInstance();
        final float windowHeight = window.getHeight() / 100;
        final float windowWidth = window.getWidth() / 100;
        return new Vector2(this.size.x * windowWidth, this.size.y * windowHeight);
    }

    /**
     * Get if the element needs to be re-rendered.
     *
     * @return true if the element needs to be re-rendered, false otherwise
     */
    public boolean needsRender() {
        return this.shouldRender;
    }

    /**
     * Is the element active.
     *
     * @return true if the element is active, false otherwise
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Set if the element is active.
     *
     * @param active true if the element is active, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Update the element.
     * This method is called every frame, before any render() call.
     */
    @Override
    public abstract void update();

    /**
     * Render the element.
     * This method is called every frame, after all update() calls.
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

    abstract void create();
}
