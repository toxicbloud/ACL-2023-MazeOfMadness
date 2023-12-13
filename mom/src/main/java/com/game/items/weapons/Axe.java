package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Axe item class.
 */
public class Axe extends Weapon {
    /**
     * Sprite shift.
     */
    private static final int SPRITE_SHIFT = 4;

    /**
     * Axe constructor.
     *
     * @param position Position of the item inside the maze.
     */
    public Axe(Vector3 position) {
        super(SPRITE_SHIFT * SPRITE_SIZE, position, ItemType.AXE);
    }

    @Override
    protected void equipWeapon(Player player) {
        player.setWeapon(new com.game.weapons.Axe());
    }

}
