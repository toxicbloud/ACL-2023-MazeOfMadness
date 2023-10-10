package com.game.generators.tree;

/**
 * Rectangle class. Used to define rooms.
 */
public class Rectangle {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    /**
     * top value.
     */
    private final int top;
    /**
     * bottom value.
     */
    private final int bottom;
    /**
     * left value.
     */
    private final int left;
    /**
     * right value.
     */
    private final int right;

    /**
     * Constructor. Used to create a rectangle.
     * @param x x coord.
     * @param y y coord.
     * @param width width coord.
     * @param height height coord.
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bottom = y + height;
        this.top = y;
        this.left = x;
        this.right = x + width;
    }

    /**
     * Returns top value.
     * @return top value.
     */
    public int getTop() {
        return top;
    }

    /**
     * Returns bottom value.
     * @return bottom value.
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * Returns left value.
     * @return left value.
     */
    public int getLeft() {
        return left;
    }

    /**
     * Returns right value.
     * @return right value.
     */
    public int getRight() {
        return right;
    }

    /**
     * Returns X value.
     * @return X value.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns Y value.
     * @return Y value.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns width value.
     * @return width value.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns height value.
     * @return height value.
     */
    public int getHeight() {
        return height;
    }
}
