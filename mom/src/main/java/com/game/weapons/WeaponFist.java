package com.game.weapons;

import com.game.ItemType;

/**
 * WeaponFist class.
 * This is the base class for all fist weapons.
 */
public abstract class WeaponFist extends Weapon {
    /** Default fist range. */
    private static final float FIST_RANGE = 0.65f;

    /**
     * WeaponFist constructor.
     * @param damage The damage amount.
     * @param cooldown The cooldown between two attacks.
     * @param t        The type of fist
     */
    protected WeaponFist(int damage, int cooldown, ItemType t) {
        super(damage, cooldown, FIST_RANGE, t);
    }

    /**
     * WeaponFist constructor.
     * @param damage The damage amount.
     * @param cooldown The cooldown between two attacks.
     * @param hasDoubleDamage If the damages of this weapon has been doubled.
     * @param t               The type of fist
     */
    protected WeaponFist(int damage, int cooldown, boolean hasDoubleDamage, ItemType t) {
        super(damage, cooldown, FIST_RANGE, hasDoubleDamage, t);
    }
}
