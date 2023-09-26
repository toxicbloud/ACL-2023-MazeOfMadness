package com.engine.utils;

public class Vector2 extends com.badlogic.gdx.math.Vector2 {
    public Vector2() { super(); }
    public Vector2(float x, float y) { super(x, y); }
    public Vector2(com.badlogic.gdx.math.Vector2 v) { super(v); }
    public Vector2(Vector2 v) { super(v); }

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }
}
