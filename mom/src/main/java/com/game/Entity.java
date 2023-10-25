package com.game;

import com.engine.Evolvable;
import com.engine.Sprite;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.controllers.Controller;
import com.game.tiles.Tile;

/***
 * Entity class.
 * This is the base class for all entities.
 */
public abstract class Entity implements Evolvable {
    /** Default sprite size. */
    protected static final int SPRITE_SIZE = 64;

    /** Collisions border with. */
    protected static final float COLLISIONS_BORDER_WIDTH = 0.08f;

    /** Entity position. */
    private Vector3 position;
    /** Entity size. */
    private Vector3 size;
    /** Entity sprite. */
    private Sprite sprite;

    /** Entity's controller. */
    private Controller controller;

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
     * Register a controller to the entity.
     * @param c The controller to register.
     */
    public void registerController(Controller c) {
        this.controller = c;
    }

    /**
     * Update the entity.
     * This method is called every frame, before any render() call.
     */
    public void update() {
        if (this.controller != null) {
            this.controller.update();
        }
    }

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
    public void moveBy(Vector2 delta) {
        float floorDiff = Math.abs(this.position.z - (int) this.position.z);
        if (floorDiff < COLLISIONS_BORDER_WIDTH || floorDiff > (1 - COLLISIONS_BORDER_WIDTH)) {
            Vector2 collideMovement = getCollideMovement(delta);
            this.position = this.position.add(new Vector3(
                collideMovement.x,
                collideMovement.y,
                0
            ));
        } else {
            Vector3 downPos = new Vector3(this.position.x, this.position.y, (int) this.position.z);
            Vector3 upPos = new Vector3(this.position.x, this.position.y, (int) this.position.z + 1);

            Vector2 collideMovementDown = getCollideMovement(delta, downPos);
            Vector2 collideMovementUp = getCollideMovement(delta, upPos);

            this.position = this.position.add(new Vector3(
                (Math.abs(collideMovementDown.x) < Math.abs(collideMovementUp.x))
                    ? collideMovementDown.x
                    : collideMovementUp.x,
                (Math.abs(collideMovementDown.y) < Math.abs(collideMovementUp.y))
                    ? collideMovementDown.y
                    : collideMovementUp.y,
                0
            ));
        }
    }

    /*
     * Returns the movement that the entity can do without colliding with the maze.
     * @param move The movement to do.
     * @return The movement that the entity can do without colliding with the maze.
     */
    private Vector2 getCollideMovement(Vector2 move) {
        return new Vector2(
            getCollideMovementX(move.x, this.position),
            getCollideMovementY(move.y, this.position)
        );
    }

    /*
     * Returns the movement that the entity can do without colliding with the maze.
     * @param move The movement to do.
     * @param pos The position from where the movement begins.
     * @return The movement that the entity can do without colliding with the maze.
     */
    private Vector2 getCollideMovement(Vector2 move, Vector3 pos) {
        return new Vector2(
            getCollideMovementX(move.x, pos),
            getCollideMovementY(move.y, pos)
        );
    }

    /**
     * Returns the x movement that the entity can do without colliding with the maze.
     * @param moveX The x movement to do.
     * @param origin The position from where the movement begins.
     * @return The x movement that the entity can do without colliding with the maze.
     */
    private float getCollideMovementX(float moveX, Vector3 origin) {
        if (moveX == 0) {
            return 0;
        }

        Vector2 p1;
        Vector2 p2;
        if (moveX > 0) {
            p1 = new Vector2(origin.x + size.x + COLLISIONS_BORDER_WIDTH, origin.y + COLLISIONS_BORDER_WIDTH);
            p2 = new Vector2(origin.x + size.x + COLLISIONS_BORDER_WIDTH, origin.y + size.y - COLLISIONS_BORDER_WIDTH);
        } else {
            p1 = new Vector2(origin.x - COLLISIONS_BORDER_WIDTH, origin.y + COLLISIONS_BORDER_WIDTH);
            p2 = new Vector2(origin.x - COLLISIONS_BORDER_WIDTH, origin.y + size.y - COLLISIONS_BORDER_WIDTH);
        }

        boolean collidesP1 = tileCollides(Game.getInstance().getMaze().getTile(new Vector3(p1.x, p1.y, origin.z)));
        boolean collidesP2 = tileCollides(Game.getInstance().getMaze().getTile(new Vector3(p2.x, p2.y, origin.z)));
        if (collidesP1 || collidesP2) {
            return 0; // TODO : Get the distance to the wall
        } else {
            return moveX;
        }
    }

    /**
     * Returns the y movement that the entity can do without colliding with the maze.
     * @param moveY The y movement to do.
     * @param origin The position from where the movement begins.
     * @return The y movement that the entity can do without colliding with the maze.
     */
    private float getCollideMovementY(float moveY, Vector3 origin) {
        if (moveY == 0) {
            return 0;
        }

        Vector2 p1;
        Vector2 p2;
        if (moveY > 0) {
            p1 = new Vector2(origin.x + COLLISIONS_BORDER_WIDTH, origin.y + size.y + COLLISIONS_BORDER_WIDTH);
            p2 = new Vector2(origin.x + size.x - COLLISIONS_BORDER_WIDTH, origin.y + size.y + COLLISIONS_BORDER_WIDTH);
        } else {
            p1 = new Vector2(origin.x + COLLISIONS_BORDER_WIDTH, origin.y - COLLISIONS_BORDER_WIDTH);
            p2 = new Vector2(origin.x + size.x - COLLISIONS_BORDER_WIDTH, origin.y - COLLISIONS_BORDER_WIDTH);
        }

        boolean collidesP1 = tileCollides(Game.getInstance().getMaze().getTile(new Vector3(p1.x, p1.y, origin.z)));
        boolean collidesP2 = tileCollides(Game.getInstance().getMaze().getTile(new Vector3(p2.x, p2.y, origin.z)));
        if (collidesP1 || collidesP2) {
            return 0; // TODO : Get the distance to the wall
        } else {
            return moveY;
        }
    }

    /**
     * Returns if the Entity can go through the given Tile.
     * @param t The tile to test
     * @return If the Entity can go through the Tile.
     */
    protected boolean tileCollides(Tile t) {
        return t.isSolid();
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
