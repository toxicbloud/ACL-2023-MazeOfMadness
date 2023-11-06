package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * GroundRock class.
 * This is the ground rock class.
 */
public class GroundRock extends Ground {
    /** Ground rock sprite shift from texture top. */
    private static final int GROUND_ROCK_SPRITE_SHIFT = SPRITE_SIZE * 4;

    /**
     * GroundRock default constructor.
     * This is the default constructor for the ground rock class.
     */
    public GroundRock() {
        super(TileType.GROUND_ROCK, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_ROCK_SPRITE_SHIFT));
    }

    /**
     * GroundRock full constructor.
     * This is the full constructor for the ground rock class.
     * @param position The position of the tile
     */
    public GroundRock(Vector3 position) {
        super(
            TileType.GROUND_ROCK,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_ROCK_SPRITE_SHIFT),
            position
        );
    }

    @Override
    public void update() {

    }
}
