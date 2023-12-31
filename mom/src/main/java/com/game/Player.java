package com.game;

import com.badlogic.gdx.graphics.Color;
import com.engine.SoundManager;
import com.engine.SoundManager.SoundList;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.controllers.PlayerController;
import com.game.tiles.Tile;
import com.game.weapons.PlayerFist;
import com.game.weapons.Weapon;
import org.json.JSONObject;

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
        this.defaultWeapon = new PlayerFist();
        this.setWeapon(this.defaultWeapon);
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
        this.setWeapon(new PlayerFist());
        this.defaultWeapon = new PlayerFist();
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

    @Override
    public boolean takeDamage(int damage) {
        indicateUpdate();
        boolean isDead = super.takeDamage(damage);
        SoundManager.getInstance().play(SoundList.PLAYER_DAMAGE);
        return isDead;
    }

    /**
     * Detect whether an enemy is in the player's field of vision.
     *
     * @return The living entity in the range attack.
     */
    public List<Living> findEnemies() {
        List<Living> enemiesInFOV = new ArrayList<>();
        Maze maze = Game.getInstance().getMaze();
        if (maze == null) {
            return enemiesInFOV;
        }

        Living[] enemies = maze.getMonsters();

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
        indicateUpdate();
        super.setWeapon(weapon == null ? defaultWeapon : weapon);
        if (weapon != null) {
            weapon.setOwner(this);
        }
    }

    @Override
    public void render() {
        super.render();
        PlayerController controller = (PlayerController) getController();
        if (this.getWeapon() != null && controller != null) {
            if (!controller.isMoving() || !controller.isAttacking()) {
                this.getWeapon().render();
            }
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("position", this.getPosition().toJSON());
        json.put("health", this.getHealth());

        return json;
    }
}
