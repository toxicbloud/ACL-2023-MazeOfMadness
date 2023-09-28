package com.game.tiles;

/**
 * Ground class.
 * This is the ground class.
 */
public abstract class Ground extends Tile {
    /**
     * Ground constructor.
     * This is the default constructor for the ground class.
     * @param type The type of the ground.
     */
    public Ground(TileType type) {
        super(type, TILE_SPRITE, false);
    }
}
