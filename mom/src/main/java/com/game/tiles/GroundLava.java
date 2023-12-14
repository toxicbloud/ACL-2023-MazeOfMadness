package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import com.game.Player;

/**
 * GroundLava class.
 * This is the ground lava class.
 */
public class GroundLava extends Ground {
    /** Ground lava sprite shift from texture top. */
    private static final int GROUND_LAVA_SPRITE_SHIFT = SPRITE_SIZE * 5;
    /** Speed factor by which the player is slowed. */
    private static final float SPEED_FACTOR = 3.0f;
    /** Time between each damage. */
    private static final int DAMAGE_DELAY = 1000;
    /** Damage amount. */
    private static final int DAMAGE_AMOUNT = 3;

    /**
     * Player currently on the tile.
     * Used to affect the player continuously.
     */
    private Player currentPlayer;
    /** Last time the player took damage. */
    private long lastDamageTime;

    /**
     * GroundLava constructor.
     * This is the default constructor for the ground lava class.
     */
    public GroundLava() {
        super(TileType.GROUND_LAVA, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_LAVA_SPRITE_SHIFT));
    }

    /**
     * GroundLava full constructor.
     * This is the full constructor for the ground lava class.
     *
     * @param position The position of the tile
     */
    public GroundLava(Vector3 position) {
        super(
                TileType.GROUND_LAVA,
                new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_LAVA_SPRITE_SHIFT),
                position);
    }

    @Override
    public void update() {
        if (currentPlayer != null) {
            if (System.currentTimeMillis() - lastDamageTime > DAMAGE_DELAY) {
                currentPlayer.takeDamage(DAMAGE_AMOUNT);
                lastDamageTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onPlayerEnter(Player p) {
        currentPlayer = p;
        p.setSpeed(p.getSpeed() / SPEED_FACTOR);
    }

    @Override
    public void onPlayerExit(Player p) {
        currentPlayer = null;
        p.setSpeed(p.getSpeed() * SPEED_FACTOR);
    }
}
