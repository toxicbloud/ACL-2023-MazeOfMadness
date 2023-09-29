package com.game.tiles;

import com.engine.Sprite;

/**
 * Ground class.
 * This is the ground class.
 */
public abstract class Ground extends Tile {
    /**
     * Ground constructor.
     * This is the default constructor for the ground class.
     * @param type The type of the ground.
     * @param s The sprite of the ground.
     */
    public Ground(TileType type, Sprite s) {
        super(type, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE), false);
    }
}
