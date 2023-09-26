package com.game;

import com.engine.Sprite;
import com.engine.utils.Vector3;

public abstract class Item extends Entity {
    protected Item(Sprite sprite) {
        super(sprite);
    }

    protected Item(Sprite sprite, Vector3 position, Vector3 size) {
        super(sprite, position, size);
    }
}
