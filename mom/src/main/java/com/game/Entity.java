package com.game;

import com.engine.Evolvable;
import com.engine.Sprite;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.controllers.Controller;
import com.game.tiles.Tile;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * Entity class.
 * This is the base class for all entities.
 */
public abstract class Entity implements Evolvable {
    /** Default sprite size. */
    public static final int SPRITE_SIZE = 64;

    /** All entities list. */
    public static final List<Entity> ENTITIES = new ArrayList<Entity>();

    /** Collisions border with. */
    protected static final float COLLISIONS_BORDER_WIDTH = 0.08f;

    /** Entities unique ID counter. */
    private static int idCounter;

    /** Entity position. */
    private Vector3 position;
    /** Entity size. */
    private Vector3 size;
    /** Entity sprite. */
    private Sprite sprite;

    /** Updated flag. To indicate an updated state in the entity. */
    private boolean updatedFlag;

    /** Destroy flag. To indicate that the entity should be removed. */
    private boolean destroyFlag;

    /** Entity's controller. */
    private Controller controller;

    /** Entity unique id. */
    private int id;

    /**
     * Entity constructor.
     * @param sprite The sprite to use.
     */
    protected Entity(Sprite sprite) {
        this.sprite = sprite;
        position = new Vector3();
        size = new Vector3();
        this.id = idCounter++;
        ENTITIES.add(this);
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
        this.id = idCounter++;
        ENTITIES.add(this);
    }

    /**
     * Register a controller to the entity.
     * @param c The controller to register.
     */
    public void registerController(Controller c) {
        if (this.controller != null) {
            this.controller.unregister();
        }
        this.controller = c;
    }

    /**
     * Update the entity.
     * This method is called every frame, before any render() call.
     */
    public void update() {
        this.updatedFlag = false;
        if (this.controller != null) {
            this.controller.update();
        }

        if (this.destroyFlag) {
            this.remove();
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
     * Indicates that the entity has been updated.
     * Used by child classes to indicate an updated state.
     */
    protected void indicateUpdate() {
        this.updatedFlag = true;
    }

    /**
     * Indicates if the entity has been updated.
     * @return If the entity has been updated or not.
     */
    public boolean hasBeenUpdated() {
        return this.updatedFlag;
    }

    /**
     * Indicates if the entity has been destroyed.
     * @return If the entity has been destroyed or not.
     */
    public boolean hasBeenDestroyed() {
        return this.destroyFlag;
    }

    /**
     * Move entity of delta position.
     * @param delta movement delta
     */
    public void moveBy(Vector2 delta) {
        indicateUpdate();

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
        return t != null && t.isSolid();
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
     * Get the controller of the entity.
     * @return The controller of the entity.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Get the unique id of the entity.
     * @return The unique id of the entity.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the unique id of the entity.
     * @param id The new unique id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the position of the entity.
     * @param position The new position.
     */
    public void setPosition(Vector3 position) {
        indicateUpdate();
        this.position = position;
    }

    /**
     * Set the size of the entity.
     * @param size The new size.
     */
    public void setSize(Vector3 size) {
        indicateUpdate();
        this.size = size;
    }

    /**
     * Set the sprite of the entity.
     * @param sprite The new sprite.
     */
    public void setSprite(Sprite sprite) {
        indicateUpdate();
        this.sprite = sprite;
    }

    /**
     * Destroys the entity at next maze update.
     */
    public void destroy() {
        this.indicateUpdate();
        this.destroyFlag = true;
    }

    /**
     * Remove the entity from the game.
     */
    protected abstract void remove();

    /**
     * Returns JSON representation of the entity.
     * @return JSON representation of the entity.
     */
    public abstract JSONObject toJSON();
}
