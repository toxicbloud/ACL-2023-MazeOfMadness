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
    /** Animation frame displayed time in seconds.  */
    private static final float ANIMATION_FRAME_DELAY = 1 / 30.0f;
    /** The width of the sprite. */
    private int width;
    /** The height of the sprite. */
    private int height;
    /** The x shift in pixels to get the sprite (animation progress). */
    private int shiftX;
    /** The y shift in pixels to get the sprite (sprite index). */
    private int shiftY;
    /** The time counter for the current frame display time. */
    private float frameTimeCounter;
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
        this.shiftY = 0;
        this.shiftX = 0;
        this.frameTimeCounter = 0;
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
        this.shiftY = shift;
        this.shiftX = 0;
        this.frameTimeCounter = 0;
        this.generateSprite();
    }

    private void generateSprite() {
        com.badlogic.gdx.graphics.Texture baseTex = texture.getTexture();
        TextureRegion spriteTex = new TextureRegion(baseTex, this.shiftX, this.shiftY, this.width, this.height);
        this.sprite = new com.badlogic.gdx.graphics.g2d.Sprite(spriteTex);
    }

    private void updateSpriteAnimation() {
        if (this.frameTimeCounter < ANIMATION_FRAME_DELAY) {
            this.frameTimeCounter += Time.getInstance().getDeltaTime();
            return;
        } else {
            this.frameTimeCounter -= ANIMATION_FRAME_DELAY;
            this.shiftX = (this.shiftX + this.width) % texture.getTexture().getWidth();
            generateSprite();
        }
    }

    /**
     * Render the sprite.
     * @param position The position of the sprite. (world coordinates)
     * @param size The size of the sprite. (world coordinates)
     */
    public void render(Vector3 position, Vector3 size) {
        updateSpriteAnimation();

        Window window = Window.getInstance();
        SpriteBatch canvas = window.getCanvas();

        Vector2 screenPos = GameScene.getWorldToScreenCoordinates(position);
        Vector2 screenSize = GameScene.getWorldToScreenSize(size);
        sprite.setPosition(screenPos.x, screenPos.y);
        sprite.setSize(screenSize.x, screenSize.y);
        sprite.draw(canvas);
    }

    /**
     * Set the shift of the sprite.
     * @param shift The shift of the sprite.
     */
    public void setShift(int shift) {
        this.shiftY = shift;
    }

    /**
     * Sets the frame animation counter.
     * @param counter The animation's frame number.
     */
    public void setFrameCounter(int counter) {
        this.shiftX = this.width * counter % texture.getTexture().getWidth();
    }

    /**
     * Get the shift of the sprite.
     * @return The shift of the sprite.
     */
    public int getShift() {
        return this.shiftY;
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
