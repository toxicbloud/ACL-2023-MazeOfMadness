package com.game.items.potions;

import com.game.weapons.PlayerFist;
import com.game.weapons.Weapon;
import org.junit.jupiter.api.Test;

/**
 * StrengthPotion test class.
 */
public class StrengthPotionTest extends PotionTestInitializer {

    /**
     * This method checks if the strength effect is applied to the weapon when the weapon was not already improved.
     */
    @Test
    public void testStrengthPotionInNormalConditions() {
        StrengthPotion potion = new StrengthPotion();
        potion.applyEffect(this.getPlayer());

        final String str = "[ERROR] - The potion effect did not change the weapon !";
        assert this.getPlayer().getWeapon().hasDoubleDamage() : str;
    }

    /**
     * This method checks if the strength effect is applied to the weapon when the weapon was already improved.
     * We must check if the weapon does not change before and after the method.
     */
    @Test
    public void testStrengthPotionWhenAlreadyApplied() {
        StrengthPotion potion = new StrengthPotion();
        Weapon baseWeapon = new PlayerFist(true);
        this.getPlayer().setWeapon(baseWeapon);       // We set a weapon with double damage.
        potion.applyEffect(this.getPlayer());

        assert this.getPlayer().getWeapon() == baseWeapon : "[ERROR] - The potion effect changed the weapon ! Expected "
                + baseWeapon
                + ", got "
                + this.getPlayer().getWeapon();
    }
}
