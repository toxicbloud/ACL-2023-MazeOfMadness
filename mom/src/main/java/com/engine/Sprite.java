package com.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.renderer.Camera;
import com.renderer.GameScene;

/**
 * Sprite class.
 * This is a sprite.
 */
public class Sprite {
    /** The width of the sprite. */
    private int width;
    /** The height of the sprite. */
    private int height;
    /** The shift in pixels to get the sprite. */
    private int shift;
    /** The texture of the sprite. */
    private Texture texture;
    /** The sprite base object. */
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;

    /**
     * Sprite constructor.
     * This is the constructor with parameters.
     * @param texture The texture of the sprite.
     * @param width The width of the sprite.
     * @param height The height of the sprite.
     */
    public Sprite(Texture texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.shift = 0;
        this.generateSprite();
    }

    /**
     * Sprite constructor.
     * This is the constructor with parameters.
     * @param texture The texture of the sprite.
     * @param width The width of the sprite.
     * @param height The height of the sprite.
     * @param shift The shift of texture applied to get the sprite (in the y down axis)
     */
    public Sprite(Texture texture, int width, int height, int shift) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.shift = shift;
        this.generateSprite();
    }

    private void generateSprite() {
        com.badlogic.gdx.graphics.Texture baseTex = texture.getTexture();
        TextureRegion spriteTex = new TextureRegion(baseTex, 0, this.shift, this.width, this.height);
        this.sprite = new com.badlogic.gdx.graphics.g2d.Sprite(spriteTex);
    }

    /**
     * Render the sprite.
     * @param position The position of the sprite. (world coordinates)
     * @param size The size of the sprite. (world coordinates)
     */
    public void render(Vector3 position, Vector3 size) {
        Window window = Window.getInstance();
        GameScene gscene = (GameScene) window.getScene();
        if (gscene == null) {
            return; // Not a game scene, shouldn't happen
        }

        SpriteBatch canvas = window.getCanvas();
        Camera cam = gscene.getCamera();
        float zoom = cam.getZoom();

        Vector2 screenPos = orthoCoord2Screen(new Vector3(
            position.x - cam.getPosition().x,
            position.y + cam.getPosition().y,
            position.z - cam.getPosition().z
        )).mul(zoom);
        Vector2 screenSize = orthoSize2Screen(size).mul(zoom / 2);
        sprite.setPosition(
            screenPos.x + window.getWidth() / 2 - zoom / 2,
            screenPos.y + window.getHeight() / 2 - zoom / 2
        );
        sprite.setSize(screenSize.x, screenSize.y);
        sprite.draw(canvas);
    }

    /**
     * Convert a world coordinate to a screen coordinate.
     * @param coord The world coordinate.
     * @return The screen coordinate.
     */
    public Vector2 orthoCoord2Screen(Vector3 coord) {
        return new Vector2(
            // coord.x - coord.z / 2,
            // coord.y - coord.z / 2
            (coord.x - coord.y) / 2.0f,
            coord.z - (coord.x + coord.y) / 2.0f
        );
    }

    /**
     * Convert a world size to a screen size.
     * @param size The world size.
     * @return The screen size.
     */
    public Vector2 orthoSize2Screen(Vector3 size) {
        return new Vector2(
            size.x + size.z,
            size.y + size.z
        );
    }

    /**
     * Get the height of the sprite.
     * @return The height of the sprite.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of the sprite.
     * @return The width of the sprite.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the texture of the sprite.
     * @return The texture of the sprite.
     */
    public Texture getTexture() {
        return texture;
    }
}
