package com.renderer;

import com.engine.Scene;
import com.engine.events.Event;

public class GameScene extends Scene {
    private Camera camera;

    public GameScene() {
        super();
        camera = new Camera();
    }

    public void update() {
        // TODO
    }

    public void render() {
        // TODO
    }

    public void onEvent(Event event) {
        // TODO
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
