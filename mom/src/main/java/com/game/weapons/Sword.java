package com.game.weapons;

import com.engine.utils.Vector3;

/**
 * Sword class.
 * This is the player fist class.
 */
public class Sword extends Weapon {
    /** Sword damage amount. */
    private static final int DAMAGE = 5;
    /** Sword cooldown. */
    private static final int ATTACK_COOLDOWN = 150;
    /** Sword range. */
    private static final int RANGE = 2;

    /**
     * Sword constructor.
     */
    public Sword() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE);
    }

    /**
     * Sword full constructor.
     * @param position The position of the sword.
     */
    public Sword(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE);
    }
}
