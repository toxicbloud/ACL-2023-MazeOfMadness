package com.game.weapons;

import com.game.ItemType;
import com.game.Player;

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
        super(PlayerFist.DAMAGE, PlayerFist.ATTACK_COOLDOWN, ItemType.WEAPON_PLAYER_FIST);
    }

    /**
     * PlayerFist constructor.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public PlayerFist(boolean hasDoubleDamage) {
        super(PlayerFist.DAMAGE * 2, PlayerFist.ATTACK_COOLDOWN, hasDoubleDamage, ItemType.WEAPON_PLAYER_FIST);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new PlayerFist(true);
    }
}
