package com.game.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;

/**
 * Teddy class.
 */
public class Teddy extends Weapon {
    /** Teddy damage amount. */
    private static final int DAMAGE = 10;
    /** Teddy cooldown. */
    private static final int ATTACK_COOLDOWN = 100;
    /** Teddy range. */
    private static final int RANGE = 1;

    /**
     * Teddy constructor.
     */
    public Teddy() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE, ItemType.WEAPON_TEDDY);
    }

    /**
     * Teddy full constructor.
     *
     * @param position The position of the Teddy.
     */
    public Teddy(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, ItemType.WEAPON_TEDDY);
    }

    /**
     * Teddy full constructor.
     *
     * @param position        The position of the Teddy.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Teddy(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, ItemType.WEAPON_TEDDY);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Teddy(this.getPosition(), true);
    }

}
