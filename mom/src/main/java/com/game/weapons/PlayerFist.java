package com.game.weapons;

/**
 * PlayerFist class.
 * This is the player fist class.
 */
public class PlayerFist extends WeaponFist {
    /** PlayerFist damage amount. */
    private static final int DAMAGE = 10;
    /** PlayerFist cooldown. */
    private static final int ATTACK_COOLDOWN = 250;

    /**
     * PlayerFist constructor.
     */
    public PlayerFist() {
        super(PlayerFist.DAMAGE, PlayerFist.ATTACK_COOLDOWN);
    }

    /**
     * PlayerFist constructor.
     *
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public PlayerFist(boolean hasDoubleDamage) {
        super(hasDoubleDamage ? PlayerFist.DAMAGE * 2 : PlayerFist.DAMAGE, PlayerFist.ATTACK_COOLDOWN,
                hasDoubleDamage);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new PlayerFist(true);
    }
}
