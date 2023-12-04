package com.game.weapons;

import com.game.ItemType;

/**
 * ZombieFist class.
 * This is the zombie fist class.
 */
public class ZombieFist extends WeaponFist {
    /** ZombieFist damage amout. */
    private static final int DAMAGE = 5;
    /** ZombieFist cooldown. */
    private static final int ATTACK_COOLDOWN = 1000;

    /**
     * ZombieFist constructor.
     */
    public ZombieFist() {
        super(ZombieFist.DAMAGE, ZombieFist.ATTACK_COOLDOWN, ItemType.WEAPON_ZOMBIE_FIST);
    }

    /**
     * ZombieFist constructor.
     *
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public ZombieFist(boolean hasDoubleDamage) {
        super(ZombieFist.DAMAGE * 2, ZombieFist.ATTACK_COOLDOWN, hasDoubleDamage, ItemType.WEAPON_ZOMBIE_FIST);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new ZombieFist(true);
    }
}
