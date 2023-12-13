package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Teddy item class.
 */
public class Teddy extends Weapon {

    /**
     * Sprite shift.
     */
    private static final int SPRITE_SHIFT = 5;

    /**
     * Teddy constructor.
     *
     * @param position Position of the item inside the maze.
     */
    public Teddy(Vector3 position) {
        super(SPRITE_SHIFT * SPRITE_SIZE, position, ItemType.TEDDY);
    }

    @Override
    protected void equipWeapon(Player player) {
        player.setWeapon(new com.game.weapons.Teddy());
    }

}
