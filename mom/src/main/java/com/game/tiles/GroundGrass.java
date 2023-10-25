package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * GroundGrass class.
 * This is the ground grass class.
 */
public class GroundGrass extends Ground {
    /** Ground grass sprite shift from texture top. */
    private static final int GROUND_GRASS_SPRITE_SHIFT = SPRITE_SIZE * 0; // TODO : Make block texture

    /**
     * GroundGrass default constructor.
     * This is the default constructor for the ground grass class.
     */
    public GroundGrass() {
        super(TileType.GROUND_GRASS, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_GRASS_SPRITE_SHIFT));
    }

    /**
     * GroundGrass full constructor.
     * This is the full constructor for the ground grass class.
     * @param position The position of the tile
     */
    public GroundGrass(Vector3 position) {
        super(
            TileType.GROUND_GRASS,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_GRASS_SPRITE_SHIFT),
            position
        );
    }

    @Override
    public void update() {

    }
}
