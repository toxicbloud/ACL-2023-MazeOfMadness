package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

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

    /**
     * Ground constructor.
     * This is the full constructor for the ground class.
     * @param type The type of the ground.
     * @param s The sprite of the ground.
     * @param p The position of the ground.
     */
    public Ground(TileType type, Sprite s, Vector3 p) {
        super(type, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE), p, false);
    }
}
