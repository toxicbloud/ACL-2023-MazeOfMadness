package com.engine;

import com.badlogic.gdx.Gdx;

/**
 * Texture class.
 * This is a texture.
 */
public class Texture {
    /** The path to the texture. */
    private String path;
    /** The texture base object. */
    private com.badlogic.gdx.graphics.Texture texture;

    /**
     * Texture constructor.
     * @param path The path to the texture.
     */
    public Texture(String path) {
        this.path = path;
        this.texture = new com.badlogic.gdx.graphics.Texture(Gdx.files.internal(this.path));
    }

    /**
     * Get the texture base object.
     * @return The texture base object.
     */
    public com.badlogic.gdx.graphics.Texture getTexture() {
        return texture;
    }
}
