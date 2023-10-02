package com.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.engine.utils.Vector2;

public class TextButton extends Button {

    private String text;
    private BitmapFont font;
    private Batch batch;

    public TextButton(Vector2 position, Vector2 size, String text) {
        super(position, size);
        this.text = text;
        this.font = new BitmapFont();
    }

    @Override
    public void render() {
        super.render();
        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        com.engine.Window window = com.engine.Window.getInstance();
        float windowWidth = window.getWidth();
        float windowHeight = window.getHeight();
        font.draw(batch, text, getPosition().x * windowWidth - getSize().x / 2,
                windowHeight - getPosition().y * windowHeight);
        batch.end();
    }

    @Override
    void create() {
        super.create();
        batch = new SpriteBatch();
    }

}
