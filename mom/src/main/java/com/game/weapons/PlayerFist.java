package com.game.weapons;

/**
 * PlayerFist class.
 * This is the player fist class.
 */
public class PlayerFist extends WeaponFist {
    /** PlayerFist damage amount. */
    private static final int DAMAGE = 1;
    /** PlayerFist cooldown. */
    private static final int ATTACK_COOLDOWN = 250;

    /**
     * PlayerFist constructor.
     */
    public PlayerFist() {
        super(PlayerFist.DAMAGE, PlayerFist.ATTACK_COOLDOWN);
    }
}
