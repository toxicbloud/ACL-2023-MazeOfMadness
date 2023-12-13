package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Bomb item class.
 */
public class Bomb extends Weapon {
    /**
     * Bomb constructor.
     */
    public Bomb() {
        super(1 * SPRITE_SIZE, new Vector3(), ItemType.BOMB);
    }

    /**
     * Bomb constructor.
     *
     * @param position The position of the bomb in the maze.
     */
    public Bomb(Vector3 position) {
        super(1 * SPRITE_SIZE, position, ItemType.BOMB);
    }

    @Override
    protected void equipWeapon(Player player) {
        player.setWeapon(new com.game.weapons.Bomb(player.getPosition()));
    }

}
