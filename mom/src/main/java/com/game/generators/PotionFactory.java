package com.game.generators;

import com.engine.utils.Vector3;
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
     * @param x X coordinate for the potion to spawn.
     * @param y Y coordinate for the potion to spawn.
     * @return A health potion.
     */
    public static HealthPotion createHealthPotion(int x, int y) {
        return new HealthPotion(new Vector3(x, y, 1));
    }

    /**
     * createStrengthPotion method. Creates a Strength potion.
     *
     * @param x X coordinate for the potion to spawn.
     * @param y Y coordinate for the potion to spawn.
     * @return A strength potion.
     */
    public static StrengthPotion createStrengthPotion(int x, int y) {
        return new StrengthPotion(new Vector3(x, y, 1));
    }

    /**
     * createSpeedPotion method. Creates a Speed potion.
     *
     * @param x X coordinate for the potion to spawn.
     * @param y Y coordinate for the potion to spawn.
     * @return A speed potion.
     */
    public static SpeedPotion createSpeedPotion(int x, int y) {
        return new SpeedPotion(new Vector3(x, y, 1));
    }
}
