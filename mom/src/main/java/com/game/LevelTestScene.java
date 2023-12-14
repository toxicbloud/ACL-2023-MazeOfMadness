package com.game;

import com.badlogic.gdx.files.FileHandle;
import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.renderer.GameScene;
import org.json.JSONObject;

/**
 * Test scene for level management.
 */
public class LevelTestScene extends Scene {
    /** Level path. */
    private String levelPath;

    /**
     * LevelTestScene constructor.
     *
     * @param levelPath The path to the level file.
     */
    public LevelTestScene(String levelPath) {
        this.levelPath = levelPath;
    }

    @Override
    public void create() {
        FileHandle file = new FileHandle(levelPath);
        JSONObject json = new JSONObject(file.readString());
        Level level = new Level(json);
        System.out.println("==> Level object : " + level);

        Game.getInstance().loadFromLevel(level);
        Window.getInstance().setScene(new GameScene(level.getMaze()));
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void onEvent(Event ev) {

    }
}
