package com.game;

import com.engine.Evolvable;
import com.engine.Sprite;
import com.engine.utils.Vector3;

public abstract class Entity implements Evolvable {
    private Vector3 position;
    private Vector3 size;
    private Sprite sprite;

    protected Entity(Sprite sprite) {
        this.sprite = sprite;
        position = new Vector3();
        size = new Vector3();
    }

    protected Entity(Sprite sprite, Vector3 position, Vector3 size) {
        this.sprite = sprite;
        this.position = position;
        this.size = size;
    }

    public abstract void update();

    public void render() {
        sprite.render(position, size);
    }

    public float distance(Entity entity) {
        return this.position.dst(entity.position);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getSize() {
        return size;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setSize(Vector3 size) {
        this.size = size;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
