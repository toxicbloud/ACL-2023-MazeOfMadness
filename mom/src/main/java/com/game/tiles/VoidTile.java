package com.game.tiles;

import com.engine.utils.Vector3;

/**
 * VoidTile class.
 * This represents a void in the maze
 */
public class VoidTile extends Tile {

    /**
     * Void tile constructor.
     * This is the default constructor for the void tile.
     */
    public VoidTile() {
        super(TileType.VOID, null);
    }

    /**
     * Void tile constructor.
     * This is the full constructor for the void tile.
     * @param p The position of the tile.
     */
    public VoidTile(Vector3 p) {
        super(TileType.VOID, null);
        this.setPosition(p);
    }

    @Override
    public void update() {

    }
}
