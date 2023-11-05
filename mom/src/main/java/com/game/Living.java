package com.game;

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
    /** Number of possible directions. */
    private static final int NB_DIRECTIONS = 4;
    /** Scale value for the health bar. */
    private static final int WIDTH_SCALE_VALUE = 1280;
    /** Scale value for the health bar. */
    private static final int HEIGHT_SCALE_VALUE = 720;
    /** Vector3 uses in the healthBar's function for the position of the healthbar. */
    private static final Vector3 POSITION_HEALTHBAR_SCREEN = new Vector3(1.0f, 0.0f, 2.4f);
    /** Vector3 uses in the healthBar's function for the size of the healthbar. */
    private static final Vector3 SIZE_HEALTHBAR_SCREEN = new Vector3(0.9f, 0.04f, 0.1f);

    /** Living health. */
    private int health;
    /** Living max health. */
    private int maxHealth;
    /** Living speed. */
    private int speed;
    /** Living direction. */
    private Direction direction = Direction.DOWN;
    /** Living weapon. */
    private Weapon weapon;

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
     * @param sprite The sprite to use.
     */
    protected Living(Sprite sprite) {
        super(sprite);
    }

    /**
     * Living constructor.
     * @param sprite The sprite to use.
     * @param position The position of the living entity.
     * @param size The size of the living entity.
     */
    protected Living(Sprite sprite, Vector3 position, Vector3 size) {
        super(sprite, position, size);
    }

    /**
     * Render the living entity.
     */
    @Override
    public void render() {
        super.render();
        Window.getInstance().getCanvas().end();

        this.healthBar();

        Window.getInstance().getCanvas().begin();
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
            + (running ? this.getSprite().getHeight() * NB_DIRECTIONS : 0)
        );
    }

    private void setDirection(Direction dir) {
        if (this.direction != dir) {
            this.direction = dir;
        }
    }

    /**
     * Attack a living entity.
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
     * Take damage.
     * @param damage The damage amount.
     * @return Whether the living entity died.
     */
    public boolean takeDamage(int damage) {
        this.health -= damage;
        return this.health <= 0;
    }

    /**
     * Create an health bar.
     */
    public void healthBar() {
        float screenWidth = Window.getInstance().getWidth();
        float screenHeight = Window.getInstance().getHeight();
        float scaleX = screenWidth / WIDTH_SCALE_VALUE;
        float scaleY = screenHeight / HEIGHT_SCALE_VALUE;

        Vector2 pos = GameScene.getWorldToScreenCoordinates(getPosition().add(POSITION_HEALTHBAR_SCREEN));
        Vector2 size = GameScene.getWorldToScreenSize(SIZE_HEALTHBAR_SCREEN);
        Vector2 scaledPos = new Vector2(pos.x / scaleX, pos.y / scaleY);

        float healthBarStatus = (50f / 100f) * (size.x - 2);

        ShapeRenderer renderer = Window.getInstance().getHUD();
        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(scaledPos.x - (size.x / 2), scaledPos.y, size.x, size.y);
        renderer.set(ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.rect((scaledPos.x - (size.x / 2)) + 1, scaledPos.y + 1, healthBarStatus, size.y - 2);
        renderer.end();
    }

    /**
     * Regenerate health.
     * @param h The health amount.
     */
    public void regen(int h) {
        this.health += h;
    }

    /**
     * Check if the living entity is dead.
     * @return Whether the living entity is dead.
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Get the health.
     * @return The health of the entity.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the max health.
     * @return The health of the entity.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the speed.
     * @return The speed of the entity.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Get the weapon.
     * @return The weapon of the entity.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Set the health.
     * @param health The health of the entity.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Set the max health.
     * @param maxHealth The max health of the entity.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Set the speed.
     * @param speed The speed of the entity.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Set the weapon.
     * @param weapon The weapon of the entity.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
