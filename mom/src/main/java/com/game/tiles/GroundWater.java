package com.game.tiles;

import com.engine.Sprite;

/**
 * GroundWater class.
 * This is the ground water class.
 */
public class GroundWater extends Ground {

    /**
     * GroundWater constructor.
     * This is the default constructor for the ground water class.
     */
    public GroundWater() {
        super(TileType.GROUND_ROCK, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE));
    }

    @Override
    public void update() {

    }
}
