package com.game.items.potions;

import org.junit.jupiter.api.Test;

/**
 * HealthPotion test class.
 */
public class HealthPotionTest extends PotionTestInitializer {

    /**
     * This method checks if the health effect is applied to the player when the
     * player was not at full health and the
     * health amount restored plus the current health of the player does not exceed
     * the health cap.
     * This method calls the applyEffect() method 1 time in order to check if the
     * method works and applies the correct
     * health amount to the player.
     */
    @Test
    public void testHealthPotionInNormalConditions() {
        HealthPotion potion = new HealthPotion();
        final int basePlayerHealth = 50;
        this.getPlayer().setHealth(basePlayerHealth);
        potion.applyEffect(this.getPlayer());

        String str = "[ERROR] - Player health is not correct. Got "
                + this.getPlayer().getHealth()
                + ", expected "
                + basePlayerHealth + HealthPotion.HEALTH_AMOUNT;
        assert this.getPlayer().getHealth() == basePlayerHealth + HealthPotion.HEALTH_AMOUNT : str;
    }

    /**
     * This method checks if the health effect is applied to the player when the
     * player was at full health.
     * This method calls the applyEffect() method 1 time in order to check if the
     * method works and applies the correct
     * health amount to the player.
     */
    @Test
    public void testHealthPotionWhenPlayerIsAtFullHealth() {
        HealthPotion potion = new HealthPotion();
        potion.applyEffect(this.getPlayer());

        String str = "[ERROR] - Player health is not correct. Got "
                + this.getPlayer().getHealth()
                + ", expected "
                + this.getPlayer().getMaxHealth();
        assert this.getPlayer().getHealth() == this.getPlayer().getMaxHealth() : str;
    }

    /**
     * This method checks if the health effect is applied to the player when the
     * player was not at full health and the
     * health amount restored plus the current health of the player exceeds the
     * health cap.
     * This method calls the applyEffect() method 1 time in order to check if the
     * method works and applies the correct
     * health amount to the player.
     * It assumes that the player's maximum health is strictly higher than 1.
     */
    @Test
    public void testHealthPotionWhenAddingExceedsMaxHealth() {
        HealthPotion potion = new HealthPotion();
        final int basePlayerHealth = this.getPlayer().getMaxHealth() - 1;
        this.getPlayer().setHealth(basePlayerHealth);
        potion.applyEffect(this.getPlayer());

        String str = "[ERROR] - Player health is not correct. Got "
                + this.getPlayer().getHealth()
                + ", expected "
                + this.getPlayer().getMaxHealth();
        assert this.getPlayer().getHealth() == this.getPlayer().getMaxHealth() : str;
    }

}
