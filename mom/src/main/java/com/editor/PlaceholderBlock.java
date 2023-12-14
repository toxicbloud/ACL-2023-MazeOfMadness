package com.editor;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Entity;
import org.json.JSONObject;

/**
 * Placeholder block.
 * This is a placeholder block used in the editor.
 */
public class PlaceholderBlock extends Entity {

    /**
     * PlaceholderBlock constructor.
     */
    public PlaceholderBlock() {
        super(
            new Sprite(
                new Texture("images/debug.png"),
                Entity.SPRITE_SIZE,
                Entity.SPRITE_SIZE,
                0
            ),
            new Vector3(0, 0, 0),
            new Vector3(1, 1, 1));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    protected void remove() {
        // SHOULD NOT BE REMOVED
    }

    @Override
    public JSONObject toJSON() {
        return null; // not needed
    }
}
