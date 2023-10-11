package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * GroundRockVisible class.
 * This is the ground rock  class.
 */
public class GroundRockTiled extends Ground {

    /** Ground rock tiled sprite shift from texture top. */
    private static final int GROUND_ROCK_TILED_SPRITE_SHIFT = SPRITE_SIZE * 59;

    /**
     * GroundRockTiled default constructor.
     * This is the default constructor for the ground rock tiled class.
     */
    public GroundRockTiled() {
        super(TileType.GROUND_ROCK, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_ROCK_TILED_SPRITE_SHIFT));
    }

    /**
     * GroundRockTiled full constructor.
     * This is the full constructor for the ground rock tiled class.
     * @param position The position of the tile
     */
    public GroundRockTiled(Vector3 position) {
        super(
            TileType.GROUND_ROCK,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_ROCK_TILED_SPRITE_SHIFT),
            position
        );
    }

    @Override
    public void update() {

    }
}
