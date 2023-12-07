package com.game.generators;

import com.game.weapons.*;

/**
 * WeaponFactory class.
 * Creates Weapon objects to place on the maze.
 */
public final class WeaponFactory {

    /**
     * Private constructor for WeaponFactory class.
     */
    private WeaponFactory() {}

    /**
     * createSwordFunction : Creates a Sword item.
     *
     * @return A brand-new Sword !
     */
    public static Sword createSword() {
        return new Sword();
    }

    /**
     * createBowFunction : Creates a Bow item.
     *
     * @return A brand-new Bow !
     */
    public static Bow createBow() {
        return new Bow();
    }

    /**
     * createAxeFunction : Creates an Axe item.
     *
     * @return A brand-new Axe !
     */
    public static Axe createAxe() {
        return new Axe();
    }

    /**
     * createBombFunction : Creates a Bomb item.
     *
     * @return A brand-new Bomb !
     */
    public static Bomb createBomb() {
        return new Bomb();
    }

    /**
     * createTeddyFunction : Creates a Teddy item.
     *
     * @return A brand-new Teddy Bear !
     */
    public static Teddy createTeddy() {
        return new Teddy();
    }

    /**
     * createTridentFunction : Creates a Trident item.
     *
     * @return A brand-new Trident !
     */
    public static Trident createTrident() {
        return new Trident();
    }
}
