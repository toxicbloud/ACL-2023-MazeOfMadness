package com.game.potions;

import com.engine.utils.Vector3;
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
        super(HealthPotion.SPRITE_SHIFT, position);
    }

    /**
     * Health potion constructor.
     */
    public HealthPotion() {
        super(HealthPotion.SPRITE_SHIFT, new Vector3());
    }

    @Override
    protected void applyEffect(Player player) {
        int newHealth = player.getHealth() + HealthPotion.HEALTH_AMOUNT;
        if (newHealth > player.getMaxHealth()) {
            newHealth = player.getMaxHealth();
        }

        player.setHealth(newHealth);
    }
}
