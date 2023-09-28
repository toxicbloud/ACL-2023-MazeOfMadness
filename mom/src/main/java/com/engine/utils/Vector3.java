package com.engine.utils;

/**
 * Vector3 class.
 * This is a 3D vector.
 */
public class Vector3 extends com.badlogic.gdx.math.Vector3 {
    /**
     * Vector3 constructor.
     * This is the default constructor, initializes the vector to (0, 0, 0).
     */
    public Vector3() {
        super();
    }

    /**
     * Vector3 constructor.
     * This is the constructor with parameters, initializes the vector to (x, y, z).
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     */
    public Vector3(float x, float y, float z) {
        super(x, y, z);
    }

    /**
     * Vector3 constructor.
     * This is the constructor from a libgdx vector.
     * @param v The libgdx vector.
     * @see com.badlogic.gdx.math.Vector3
     */
    public Vector3(com.badlogic.gdx.math.Vector3 v) {
        super(v);
    }

    /**
     * Vector3 constructor.
     * This is the constructor from a vector.
     * @param v The vector.
     */
    public Vector3(Vector3 v) {
        super(v);
    }

    /**
     * Add a vector to this vector.
     * @param v The vector to add.
     * @return The sum of the two vectors.
     */
    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtract a vector from this vector.
     * @param v The vector to subtract.
     * @return The difference of the two vectors.
     */
    public Vector3 sub(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }
}
