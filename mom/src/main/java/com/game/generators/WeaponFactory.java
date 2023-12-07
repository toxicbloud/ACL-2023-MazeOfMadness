package com.game.generators;

import com.engine.utils.Vector3;
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
     * @param x x coordinate.
     * @param y y coordinate.
     * @return A brand-new Sword !
     */
    public static Sword createSword(int x, int y) {
        return new Sword(new Vector3(x, y, 1));
    }

    /**
     * createBowFunction : Creates a Bow item.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return A brand-new Bow !
     */
    public static Bow createBow(int x, int y) {
        return new Bow(new Vector3(x, y, 1));
    }

    /**
     * createAxeFunction : Creates an Axe item.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return A brand-new Axe !
     */
    public static Axe createAxe(int x, int y) {
        return new Axe(new Vector3(x, y, 1));
    }

    /**
     * createBombFunction : Creates a Bomb item.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return A brand-new Bomb !
     */
    public static Bomb createBomb(int x, int y) {
        return new Bomb(new Vector3(x, y, 1));
    }

    /**
     * createTeddyFunction : Creates a Teddy item.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return A brand-new Teddy Bear !
     */
    public static Teddy createTeddy(int x, int y) {
        return new Teddy(new Vector3(x, y, 1));
    }

    /**
     * createTridentFunction : Creates a Trident item.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return A brand-new Trident !
     */
    public static Trident createTrident(int x, int y) {
        return new Trident(new Vector3(x, y, 1));
    }
}
