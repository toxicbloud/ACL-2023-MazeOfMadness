package com.game;

import com.badlogic.gdx.graphics.Color;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.tiles.Tile;
import com.game.weapons.PlayerFist;
import com.game.weapons.Trident;
import com.game.weapons.Weapon;

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
    /** Modified color for the player's Health Bar. */
    private static final Color HEALTH_BAR_COLOR = new Color(0.1f, 0.62f, 0.1f, 1f);
    /** Last entered tile by the player. */
    private Tile enteredTile;
    /** Default weapon for when the player has no picked up any custom weapon. */
    private Weapon defaultWeapon;

    /**
     * Player constructor.
     */
    public Player() {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), new Vector3(), PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
        this.setWeapon(new PlayerFist());
        this.defaultWeapon = this.getWeapon();
        this.setHealth(PLAYER_HEALTH);
        this.setSpeed(PLAYER_SPEED);
        this.setHealthBarColor(Player.HEALTH_BAR_COLOR);
    }

    /**
     * Player full constructor.
     *
     * @param position The position of the player.
     */
    public Player(Vector3 position) {
        super(new Sprite(new Texture("images/player.png"), SPRITE_SIZE, SPRITE_SIZE), position, PLAYER_SIZE,
                PLAYER_HEALTH, PLAYER_MAX_HEALTH);
        this.setWeapon(new Trident());
        this.defaultWeapon = new PlayerFist();
        this.getWeapon().interact(this);
        this.setHealth(PLAYER_HEALTH);
        this.setSpeed(PLAYER_SPEED);
        this.setHealthBarColor(Player.HEALTH_BAR_COLOR);
    }

    @Override
    public void update() {
        super.update();
        this.getWeapon().update();
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

    /**
     * Set the player's weapon and fallback to the default weapon if null.
     *
     * @param weapon The weapon to set.
     */
    @Override
    public void setWeapon(Weapon weapon) {
        super.setWeapon(weapon == null ? defaultWeapon : weapon);
    }

    @Override
    public void render() {
        super.render();
        this.getWeapon().render();
    }

    /**
     * Returns a JSON representation of the player.
     * @return JSON representation of the player.
     */
    public String toJSON() {
        return "{\"position\": " + this.getPosition().toJSON() + ", \"health\": " + this.getHealth() + "}";
    }
}
