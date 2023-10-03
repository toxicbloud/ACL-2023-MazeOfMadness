package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * Wall class.
 * This is the wall class.
 */
public abstract class Wall extends Tile {
    /**
     * Wall default constructor.
     * This is the default constructor for the wall class.
     * @param type The type of the wall.
     * @param s The sprite of the wall.
     */
    public Wall(TileType type, Sprite s) {
        super(type, s, true);
    }

    /**
     * Wall full constructor.
     * This is the full constructor for the wall class.
     * @param type The type of the wall.
     * @param s The sprite of the wall.
     * @param position The position of the wall.
     */
    public Wall(TileType type, Sprite s, Vector3 position) {
        super(type, s, position, true);
    }
}
