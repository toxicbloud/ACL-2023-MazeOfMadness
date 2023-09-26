package com.engine.utils;

public class Vector3 extends com.badlogic.gdx.math.Vector3 {
    public Vector3() { super(); }
    public Vector3(float x, float y, float z) { super(x, y, z); }
    public Vector3(com.badlogic.gdx.math.Vector3 v) { super(v); }
    public Vector3(Vector3 v) { super(v); }

    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public Vector3 sub(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }
}
