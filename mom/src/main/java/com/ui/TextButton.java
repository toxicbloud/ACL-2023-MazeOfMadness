package com.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
        // draw text center of button
        batch.begin();
        font.draw(batch, text, getPosition().x, getPosition().y);
        batch.end();
    }

}
