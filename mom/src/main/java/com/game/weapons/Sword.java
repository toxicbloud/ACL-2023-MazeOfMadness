package com.game.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;

/**
 * Sword class.
 * This is the player fist class.
 */
public class Sword extends Weapon {
    /** Sword damage amount. */
    private static final int DAMAGE = 20;
    /** Sword cooldown. */
    private static final int ATTACK_COOLDOWN = 150;
    /** Sword range. */
    private static final int RANGE = 2;

    /**
     * Sword constructor.
     */
    public Sword() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE, ItemType.WEAPON_SWORD);
    }

    /**
     * Sword full constructor.
     *
     * @param position The position of the sword.
     */
    public Sword(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, ItemType.WEAPON_SWORD);
    }

    /**
     * Sword full constructor.
     *
     * @param position        The position of the sword.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Sword(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, ItemType.WEAPON_SWORD);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Sword(this.getPosition(), true);
    }
}
