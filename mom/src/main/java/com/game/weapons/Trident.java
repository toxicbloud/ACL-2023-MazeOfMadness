package com.game.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;

/**
 * Trident class.
 */
public class Trident extends Weapon {
    /** Trident damage amount. */
    private static final int DAMAGE = 30;
    /** Trident cooldown. */
    private static final int ATTACK_COOLDOWN = 300;
    /** Trident range. */
    private static final int RANGE = 1;

    /**
     * Trident constructor.
     */
    public Trident() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE, ItemType.WEAPON_TRIDENT);
    }

    /**
     * Trident full constructor.
     *
     * @param position The position of the Trident.
     */
    public Trident(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, ItemType.WEAPON_TRIDENT);
    }

    /**
     * Trident full constructor.
     *
     * @param position        The position of the Trident.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Trident(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, ItemType.WEAPON_TRIDENT);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Trident(this.getPosition(), true);
    }

}
