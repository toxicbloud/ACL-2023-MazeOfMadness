package com.game.weapons;

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
     */
    protected WeaponFist(int damage, int cooldown) {
        super(damage, cooldown, FIST_RANGE);
    }

    /**
     * WeaponFist constructor.
     * @param damage The damage amount.
     * @param cooldown The cooldown between two attacks.
     * @param hasDoubleDamage If the damages of this weapon has been doubled.
     */
    protected WeaponFist(int damage, int cooldown, boolean hasDoubleDamage) {
        super(damage, cooldown, FIST_RANGE, hasDoubleDamage);
    }
}
