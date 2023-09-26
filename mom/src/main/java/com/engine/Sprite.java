package com.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.renderer.Camera;
import com.renderer.GameScene;

public class Sprite {
    private int width;
    private int height;
    private Texture texture;
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;

    public Sprite(Texture texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture.getTexture());
    }

    public void render(Vector3 position, Vector3 size) {
        GameScene gscene = (GameScene) Window.GetInstance().getScene();
        if (gscene == null) return; // Not a game scene, shouldn't happen
        SpriteBatch canvas = Window.GetInstance().getCanvas();
        Camera cam = gscene.getCamera();

        Vector2 screenPos = OrthoCoord2Screen(position.sub(cam.getPosition()));
        Vector2 screenSize = OrthoSize2Screen(size);
        sprite.setPosition(screenPos.x, screenPos.y);
        sprite.setSize(screenSize.x, screenSize.y);
        sprite.draw(canvas);
    }

    public Vector2 OrthoCoord2Screen(Vector3 coord) {
        return new Vector2(
            coord.x - coord.z / 2,
            coord.y - coord.z / 2
        );
    }

    public Vector2 OrthoSize2Screen(Vector3 size) {
        return new Vector2(
            size.x + size.z,
            size.y + size.z
        );
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Texture getTexture() {
        return texture;
    }
}
