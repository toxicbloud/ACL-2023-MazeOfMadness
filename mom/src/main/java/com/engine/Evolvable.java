package com.engine;

/**
 * Evolvable interface.
 * This is the interface for all evolvable objects.
 */
public interface Evolvable {
    /**
     * Update the evolvable object.
     * This is called every frame before any render() call.
     */
    void update();

    /**
     * Render the evolvable object.
     * This is called every frame after all update() calls.
     */
    void render();
}
