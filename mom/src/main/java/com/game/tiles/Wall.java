package com.game.tiles;

/**
 * Wall class.
 * This is the wall class.
 */
public abstract class Wall extends Tile {
    /**
     * Wall constructor.
     * This is the default constructor for the wall class.
     * @param type The type of the wall.
     */
    public Wall(TileType type) {
        super(type, TILE_SPRITE, true);
    }
}
