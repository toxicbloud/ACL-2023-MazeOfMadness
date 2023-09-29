package com.game.tiles;

import com.engine.Sprite;

/**
 * GroundRock class.
 * This is the ground rock class.
 */
public class GroundRock extends Ground {

    /**
     * GroundRock constructor.
     * This is the default constructor for the ground rock class.
     */
    public GroundRock() {
        super(TileType.GROUND_ROCK, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE));
    }

    @Override
    public void update() {

    }
}
