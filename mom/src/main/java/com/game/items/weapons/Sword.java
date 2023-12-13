package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Sword item class.
 */
public class Sword extends Weapon {

    protected Sword(int spriteShift, Vector3 position, ItemType t) {
        super(spriteShift, position, t);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void equipWeapon(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equipWeapon'");
    }

}
