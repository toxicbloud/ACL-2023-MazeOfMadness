package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * Stair class.
 * This is the stair class.
 */
public abstract class Stair extends Tile {
    /** Stair direction x. */
    public static final int DIRECTION_X = 1;
    /** Stair direction y. */
    public static final int DIRECTION_Y = 2;

    /** Stair direction. */
    private int direction;

    /**
     * Stair constructor.
     * This is the default constructor for the stair class.
     * @param type The type of the stair.
     * @param s The sprite of the stair.
     * @param dir The direction of the stair.
     */
    public Stair(TileType type, Sprite s, int dir) {
        super(type, s, false);
        this.direction = dir;
    }

    /**
     * Stair constructor.
     * This is the full constructor for the stair class.
     * @param type The type of the stair.
     * @param s The sprite of the stair.
     * @param p The position of the ground.
     * @param dir The direction of the stair.
     */
    public Stair(TileType type, Sprite s, Vector3 p, int dir) {
        super(type, s, p, false);
        this.direction = dir;
    }

    /**
     * Get the direction of the stair.
     * @return The direction of the stair.
     */
    public int getDirection() {
        return direction;
    }
}
