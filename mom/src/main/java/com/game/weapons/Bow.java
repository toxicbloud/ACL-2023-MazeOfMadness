package com.game.weapons;

import com.engine.utils.Vector3;
import com.game.Game;
import com.game.ItemType;
import com.game.Living;
import com.game.particles.Arrow;

import java.util.List;

/**
 * Bow class.
 */
public class Bow extends Weapon {
    /** Bow damage amount. */
    private static final int DAMAGE = 30;
    /** Bow cooldown. */
    private static final int ATTACK_COOLDOWN = 500;

    /**
     * Bow constructor.
     */
    public Bow() {
        super(DAMAGE, ATTACK_COOLDOWN, Integer.MAX_VALUE, ItemType.WEAPON_BOW);
    }

    /**
     * Bow full constructor.
     *
     * @param position The position of the Bow.
     */
    public Bow(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, Integer.MAX_VALUE, false, ItemType.WEAPON_BOW);
    }

    /**
     * Bow full constructor.
     *
     * @param position        The position of the Bow.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Bow(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, Integer.MAX_VALUE, hasDoubleDamage, ItemType.WEAPON_BOW);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Bow(this.getPosition(), true);
    }

    @Override
    public boolean attack(Living living) {
        if (!handleCooldown()) {
            return false;
        }
        Game.getInstance().getMaze()
                .addParticle(new Arrow(getSprite(), getOwner().getDirectionVector(), getPosition(), getDamage()));
        return true;
    }

    @Override
    public boolean attack(List<Living> livingList) {
        if (!handleCooldown()) {
            return false;
        }
        Game.getInstance().getMaze()
                .addParticle(new Arrow(getSprite(), getOwner().getDirectionVector(), getPosition(), getDamage()));
        return true;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void update() {
    }

}
