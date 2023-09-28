package com.renderer;

import com.engine.Scene;
import com.engine.events.Event;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
    /**
     * Camera object.
     * This is the scene camera.
     */
    private Camera camera;

    /**
     * GameScene constructor.
     */
    public GameScene() {
        super();
        camera = new Camera();
    }

    /**
     * Update the scene.
     */
    public void update() {
        // TODO
    }

    /**
     * Render the scene.
     */
    public void render() {
        // TODO
    }

    /**
     * Handle an event.
     * @param event The event.
     */
    public void onEvent(Event event) {
        // TODO
    }

    /**
     * Get the scene camera.
     * @return The scene camera.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Set the scene camera.
     * @param camera The scene camera.
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
