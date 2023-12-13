package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Bow item class.
 */
public class Bow extends Weapon {
    /**
     * SPRITE_SHIFT.
     */
    private static final int SPRITE_SHIFT = 3;

    /**
     * @param position The position of the bow in the maze.
     */
    public Bow(Vector3 position) {
        super(SPRITE_SHIFT * SPRITE_SIZE, position, ItemType.BOW);
    }

    @Override
    protected void equipWeapon(Player player) {
        player.setWeapon(new com.game.weapons.Bow(player.getPosition()));
    }

}
