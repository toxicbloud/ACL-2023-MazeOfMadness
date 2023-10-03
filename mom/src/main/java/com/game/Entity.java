package com.game;

import com.engine.Evolvable;
import com.engine.Sprite;
import com.engine.utils.Vector3;

/***
 * Entity class.
 * This is the base class for all entities.
 */
public abstract class Entity implements Evolvable {
    /** Default sprite size. */
    protected static final int SPRITE_SIZE = 16;

    /** Entity position. */
    private Vector3 position;
    /** Entity size. */
    private Vector3 size;
    /** Entity sprite. */
    private Sprite sprite;

    /**
     * Entity constructor.
     * @param sprite The sprite to use.
     */
    protected Entity(Sprite sprite) {
        this.sprite = sprite;
        position = new Vector3();
        size = new Vector3();
    }

    /**
     * Entity constructor.
     * @param sprite The sprite to use.
     * @param position The position of the entity.
     * @param size The size of the entity.
     */
    protected Entity(Sprite sprite, Vector3 position, Vector3 size) {
        this.sprite = sprite;
        this.position = position;
        this.size = size;
    }

    /**
     * Update the entity.
     * This method is called every frame, before any render() call.
     */
    public abstract void update();

    /**
     * Render the entity.
     * This method is called every frame, after all update() calls.
     */
    public void render() {
        if (this.sprite != null) {
            sprite.render(position, size);
        }
    }

    /**
     * Move entity of delta position.
     * @param delta movement delta
     */
    public void move(Vector3 delta) {
        this.position = this.position.add(delta);
    }

    /**
     * Get the distance between this entity and another.
     * @param entity The other entity.
     * @return The distance between the two entities.
     */
    public float distance(Entity entity) {
        return this.position.dst(entity.position);
    }

    /**
     * Get the position of the entity.
     * @return The position of the entity.
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Get the size of the entity.
     * @return The size of the entity.
     */
    public Vector3 getSize() {
        return size;
    }

    /**
     * Get the sprite of the entity.
     * @return The sprite of the entity.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Set the position of the entity.
     * @param position The new position.
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Set the size of the entity.
     * @param size The new size.
     */
    public void setSize(Vector3 size) {
        this.size = size;
    }

    /**
     * Set the sprite of the entity.
     * @param sprite The new sprite.
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
