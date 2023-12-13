package com.game.generators;

import com.game.potions.HealthPotion;
import com.game.potions.SpeedPotion;
import com.game.potions.StrengthPotion;

/**
 * PotionFactory class.
 * Creates Potion objects to place on the maze.
 */
public final class PotionFactory {

    /**
     * Private constructor of PotionFactory class.
     */
    private PotionFactory() {}

    /**
     * createHealthPotion method. Creates a Health potion.
     *
     * @return A health potion.
     */
    public static HealthPotion createHealthPotion() {
        return new HealthPotion();
    }

    /**
     * createStrengthPotion method. Creates a Strength potion.
     *
     * @return A strength potion.
     */
    public static StrengthPotion createStrengthPotion() {
        return new StrengthPotion();
    }

    /**
     * createSpeedPotion method. Creates a Speed potion.
     *
     * @return A speed potion.
     */
    public static SpeedPotion createSpeedPotion() {
        return new SpeedPotion();
    }
}
