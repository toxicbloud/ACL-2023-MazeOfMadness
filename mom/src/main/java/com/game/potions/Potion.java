package com.game.potions;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Item;
import com.game.Player;

/**
 * Potion abstract class.
 */
public abstract class Potion extends Item {

    /** Texture for the potions. */
    protected static final Texture POTIONS_TEXTURE = new Texture("images/potions.png");

    /**
     * Potion Constructor.
     *
     * @param spriteShift Shift for the potion's sprite.
     * @param position Position of the potion inside the maze.
     */
    protected Potion(int spriteShift, Vector3 position) {
        super(new Sprite(Potion.POTIONS_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, spriteShift),
                position,
                new Vector3(1, 1, 1)
        );
    }

    /**
     * This method applies the effects of the current potion to the player.
     *
     * @param player Player to apply the potion effects upon.
     */
    protected abstract void applyEffect(Player player);
}
