package com.engine.utils;

/**
 * Vector2 class.
 * This is a 2D vector.
 */
public class Vector2 extends com.badlogic.gdx.math.Vector2 {
    /**
     * Vector2 constructor.
     * This is the default constructor, initializes the vector to (0, 0).
     */
    public Vector2() {
        super();
    }

    /**
     * Vector2 constructor.
     * This is the constructor with parameters, initializes the vector to (x, y).
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Vector2(float x, float y) {
        super(x, y);
    }

    /**
     * Vector2 constructor.
     * This is the constructor from a vector.
     * @param v The vector.
     */
    public Vector2(com.badlogic.gdx.math.Vector2 v) {
        super(v);
    }

    /**
     * Vector2 constructor.
     * This is the constructor from a vector.
     * @param v The vector.
     */
    public Vector2(Vector2 v) {
        super(v);
    }

    /**
     * Add a vector to this vector.
     * @param v The vector to add.
     * @return The sum of the two vectors.
     */
    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    /**
     * Subtract a vector from this vector.
     * @param v The vector to subtract.
     * @return The difference of the two vectors.
     */
    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    /**
     * Multiplies the vector by a float.
     * @param f the float used to multiply
     * @return a new vector corresponding to (this.x * float, this.y * float)
     */
    public Vector2 mul(float f) {
        return new Vector2(x * f, y * f);
    }

    /**
     * Get the normalized vector.
     * @return The normalized vector.
     */
    public Vector2 normalize() {
        float length = this.len();
        if (length == 0) {
            return new Vector2(0, 0);
        }
        return new Vector2(x / length, y / length);
    }

    /**
     * Get the x coordinate.
     * @return The x coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Get the y coordinate.
     * @return The y coordinate.
     */
    public float getY() {
        return y;
    }
}
