package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * GroundLava class.
 * This is the ground lava class.
 */
public class GroundLava extends Ground {
    /** Ground lava sprite shift from texture top. */
    private static final int GROUND_LAVA_SPRITE_SHIFT = SPRITE_SIZE * 74;

    /**
     * GroundLava constructor.
     * This is the default constructor for the ground lava class.
     */
    public GroundLava() {
        super(TileType.GROUND_LAVA, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_LAVA_SPRITE_SHIFT));
    }

    /**
     * GroundLava full constructor.
     * This is the full constructor for the ground lava class.
     * @param position The position of the tile
     */
    public GroundLava(Vector3 position) {
        super(
            TileType.GROUND_LAVA,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_LAVA_SPRITE_SHIFT),
            position
        );
    }

    @Override
    public void update() {

    }
}
