package com.game.tiles;

import com.engine.Sprite;

/**
 * Wall class.
 * This is the wall class.
 */
public abstract class Wall extends Tile {
    /**
     * Wall constructor.
     * This is the default constructor for the wall class.
     * @param type The type of the wall.
     * @param s The sprite of the wall.
     */
    public Wall(TileType type, Sprite s) {
        super(type, s, true);
    }
}
