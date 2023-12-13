package com.game;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.engine.Sprite;
import com.engine.Window;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.weapons.Weapon;
import com.renderer.GameScene;

/**
 * Living class.
 * This is the base class for all living entities.
 */
public abstract class Living extends Entity {
    /**
     * Range at which the player can pick up an item.
     */
    private static final float PICKUP_RANGE = 0.5f;
    /** Number of possible directions. */
    private static final int NB_DIRECTIONS = 4;
    /**
     * Vector3 uses in the healthBar's function for the position of the healthbar.
     */
    private static final Vector3 POSITION_HEALTHBAR_SCREEN = new Vector3(1.0f, 0.0f, 2.4f);
    /**
     * Vector3 uses in the healthBar's function for the size of the healthbar.
     */
    private static final Vector3 SIZE_HEALTHBAR_SCREEN = new Vector3(0.9f, 0.04f, 0.1f);
    /**
     * Default living health for the constructor.
     */
    private static final int DEFAULT_LIVING_HEALTH = 100;
    /**
     * Value in degrees for RIGHT direction.
     */
    private static final double RIGHT_DEGREES = 0.0;
    /**
     * Value in degrees for UP direction.
     */
    private static final double UP_DEGREES = 90.0;
    /**
     * Value in degrees for LEFT direction.
     */
    private static final double LEFT_DEGREES = 180.0;
    /**
     * Value in degrees for DOWN direction.
     */
    private static final double DOWN_DEGREES = 270.0;
    /**
     * Radian to degree conversion value.
     */
    private static final double DEGREES = 360.0;
    /**
     * Maximum range of the entity's FOV.
     */
    private static final double FOV_RADIUS = 100.0;
    /**
     * Maximum range of the entity's FOV.
     */
    private static final double MAX_RANGE_FOV = 6.0;

    /** Living health. */
    private int health;
    /** Living max health. */
    private int maxHealth;
    /** Living speed. */
    private float speed;
    /** Living direction. */
    private Direction direction = Direction.DOWN;
    /** Living weapon. */
    private Weapon weapon;
    /** Color of the rendered health bar. */
    private Color healthBarColor;

    /**
     * Living direction enum.
     */
    public enum Direction {
        /** Right direction. */
        RIGHT,
        /** Down direction. */
        DOWN,
        /** Left direction. */
        LEFT,
        /** Up direction. */
        UP
    }

    /**
     * Living constructor.
     *
     * @param sprite The sprite to use.
     */
    protected Living(Sprite sprite) {
        super(sprite);
        this.health = DEFAULT_LIVING_HEALTH;
        this.maxHealth = DEFAULT_LIVING_HEALTH;
        this.healthBarColor = Color.RED;
    }

    /**
     * Living constructor with default health parameters.
     *
     * @param sprite   The sprite to use.
     * @param position The position of the living entity.
     * @param size     The size of the living entity.
     */
    protected Living(Sprite sprite, Vector3 position, Vector3 size) {
        super(sprite, position, size);
        this.health = DEFAULT_LIVING_HEALTH;
        this.maxHealth = DEFAULT_LIVING_HEALTH;
        this.healthBarColor = Color.RED;
    }

    /**
     * Living constructor.
     *
     * @param sprite    The sprite to use.
     * @param position  The position of the living entity.
     * @param size      The size of the living entity.
     * @param health    The health of the living entity.
     * @param maxHealth The max health of the living entity.
     */
    protected Living(Sprite sprite, Vector3 position, Vector3 size, int health, int maxHealth) {
        super(sprite, position, size);
        this.health = health;
        this.maxHealth = maxHealth;
        this.healthBarColor = Color.RED;
    }

    /**
     * Render the living entity.
     */
    @Override
    public void render() {
        super.render();
        this.renderHealthBar();
    }

    @Override
    public void moveBy(Vector2 delta) {
        if (!delta.isZero()) {
            updateDirection(delta);
        }
        updateSprite(delta);
        super.moveBy(delta);
    }

    private void updateDirection(Vector2 delta) {
        boolean movingOnX = Math.abs(delta.x) > Math.abs(delta.y);

        if (movingOnX) {
            if (delta.x > 0) {
                setDirection(Direction.RIGHT);
            } else {
                setDirection(Direction.LEFT);
            }
        } else {
            if (delta.y > 0) {
                setDirection(Direction.UP);
            } else {
                setDirection(Direction.DOWN);
            }
        }
    }

    private void updateSprite(Vector2 delta) {
        boolean running = !delta.isZero();
        this.getSprite().setShift(
                this.direction.ordinal() * this.getSprite().getHeight()
                        + (running ? this.getSprite().getHeight() * NB_DIRECTIONS : 0));
    }

    private void setDirection(Direction dir) {
        if (this.direction != dir) {
            this.direction = dir;
        }
    }

    /**
     * Creates a health bar.
     */
    private void renderHealthBar() {
        Window.getInstance().getCanvas().end();

        Vector2 pos = GameScene.getWorldToScreenCoordinates(getPosition().add(POSITION_HEALTHBAR_SCREEN));
        Vector2 size = GameScene.getWorldToScreenSize(SIZE_HEALTHBAR_SCREEN);

        float healthBarStatus = ((float) this.health / (float) this.maxHealth) * (size.x - 2);

        ShapeRenderer renderer = Window.getInstance().getHUD();
        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(pos.x - (size.x / 2), pos.y, size.x, size.y);
        renderer.set(ShapeType.Filled);
        renderer.setColor(this.healthBarColor);
        renderer.rect((pos.x - (size.x / 2)) + 1, pos.y + 1, healthBarStatus, size.y - 2);
        renderer.end();

        Window.getInstance().getCanvas().begin();
    }

