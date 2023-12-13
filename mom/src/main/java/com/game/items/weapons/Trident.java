package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Potion abstract class.
 */
public class Trident extends Weapon {
    /**
     * Trident constructor.
     */
    public Trident() {
        super(2 * SPRITE_SIZE, new Vector3(), ItemType.TRIDENT);
    }

    /**
     * Trident item constructor.
     *
     * @param position Position of the item inside the maze.
     */
    public Trident(Vector3 position) {
        super(2 * SPRITE_SIZE, position, ItemType.TRIDENT);
    }

    @Override
    protected void equipWeapon(Player player) {
        player.setWeapon(new com.game.weapons.Trident());
    }
}
