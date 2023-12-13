package com.game.generators.probabilities;

import com.game.WorldItem;
import com.game.generators.WeaponFactory;

/** Probabilities for spawning weapons. */
public enum WeaponProbas implements ItemProbas {

    /** Dummy object. Used to call general methods on it. */
    DUMMY(0.F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return null;
        }
    },
    /** The probability for spawning a sword.*/
    SWORD(0.05F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return WeaponFactory.createSword(x, y);
        }
    },
    /** The probability for spawning an axe.*/
    AXE(0.03F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return WeaponFactory.createAxe(x, y);
        }
    },
    /** The probability for spawning a bow.*/
    BOW(0.03F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return WeaponFactory.createBow(x, y);
        }
    },
    /** The probability for spawning a trident.*/
    TRIDENT(0.01F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return WeaponFactory.createTrident(x, y);
        }
    },
    /** The probability for spawning a bomb.*/
    BOMB(0.10F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return WeaponFactory.createBomb(x, y);
        }
    },
    /** The probability for spawning a teddy.*/
    TEDDY(0.001F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return WeaponFactory.createTeddy(x, y);
        }
    };

    /** Base spawn probability of the class of item. */
    private static final float BASE_SPAWN_PROBA = 0.05f;

    /** Value of the element. */
    private final float value;

    /**
     * Enum constructor.
     * @param v value for the probability.
     */
    WeaponProbas(float v) {
        this.value = v;
    }

    @Override
    public ItemProbas[] getItemsProbasArray() {
        return WeaponProbas.values();
    }

    @Override
    public float computeTotal() {
        float total = 0.f;
        for (WeaponProbas wp : values()) {
            total += wp.getValue();
        }
        return total;
    }

    @Override
    public float getValue() {
        return this.value;
    }

    @Override
    public float getBaseSpawnProba() {
        return BASE_SPAWN_PROBA;
    }
}
