package com.game.generators;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class. It's only purpose is to make a random generator between 2 indexes.
 */
public final class Utility {

    /**
     * RNG : Attribut permettant de faire appel à la classe Random.
     */
    private static final Random RNG = new SecureRandom();

    /**
     * Private constructor for the utility class.
     */
    private Utility() {}

    /**
     * Superset of the RNG method that allows to provide a random int between 2 numbers.
     * @param min Lower bound.
     * @param max Upper bound.
     * @return Random int between min and max parameters.
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max parameter must be greater than min parameter");
        }
        return RNG.nextInt((max - min) + 1) + min;
    }

    /**
     * nextFloat : This method returns a float between 0 and 1 randomly.
     * @return a float between 0 and 1.
     */
    public static float nextFloat() {
        return RNG.nextFloat();
    }
}
