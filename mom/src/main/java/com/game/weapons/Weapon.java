package com.game.weapons;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Time;
import com.engine.utils.Vector3;
import com.game.Item;
import com.game.ItemType;
import com.game.Living;

/**
 * Weapon class.
 * This is the base class for all weapons.
 */
public abstract class Weapon extends Item {
    /** Weapon damage amount. */
    private int damage;
    /** Weapon cooldown. */
    private int cooldown;
    /** Weapon range. */
    private float range;
    /** The last time when the player attacks. */
    private long lastAttackTime;
    /** If the weapon's damage has been multiplied by a potion. */
    private final boolean hasDoubleDamage;

    /**
     * Weapon constructor.
     *
     * @param damage          The damage amount.
     * @param cooldown        The cooldown between two attacks.
     * @param range           The range.
     */
    protected Weapon(int damage, int cooldown, float range) {
        super(new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE), ItemType.WEAPON_PLAYER_FIST);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.hasDoubleDamage = false;
    }

    /**
     * Weapon constructor.
     *
     * @param damage          The damage amount.
     * @param cooldown        The cooldown between two attacks.
     * @param range           The range.
     * @param hasDoubleDamage If the power of this weapon has been doubled.
     */
    protected Weapon(int damage, int cooldown, float range, boolean hasDoubleDamage) {
        super(new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE), ItemType.WEAPON_PLAYER_FIST);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.hasDoubleDamage = hasDoubleDamage;
    }

    /**
     * Weapon constructor.
     *
     * @param position The position of the weapon.
     * @param damage   The damage amount.
     * @param cooldown The cooldown between two attacks.
     * @param range    The range.
     */
    protected Weapon(Vector3 position, int damage, int cooldown, float range) {
        super(new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE), position, new Vector3(1, 1, 1));
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
    }

    /**
     * Get the damage amount.
     *
     * @return The damage amount.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Get the cooldown of the weapon.
     *
     * @return The cooldown of the weapon.
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Get the range.
     *
     * @return The range.
     */
    public float getRange() {
        return range;
    }

    /**
     * Attack a living entity.
     *
     * @param living The living entity to attack.
     * @return Whether the attack was successful.
     */
    public boolean attack(Living living) {
        if (this.distance(living) > this.range) {
            return false;
        }
        if (Time.getInstance().getCurrentTime() - lastAttackTime > getCooldown()) {
            living.takeDamage(this.damage);
            lastAttackTime = Time.getInstance().getCurrentTime();
        }
        return true;
    }

    /**
     * Update the weapon.
     */
    public void update() {

    }

    /**
     * Returns if the weapon's damage has been doubled.
     * @return If the weapon's damage has been doubled.
     */
    public boolean hasDoubleDamage() {
        return hasDoubleDamage;
    }

    /**
     * This method creates a new weapon that has the double of the damages of the current weapon.
     *
     * @return A double damage weapon.
     */
    public abstract Weapon createDoubleDamageWeapon();
}
