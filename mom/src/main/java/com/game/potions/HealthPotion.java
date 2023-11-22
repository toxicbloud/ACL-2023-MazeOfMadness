package com.game.potions;

import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Player;

/**
 * Health potion class.
 */
public class HealthPotion extends Potion {

    /** Health given back o the player when collected. */
    public static final int HEALTH_AMOUNT = 50;

    /** Sprite shift for the health potion sprite. */
    private static final int SPRITE_SHIFT = 1 * SPRITE_SIZE;

    /**
     * Health potion constructor.
     *
     * @param position Position of the potion inside the maze.
     */
    public HealthPotion(Vector3 position) {
        super(HealthPotion.SPRITE_SHIFT, position, ItemType.ITEM_HEALTH_POTION);
    }

    /**
     * Health potion constructor.
     */
    public HealthPotion() {
        super(HealthPotion.SPRITE_SHIFT, new Vector3(), ItemType.ITEM_HEALTH_POTION);
    }

    @Override
    protected void applyEffect(Player player) {
        player.regen(HealthPotion.HEALTH_AMOUNT);
    }
}
