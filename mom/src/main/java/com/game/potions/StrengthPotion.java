package com.game.potions;

import com.engine.utils.Vector3;
import com.game.Player;
import com.game.weapons.Weapon;

/**
 * StrengthPotion class.
 */
public class StrengthPotion extends Potion {

    /** Sprite shift for the strength potion sprite. */
    private static final int STRENGTH_POTION_SPRITE_SHIFT = 2 * SPRITE_SIZE;

    /**
     * Strength potion constructor.
     *
     * @param position Position of the potion inside the maze.
     */
    public StrengthPotion(Vector3 position) {
        super(STRENGTH_POTION_SPRITE_SHIFT, position);
    }

    /**
     * Strength potion constructor.
     */
    public StrengthPotion() {
        super(STRENGTH_POTION_SPRITE_SHIFT, new Vector3());
    }

    @Override
    protected void applyEffect(Player player) {
        Weapon oldWeapon = player.getWeapon();
        if (!oldWeapon.hasDoubleDamage()) {
            player.setWeapon(oldWeapon.createDoubleDamageWeapon());
        }
    }
}
