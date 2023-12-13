package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Sword item class.
 */
public class Sword extends Weapon {
    /**
     * Sword constructor.
     */
    public Sword() {
        super(0, new Vector3(), ItemType.SWORD);
    }

    /**
     * Sword constructor.
     *
     * @param position The position of the sword in the maze.
     */
    public Sword(Vector3 position) {
        super(0, position, ItemType.SWORD);
    }

    @Override
    protected void equipWeapon(Player player) {
        player.setWeapon(new com.game.weapons.Sword(player.getPosition()));
    }

}
