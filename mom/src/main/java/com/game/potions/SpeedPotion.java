package com.game.potions;

import com.engine.utils.Vector3;
import com.game.Player;

/**
 * SpeedPotion class.
 */
public class SpeedPotion extends Potion {

    /** Speed multiplier per potion picked. */
    public static final float SPEED_MULTIPLIER = 2.f;

    /** Shift for the speed potion sprite. */
    private static final int SPRITE_SHIFT = 0 * SPRITE_SIZE;

    /**
     * Speed potion constructor.
     *
     * @param position Position of the potion inside the maze.
     */
    public SpeedPotion(Vector3 position) {
        super(SpeedPotion.SPRITE_SHIFT, position);
    }

    /**
     * Speed potion constructor.
     */
    public SpeedPotion() {
        super(SpeedPotion.SPRITE_SHIFT, new Vector3());
    }

    @Override
    protected void applyEffect(Player player) {
        player.setSpeed(Player.PLAYER_SPEED * SpeedPotion.SPEED_MULTIPLIER);
    }
}
