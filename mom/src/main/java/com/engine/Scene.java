package com.engine;

import com.engine.events.Event;

/**
 * Scene class.
 * This is the base class for all scenes.
 */
public abstract class Scene implements Evolvable {
    /** Scene width. */
    private int width;
    /** Scene height. */
    private int height;

    /**
     * Scene constructor.
     */
    protected Scene() {
        this.width = Window.getInstance().getWidth();
        this.height = Window.getInstance().getHeight();
    }

    /**
     * Scene constructor.
     * @param width Scene width.
     * @param height Scene height.
     */
    protected Scene(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Create the scene.
     * This method is before any update(), once.
     */
    public abstract void create();

    /**
     * Update the scene.
     * This method is called every frame, before any render() call.
     */
    public abstract void update();

    /**
     * Render the scene.
     * This method is called every frame, after all update() calls.
     */
    public abstract void render();

    /**
     * Handle an event.
     * This method is called before any update() call, for every new event.
     * @param ev The event.
     */
    public abstract void onEvent(Event ev);

    /**
     * Get the scene width.
     * @return The scene width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the scene height.
     * @return The scene height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the scene height.
     * @param height The scene height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set the scene width.
     * @param width The scene width.
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
