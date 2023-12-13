package com.game.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;

/**
 * Axe class.
 */
public class Axe extends Weapon {
    /** Axe damage amount. */
    private static final int DAMAGE = 30;
    /** Axe cooldown. */
    private static final int ATTACK_COOLDOWN = 300;
    /** Axe range. */
    private static final int RANGE = 1;

    /**
     * Axe constructor.
     */
    public Axe() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE, ItemType.AXE);
    }

    /**
     * Axe full constructor.
     *
     * @param position The position of the axe.
     */
    public Axe(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, ItemType.AXE);
    }

    /**
     * Axe full constructor.
     *
     * @param position        The position of the axe.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Axe(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, ItemType.AXE);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Axe(this.getPosition(), true);
    }

}
