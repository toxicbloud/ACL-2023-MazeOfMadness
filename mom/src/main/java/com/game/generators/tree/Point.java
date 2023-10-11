package com.game.generators.tree;

/**
 * Point Class. Used to simplify Rectangle creation.
 */
public class Point {
    /**
     * x coordinate.
     */
    private final int x;
    /**
     * y coordinate.
     */
    private final int y;

    /**
     * Constructor. Used to create points.
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x coordinate.
     * @return x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y coordinate.
     * @return y coordinate.
     */
    public int getY() {
        return y;
    }
}
