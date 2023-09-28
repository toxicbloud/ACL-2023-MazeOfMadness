package com.game.weapons;

/**
 * WeaponFist class.
 * This is the base class for all fist weapons.
 */
public abstract class WeaponFist extends Weapon {
    /** Default fist range. */
    private static final float FIST_RANGE = 0.5f;

    /**
     * WeaponFist constructor.
     * @param damage The damage amount.
     */
    protected WeaponFist(int damage) {
        super(damage, FIST_RANGE);
    }
}
