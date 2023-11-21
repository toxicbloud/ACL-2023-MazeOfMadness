package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

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

    /**
     * WallRock full constructor.
     * This is the full constructor for the wall rock class.
     * @param position The position of the tile
     */
    public WallRock(Vector3 position) {
        super(
            TileType.WALL_ROCK,
            new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, WALL_ROCK_SPRITE_SHIFT),
            position
        );
    }

    @Override
    public void update() {

    }
}
