package com.game;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.weapons.PlayerFist;

/**
 * Player class.
 * This is the player class.
 */
public class Player extends Living {
    /** Player default speed. */
    public static final float PLAYER_SPEED = 4.0f;
    /** Default player width. */
    private static final float PLAYER_WIDTH = 1.0f;
    /** Default player size. */
    private static final Vector3 PLAYER_SIZE = new Vector3(PLAYER_WIDTH, PLAYER_WIDTH, 1.0f);
    /** Default player health. */
    private static final int PLAYER_HEALTH = 100;
    /** Default player max health. */
    private static final int PLAYER_MAX_HEALTH = 100;

    /**
     * Player constructor.
     */
    public Player() {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), new Vector3(), PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
        this.setWeapon(new PlayerFist());
    }

    /**
     * Player full constructor.
     *
     * @param position The position of the player.
     */
    public Player(Vector3 position) {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), position, PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
        this.setWeapon(new PlayerFist());
    }

    /**
     * Update the player.
     */
    public void update() {

    }
}
