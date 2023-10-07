package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * StairRock class.
 * This is the ground rock class.
 */
public class StairRock extends Stair {
    /** Stair rock sprite shift from texture top. */
    private static final int STAIR_ROCK_SPRITE_SHIFT_X = SPRITE_SIZE * 62;
    /** Stair rock sprite shift from texture top. */
    private static final int STAIR_ROCK_SPRITE_SHIFT_Y = SPRITE_SIZE * 61;

    /**
     * StairRock default constructor.
     * This is the default constructor for the stair rock class.
     * direction = x
     */
    public StairRock() {
        super(
            TileType.STAIR_ROCK,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, STAIR_ROCK_SPRITE_SHIFT_X),
            DIRECTION_X
        );
    }

    /**
     * StairRock full constructor.
     * This is the full constructor for the stair rock class.
     * @param direction The direction of the stair
     */
    public StairRock(int direction) {
        super(
            TileType.STAIR_ROCK,
            new Sprite(
                TILE_TEXTURE,
                SPRITE_SIZE,
                SPRITE_SIZE,
                direction == DIRECTION_X ? STAIR_ROCK_SPRITE_SHIFT_X : STAIR_ROCK_SPRITE_SHIFT_Y
            ),
            direction
        );
    }

    /**
     * StairRock full constructor.
     * This is the full constructor for the stair rock class.
     * @param position The position of the tile
     * @param direction The direction of the stair
     */
    public StairRock(Vector3 position, int direction) {
        super(
            TileType.STAIR_ROCK,
            new Sprite(
                TILE_TEXTURE,
                SPRITE_SIZE,
                SPRITE_SIZE,
                direction == DIRECTION_X ? STAIR_ROCK_SPRITE_SHIFT_X : STAIR_ROCK_SPRITE_SHIFT_Y
            ),
            position,
            direction
        );
    }

    @Override
    public void update() {

    }
}