    private static double directionToAngle(Direction direction) {
        switch (direction) {
            case RIGHT:
                return RIGHT_DEGREES;
            case UP:
                return UP_DEGREES;
            case LEFT:
                return LEFT_DEGREES;
            case DOWN:
                return DOWN_DEGREES;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }

    /**
     * Detects whether entity2 is in entity1's FOV.
     *
     * @param entity1 The entity from which the FOV emanates.
     * @param entity2 The entity we're trying to determine if it's in the FOV.
     * @return whether entity2 is in entity1's FOV.
     */
    public boolean isInFOV(Living entity1, Living entity2) {
        Vector3 entity1ToEntity2 = entity2.getPosition().sub(entity1.getPosition());
        double distance = entity1ToEntity2.len();
        if (distance > MAX_RANGE_FOV) {
            return false;
        }
        double angleBetweenEntities = Math.toDegrees(Math.atan2(entity1ToEntity2.getY(), entity1ToEntity2.getX()));
        angleBetweenEntities = (angleBetweenEntities + DEGREES) % DEGREES;
        double angleDiff = Math.abs(angleBetweenEntities - directionToAngle(entity1.getDirection()));
        return angleDiff <= FOV_RADIUS / 2 || angleDiff >= DEGREES - FOV_RADIUS / 2;
    }

    /**
     * Detects and returns a pickable item in the player's range if there is one.
     *
     * @return The first pickable item in the player's range if there is one, null
     */
    public WorldItem findItemInRange() {
        for (WorldItem i : Game.getInstance().getMaze().getItems()) {
            if (isInRange(i.getPosition(), this.getPosition()) && i.isPickable()) {
                return i;
            }
        }
        return null;
    }

    /**
     * This method checks if an item is in the range of the player.
     *
     * @param itemPosition   Position of the item to check.
     * @param playerPosition Position of the player.
     * @return If the item is in the range of the player.
     */
    private boolean isInRange(Vector3 itemPosition, Vector3 playerPosition) {
        return itemPosition.dst(playerPosition) < PICKUP_RANGE;
    }

    /**
     * Attack a living entity.
     *
     * @param living The living entity to attack.
     * @return Whether the attack was successful.
     */
    public boolean attack(Living living) {
        if (this.weapon == null) {
            return false;
        }
        return this.weapon.attack(living);
    }

    /**
     * Attack a list of living entities.
     *
     * @param livings The list of living entities to attack.
     * @return Whether the attack was successful.
     */
    public boolean attack(List<Living> livings) {
        if (this.weapon == null) {
            return false;
        }
        return this.weapon.attack(livings);
    }

    /**
     * Take damage.
     *
     * @param damage The damage amount.
     * @return Whether the living entity died.
     */
    public boolean takeDamage(int damage) {
        indicateUpdate();
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
        return this.health == 0;
    }

    /**
     * Regenerate health. If the health amount makes the entity's health higher than
     * it's maximum possible health, it
     * caps to the maximum health.
     *
     * @param h The health amount.
     */
    public void regen(int h) {
        indicateUpdate();
        if (this.getHealth() + h < this.maxHealth) {
            this.health += h;
        } else {
            this.health = this.maxHealth;
        }
    }

    /**
     * Check if the living entity is dead.
     *
     * @return Whether the living entity is dead.
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Get the direction.
     *
     * @return The direction of the entity.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the direction vector.
     *
     * @return The direction vector of the entity.
     */
    public Vector2 getDirectionVector() {
        switch (direction) {
            case RIGHT:
                return new Vector2(1, 0);
            case UP:
                return new Vector2(0, 1);
            case LEFT:
                return new Vector2(-1, 0);
            case DOWN:
                return new Vector2(0, -1);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }

    /**
     * Get the health.
     *
     * @return The health of the entity.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the max health.
     *
     * @return The max health of the entity.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the speed.
     *
     * @return The speed of the entity.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Get the weapon.
     *
     * @return The weapon of the entity.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Set the health.
     *
     * @param health The health of the entity.
     */
    public void setHealth(int health) {
        indicateUpdate();
        this.health = health;
    }

    /**
     * Set the speed.
     *
     * @param speed The speed of the entity.
     */
    public void setSpeed(float speed) {
        indicateUpdate();
        this.speed = speed;
    }

    /**
     * Set the weapon.
     *
     * @param weapon The weapon of the entity.
     */
    public void setWeapon(Weapon weapon) {
        indicateUpdate();
        this.weapon = weapon;
    }

    /**
     * Sets the health bar color.
     *
     * @param healthBarColor new color for the health bar.
     */
    protected void setHealthBarColor(Color healthBarColor) {
        this.healthBarColor = healthBarColor;
    }

    @Override
    protected void remove() {
        // SHOULD NO BE ABLE TO REMOVE LIVING IF NOT MONSTER
    }
}
