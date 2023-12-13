package com.game.items.weapons;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Teddy item class.
 */
public class Teddy extends Weapon {

    protected Teddy(int spriteShift, Vector3 position, ItemType t) {
        super(spriteShift, position, t);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void equipWeapon(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equipWeapon'");
    }

}
