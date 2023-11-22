package com.game;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.tiles.Tile;
import com.game.weapons.PlayerFist;

/**
 * Player class.
 * This is the player class.
 */
public class Player extends Living {
    /** Player default speed. */
    public static final float PLAYER_SPEED = 4.0f;
    /** Player default health. */
    public static final int PLAYER_HEALTH = 100;
    /** Default player width. */
    private static final float PLAYER_WIDTH = 1.0f;
    /** Default player size. */
    private static final Vector3 PLAYER_SIZE = new Vector3(PLAYER_WIDTH, PLAYER_WIDTH, 1.0f);
    /** Default player max health. */
    private static final int PLAYER_MAX_HEALTH = 100;

    /** Last entered tile by the player. */
    private Tile enteredTile;

    /**
     * Player constructor.
     */
    public Player() {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), new Vector3(), PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
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
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), position, PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
        this.setWeapon(new PlayerFist());
        this.setHealth(PLAYER_HEALTH);
        this.setSpeed(PLAYER_SPEED);
    }

    @Override
    public void update() {
        super.update();
        handleTileCollision();
    }

    /**
     * Trigger the tile when the player enters it and trigger the
     * tile when the player exits it.
     */
    private void handleTileCollision() {
        Maze maze = Game.getInstance().getMaze();

        // find the tile under the player
        Vector3 pos = this.getPosition();
        int x = Math.round(pos.x);
        int y = Math.round(pos.y);
        int z = Math.round(pos.z);
        Tile tile = maze.getTile(x, y, z - 1);

        if (tile != null && tile != enteredTile) {
            tile.onPlayerEnter(this);
            if (enteredTile != null) {
                enteredTile.onPlayerExit(this);
            }
            enteredTile = tile;
        }
    }
}
