package com.game.potions;

import com.game.Player;
import org.junit.jupiter.api.BeforeEach;

/**
 * PotionTestInitializer class. Is used to provide initializers to the potions test classes.
 */
public abstract class PotionTestInitializer {

    /** Player used to test the potion effects. */
    private Player player;

    /**
     * This method initialize the player in the class to make a clean one before each test.
     */
    @BeforeEach
    public void initPlayer() {
        this.player = new Player();
    }

    /**
     * Getter for the player.
     * @return player inside the class.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter for the player.
     * @param player player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
