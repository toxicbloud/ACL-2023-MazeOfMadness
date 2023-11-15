package com.editor;

import com.engine.Scene;
import com.engine.events.Event;
import com.renderer.GameScene;

/**
 * Editor scene.
 * This is the scene used to edit a game level.
 */
public class EditorScene extends Scene {
    /** GameScene used as preview window. */
    private GameScene previewScene;

    /**
     * EditorScene constructor.
     */
    public EditorScene() {
        previewScene = new GameScene();
    }

    @Override
    public void create() {
        previewScene.create();
    }

    @Override
    public void update() {
        previewScene.update();
    }

    @Override
    public void render() {
        previewScene.render();
    }

    @Override
    public void onEvent(Event ev) {

    }

}
