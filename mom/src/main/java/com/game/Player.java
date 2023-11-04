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
    /** Player default health. */
    public static final int PLAYER_HEALTH = 10;
    /** Default player width. */
    private static final float PLAYER_WIDTH = 1.0f;
    /** Default player size. */
    private static final Vector3 PLAYER_SIZE = new Vector3(PLAYER_WIDTH, PLAYER_WIDTH, 1.0f);

    /**
     * Player constructor.
     */
    public Player() {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), new Vector3(), PLAYER_SIZE);
        this.setWeapon(new PlayerFist());
        this.setHealth(PLAYER_HEALTH);
        this.setSpeed(PLAYER_SPEED);
    }

    /**
     * Player full constructor.
     *
     * @param position The position of the player.
     */
    public Player(Vector3 position) {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), position, PLAYER_SIZE);
        this.setWeapon(new PlayerFist());
        this.setHealth(PLAYER_HEALTH);
        this.setSpeed(PLAYER_SPEED);
    }

    /**
     * Update the player.
     */
    public void update() {

    }
}
