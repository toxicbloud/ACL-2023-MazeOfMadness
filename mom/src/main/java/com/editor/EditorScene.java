package com.editor;

import com.engine.Scene;
import com.engine.events.Event;

/**
 * Editor scene.
 * This is the scene used to edit a game level.
 */
public class EditorScene extends Scene {

    @Override
    public void create() {
        System.out.println("Method 'create'");
    }

    @Override
    public void update() {
        System.out.println("Method 'update'");
    }

    @Override
    public void render() {
        System.out.println("Method 'render'");
    }

    @Override
    public void onEvent(Event ev) {
        System.out.println("Method 'onEvent'");
    }

}
