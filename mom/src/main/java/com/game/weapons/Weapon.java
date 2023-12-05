package com.game.weapons;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Time;
import com.engine.utils.Vector3;
import com.game.Item;
import com.game.ItemType;
import com.game.Living;
import com.game.Player;

import java.util.List;

/**
 * Weapon class.
 * This is the base class for all weapons.
 */
public abstract class Weapon extends Item {
    /** Weapon damage amount. */
    private int damage;
    /** Weapon cooldown. */
    private int cooldown;
    /** Last attack time. */
    private long lastAttackTime;
    /** Weapon range. */
    private float range;
    /** If the weapon's damage has been multiplied by a potion. */
    private final boolean hasDoubleDamage;
    /** The player that picked up the weapon. */
    private Player owner;

    /**
     * Weapon constructor.
     *
     * @param damage   The damage amount.
     * @param cooldown The cooldown between two attacks.
     * @param range    The range.
     * @param t        The type of the weapon.
     */
    protected Weapon(int damage, int cooldown, float range, ItemType t) {
        super(new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE), t);
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
     * @param t               The type of the weapon.
     */
    protected Weapon(int damage, int cooldown, float range, boolean hasDoubleDamage, ItemType t) {
        super(new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE), t);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.hasDoubleDamage = hasDoubleDamage;
    }

    /**
     * Weapon constructor.
     *
     * @param position        The position of the weapon.
     * @param damage          The damage amount.
     * @param cooldown        The cooldown between two attacks.
     * @param range           The range.
     * @param hasDoubleDamage If the power of this weapon has been doubled.
     * @param t               The type of the weapon.
     */
    protected Weapon(Vector3 position, int damage, int cooldown, float range, boolean hasDoubleDamage, ItemType t) {
        super(
                new Sprite(new Texture("images/weapon.png"), SPRITE_SIZE, SPRITE_SIZE),
                position,
                new Vector3(1, 1, 1),
                t);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.hasDoubleDamage = hasDoubleDamage;
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
     * handle the cooldown of the weapon.
     *
     * @return true if the cooldown is over, false otherwise.
     */
    protected boolean handleCooldown() {
        long currentTime = Time.getInstance().getCurrentTime();
        if (currentTime - this.lastAttackTime < this.cooldown) {
            return false;
        }
        this.lastAttackTime = currentTime;
        return true;
    }

    /**
     * Attack a living entity.
     *
     * @param living The living entity to attack.
     * @return Whether the attack was successful.
     */
    public boolean attack(Living living) {
        if (!handleCooldown()) {
            return false;
        }
        if (this.distance(living) > this.range) {
            return false;
        }
        living.takeDamage(this.damage);
        return true;
    }

    /**
     * Attack a list of living entities.
     *
     * @param livingList The list of living entities to attack.
     * @return Whether the attack was successful.
     */
    public boolean attack(List<Living> livingList) {
        if (!handleCooldown()) {
            return false;
        }
        boolean successful = false;
        for (Living l : livingList) {
            if (this.distance(l) <= this.range) {
                l.takeDamage(this.damage);
                successful = true;
            }
        }
        return successful;
    }

    /**
     * Update the weapon.
     */
    public void update() {

    }

    /**
     * Returns if the weapon's damage has been doubled.
     *
     * @return If the weapon's damage has been doubled.
     */
    public boolean hasDoubleDamage() {
        return hasDoubleDamage;
    }

    /**
     * This method creates a new weapon that has the double of the damages of the
     * current weapon.
     *
     * @return A double damage weapon.
     */
    public abstract Weapon createDoubleDamageWeapon();

    @Override
    public void interact(Player player) {
        this.owner = player;
        player.setWeapon(this);
    }

    protected Living getOwner() {
        return owner;
    }

}
