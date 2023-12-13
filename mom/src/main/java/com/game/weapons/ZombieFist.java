package com.game.weapons;

/**
 * ZombieFist class.
 * This is the zombie fist class.
 */
public class ZombieFist extends WeaponFist {
    /** ZombieFist damage amout. */
    private static final int DAMAGE = 5;
    /** ZombieFist cooldown. */
    private static final int ATTACK_COOLDOWN = 750;

    /**
     * ZombieFist constructor.
     */
    public ZombieFist() {
        super(ZombieFist.DAMAGE, ZombieFist.ATTACK_COOLDOWN);
    }

    /**
     * ZombieFist constructor.
     *
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public ZombieFist(boolean hasDoubleDamage) {
        super(hasDoubleDamage ? ZombieFist.DAMAGE * 2 : ZombieFist.DAMAGE, ZombieFist.ATTACK_COOLDOWN, hasDoubleDamage);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new ZombieFist(true);
    }
}
