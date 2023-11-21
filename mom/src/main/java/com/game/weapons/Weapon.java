package com.game.weapons;

import com.engine.Sprite;
import com.engine.Texture;
import com.game.Item;
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

    /**
     * Weapon constructor.
     *
     * @param damage   The damage amount.
     * @param cooldown The cooldown between two attacks.
     * @param range    The range.
     */
    protected Weapon(int damage, int cooldown, float range) {
        super(new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE));
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
        living.takeDamage(this.damage);
        return true;
    }

    /**
     * Update the weapon.
     */
    public void update() {

    }
}
