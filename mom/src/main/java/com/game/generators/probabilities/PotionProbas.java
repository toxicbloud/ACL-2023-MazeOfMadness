package com.game.generators.probabilities;

import com.game.WorldItem;
import com.game.generators.PotionFactory;

/** Probabilities for spawning potions. */
public enum PotionProbas implements ItemProbas {
    /** Dummy object. Used to call general methods on it. */
    DUMMY(0.F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return null;
        }
    },
    /** The probability for spawning a strength potion. */
    STRENGTH(0.05F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return PotionFactory.createStrengthPotion(x, y);
        }
    },
    /** The probability for spawning a speed potion. */
    SPEED(0.05F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return PotionFactory.createSpeedPotion(x, y);
        }
    },
    /** The probability for spawning a health potion. */
    HEALTH(0.1F) {
        @Override
        public WorldItem getNewItem(int x, int y) {
            return PotionFactory.createHealthPotion(x, y);
        }
    };

    /** Base spawn probability of the class of item. */
    private static final float BASE_SPAWN_PROBA = 0.03f;

    /** Value of the element. */
    private final float value;

    /**
     * Enum constructor.
     * @param v value for the probability.
     */
    PotionProbas(float v) {
        this.value = v;
    }

    @Override
    public ItemProbas[] getItemsProbasArray() {
        return PotionProbas.values();
    }

    @Override
    public float computeTotal() {
        float total = 0.f;
        for (PotionProbas pp : values()) {
            total += pp.getValue();
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
