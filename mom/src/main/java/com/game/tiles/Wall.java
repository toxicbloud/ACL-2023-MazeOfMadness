package com.game.tiles;

/**
 * Wall class.
 * This is the wall class.
 */
public class Wall extends Tile {
    /**
     * Wall constructor.
     * This is the default constructor for the wall class.
     */
    public Wall() {
        super(TileType.TYPE_WALL, TILE_SPRITE, true);
    }

    @Override
    public void update() {

    }
}
