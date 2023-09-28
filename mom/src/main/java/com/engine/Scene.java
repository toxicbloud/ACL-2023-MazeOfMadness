package com.engine;

import com.engine.events.Event;

/**
 * Scene class.
 * This is the base class for all scenes.
 */
public abstract class Scene implements Evolvable {
    /**
     * Scene constructor.
     */
    protected Scene() {}

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
}
