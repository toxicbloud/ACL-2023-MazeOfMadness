package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * StairGrass class.
 * This is the ground grass class.
 */
public class StairGrass extends Stair {
    /** Stair grass sprite shift from texture top. */
    private static final int STAIR_GRASS_SPRITE_SHIFT_X = SPRITE_SIZE * 69;
    /** Stair grass sprite shift from texture top. */
    private static final int STAIR_GRASS_SPRITE_SHIFT_Y = SPRITE_SIZE * 97;

    /**
     * StairGrass default constructor.
     * This is the default constructor for the stair grass class.
     * direction = x
     */
    public StairGrass() {
        super(
            TileType.STAIR_GRASS,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, STAIR_GRASS_SPRITE_SHIFT_X),
            DIRECTION_X
        );
    }

    /**
     * StairGrass full constructor.
     * This is the full constructor for the stair grass class.
     * @param direction The direction of the stair
     */
    public StairGrass(int direction) {
        super(
            TileType.STAIR_GRASS,
            new Sprite(
                TILE_TEXTURE,
                SPRITE_SIZE,
                SPRITE_SIZE,
                direction == DIRECTION_X ? STAIR_GRASS_SPRITE_SHIFT_X : STAIR_GRASS_SPRITE_SHIFT_Y
            ),
            direction
        );
    }

    /**
     * StairGrass full constructor.
     * This is the full constructor for the stair grass class.
     * @param position The position of the tile
     * @param direction The direction of the stair
     */
    public StairGrass(Vector3 position, int direction) {
        super(
            TileType.STAIR_GRASS,
            new Sprite(
                TILE_TEXTURE,
                SPRITE_SIZE,
                SPRITE_SIZE,
                direction == DIRECTION_X ? STAIR_GRASS_SPRITE_SHIFT_X : STAIR_GRASS_SPRITE_SHIFT_Y
            ),
            position,
            direction
        );
    }

    @Override
    public void update() {

    }
}
