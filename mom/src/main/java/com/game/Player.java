package com.game;

import com.engine.Sprite;
import com.engine.Texture;
import com.game.weapons.PlayerFist;

/**
 * Player class.
 * This is the player class.
 */
public class Player extends Living {
    /**
     * Player constructor.
     */
    public Player() {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE));
        this.setWeapon(new PlayerFist());
    }

    /**
     * Update the player.
     */
    public void update() {
        // TODO
    }
}
