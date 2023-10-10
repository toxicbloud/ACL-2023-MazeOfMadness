package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * GroundWater class.
 * This is the ground water class.
 */
public class GroundWater extends Ground {
    /** Ground water sprite shift from texture top. */
    private static final int GROUND_WATER_SPRITE_SHIFT = SPRITE_SIZE * 66;

    /**
     * GroundWater constructor.
     * This is the default constructor for the ground water class.
     */
    public GroundWater() {
        super(TileType.GROUND_WATER, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_WATER_SPRITE_SHIFT));
    }

    /**
     * GroundWater full constructor.
     * This is the full constructor for the ground water class.
     * @param position The position of the tile
     */
    public GroundWater(Vector3 position) {
        super(
            TileType.GROUND_WATER,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_WATER_SPRITE_SHIFT),
            position
        );
    }

    @Override
    public void update() {

    }
}
