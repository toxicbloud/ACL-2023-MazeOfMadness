package com.game.potions;

import com.game.Player;
import com.game.items.potions.SpeedPotion;

import org.junit.jupiter.api.Test;

/**
 * SpeedPotion test class.
 */
public class SpeedPotionTest extends PotionTestInitializer {

    /**
     * This method checks if the speed effect is applied to the player when it was not already applied.
     * This method calls the applyEffect() method 1 time in order to check if the method works and applies the correct
     * speed to the player.
     */
    @Test
    public void testSpeedPotionInNormalConditions() {
        SpeedPotion potion = new SpeedPotion();
        potion.applyEffect(this.getPlayer());

        String str = "[ERROR] - Player speed is not correct. Got "
                + this.getPlayer().getSpeed()
                + ", expected "
                + Player.PLAYER_SPEED * SpeedPotion.SPEED_MULTIPLIER;
        assert this.getPlayer().getSpeed() == Player.PLAYER_SPEED * SpeedPotion.SPEED_MULTIPLIER : str;
    }

    /**
     * This method checks if the speed effect is applied to the player when it was already applied.
     * This method calls the applyEffect() method 2 times in order to check if the method works and doesn't exceed the
     * speed cap.
     */
    @Test
    public void testSpeedPotionWhenPlayerAlreadyPickedASpeedPotion() {
        SpeedPotion potion = new SpeedPotion();
        potion.applyEffect(this.getPlayer());
        potion.applyEffect(this.getPlayer());

        String str = "[ERROR] - Player speed is not correct. Got "
                + this.getPlayer().getSpeed()
                + ", expected "
                + Player.PLAYER_SPEED * SpeedPotion.SPEED_MULTIPLIER;
        assert this.getPlayer().getSpeed() == Player.PLAYER_SPEED * SpeedPotion.SPEED_MULTIPLIER : str;
    }
}
