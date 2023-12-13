package com.game.items.weapons;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;
import com.game.WorldItem;

/**
 * Weapon World abstract class.
 * This is the base class for all weapons physically present in the world and
 * held by the maze.
 *
 * @see com.game.weapons.Weapon for the base class of player held weapons.
 */
public abstract class Weapon extends WorldItem {

    // FIXME: Bad
    /** Texture for the weapons. */
    protected static final Texture WEAPONS_TEXTURE = new Texture("images/weapons.png");

    /**
     * Weapon Constructor.
     *
     * @param spriteShift Shift for the weapon's sprite.
     * @param position    Position of the weapon inside the maze.
     * @param t           Type of the item.
     */
    protected Weapon(int spriteShift, Vector3 position, ItemType t) {
        super(new Sprite(Weapon.WEAPONS_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, spriteShift),
                position,
                new Vector3(1, 1, 1),
                t);
    }

    /**
     * This method applies the effects of the current potion to the player.
     *
     * @param player Player to apply the potion effects upon.
     */
    protected abstract void equipWeapon(Player player);

    @Override
    public void interact(Player player) {
        this.equipWeapon(player);
    }
}
