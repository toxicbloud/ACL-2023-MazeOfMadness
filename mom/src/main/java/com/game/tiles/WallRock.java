package com.game.tiles;

import com.engine.Sprite;

/**
 * WallRock class.
 * This is the wall rock class.
 */
public class WallRock extends Wall {
    /** Wall rock sprite shift from texture top. */
    private static final int WALL_ROCK_SPRITE_SHIFT = SPRITE_SIZE * 1;

    /**
     * WallRock constructor.
     * This is the default constructor for the wall rock class.
     */
    public WallRock() {
        super(TileType.WALL_ROCK, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, WALL_ROCK_SPRITE_SHIFT));
    }

    @Override
    public void update() {

    }
}
