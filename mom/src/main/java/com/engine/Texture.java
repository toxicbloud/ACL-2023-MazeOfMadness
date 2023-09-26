package com.engine;

import com.badlogic.gdx.Gdx;

public class Texture {
    private String path;
    private com.badlogic.gdx.graphics.Texture texture;

    public Texture(String path) {
        this.path = path;
        this.texture = new com.badlogic.gdx.graphics.Texture(Gdx.files.internal(this.path));
    }

    public com.badlogic.gdx.graphics.Texture getTexture() {
        return texture;
    }
}
