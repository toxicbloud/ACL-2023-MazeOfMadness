package com.engine.utils;

/**
 * Movement class.
 * This is the movement class.
 * Used to describe a movement of a path.
 */
public class Movement {
    /** Final x position of the movement. */
    private int x;
    /** Final y position of the movement. */
    private int y;
    /** Next movement to do (null if end of movement). */
    private Movement next;

    /**
     * Movement default constructor.
     * @param x The x position of the movement
     * @param y The y position of the movement
     * @param next The next movement to do
     */
    public Movement(int x, int y, Movement next) {
        this.x = x;
        this.y = y;
        this.next = next;
    }

    /**
     * Get the next movement.
     * @return The next movement
     */
    public Movement getNext() {
        return next;
    }

    /**
     * Get the x position of the movement.
     * @return The x position of the movement
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y position of the movement.
     * @return The y position of the movement
     */
    public int getY() {
        return y;
    }
}
