package com.game.weapons;

/**
 * GhostFist class.
 * This is the ghost fist class.
 */
public class GhostFist extends WeaponFist {
    /** GhostFist damage amout. */
    private static final int DAMAGE = 8;
    /** GhostFist cooldown. */
    private static final int ATTACK_COOLDOWN = 600;

    /**
     * GhostFist constructor.
     */
    public GhostFist() {
        super(GhostFist.DAMAGE, GhostFist.ATTACK_COOLDOWN);
    }

    /**
     * GhostFist constructor.
     *
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public GhostFist(boolean hasDoubleDamage) {
        super(hasDoubleDamage ? GhostFist.DAMAGE * 2 : GhostFist.DAMAGE, GhostFist.ATTACK_COOLDOWN, hasDoubleDamage);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new GhostFist(true);
    }
}
