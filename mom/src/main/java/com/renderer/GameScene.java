package com.renderer;

import com.engine.Scene;
import com.engine.events.Event;
import com.game.Game;

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
        Game.getInstance().getMaze().update();
    }

    /**
     * Render the scene.
     */
    public void render() {
        Game.getInstance().getMaze().update();
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
