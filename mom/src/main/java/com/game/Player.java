package com.game;

import com.badlogic.gdx.graphics.Color;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.tiles.Tile;
import com.game.weapons.PlayerFist;

import java.util.ArrayList;
import java.util.List;

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
    /** Red component for the health bar color. */
    private final float rComponent = 0.1f;
    /** Green component for the health bar color. */
    private final float gComponent = 0.62f;
    /** Blue component for the health bar color. */
    private final float bComponent = 0.1f;

    /**
     * Player constructor.
     */
    public Player() {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), new Vector3(), PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
        this.setWeapon(new PlayerFist());
        this.setHealth(PLAYER_HEALTH);
        this.setSpeed(PLAYER_SPEED);
        this.setHealthBarColor(new Color(rComponent, gComponent, bComponent, 1f));
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
        this.setHealthBarColor(new Color(rComponent, gComponent, bComponent, 1f));
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

    /**
     * Detect whether an enemy is in the player's field of vision.
     *
     * @return The living entity in the range attack.
     */
    public List<Living> findEnemies() {
        List<Living> enemiesInFOV = new ArrayList<>();
        Living[] enemies = Game.getInstance().getMaze().getMonsters();

        for (Living enemy : enemies) {
            if (enemy != this && enemy.getHealth() > 0) {
                if (isInFOV(this, enemy)) {
                    enemiesInFOV.add(enemy);
                }
            }
        }
        return enemiesInFOV;
    }
}
