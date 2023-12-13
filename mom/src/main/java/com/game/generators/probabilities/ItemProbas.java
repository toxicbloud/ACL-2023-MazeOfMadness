package com.game.generators.probabilities;

import com.game.WorldItem;

/** ItemProbas interface. It regroups logistic for all Item spawwning mechanisms. */
public interface ItemProbas {

    /**
     * This method returns an array of the enum objects without the BASE_SPAWN_PROBA one.
     *
     * @return An array containing the enum values.
     */
    ItemProbas[] getItemsProbasArray();

    /**
     * Returns the cumulation of the probabilities of the class.
     *
     * @return a float.
     */
    float computeTotal();

    /**
     * Classic getter. Returns the value of the enum.
     *
     * @return Float corresponding to the value of the enum.
     */
    float getValue();

    /**
     * This method returns a new Item depending on the enum using it.
     *
     * @param x X coord to spawn the item to.
     * @param y Y coord to spawn the item to.
     * @return A new Item.
     */
    WorldItem getNewItem(int x, int y);

    /**
     * Returns the base spawning probability of the Item class.
     *
     * @return float.
     */
    float getBaseSpawnProba();

    /**
     * Returns the maximum of spawning occurrences of the item inside the maze.
     *
     * @return float.
     */
    int getMaxSpawningOccurrences();
}
