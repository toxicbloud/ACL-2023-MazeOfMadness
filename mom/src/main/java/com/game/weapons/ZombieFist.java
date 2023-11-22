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
}
