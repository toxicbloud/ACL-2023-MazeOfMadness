package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import com.game.Player;

/**
 * GroundSpikes class.
 * This is the ground lava class.
 */
public class GroundSpikes extends Ground {
    /** Ground spikes sprite shift from texture top. */
    private static final int GROUND_SPIKES_SPRITE_SHIFT = SPRITE_SIZE * 4;
    /** Default Ground sprite shift from texture top. */
    private static final int DEFAULT_GROUND_SPRITE_SHIFT = 0;
    /** Time between each damage. */
    private static final int DAMAGE_DELAY = 2000;
    /** Damage amount. */
    private static final int DAMAGE_AMOUNT = 10;

    /**
     * Player currently on the tile.
     * Used to affect the player continuously.
     */
    private Player currentPlayer;
    /** Last time the player took damage. */
    private long lastDamageTime;

    /**
     * Spikes sprite.
     * The sprite switch to this one the first time the player enter the tile.
     */
    private Sprite spikesSprite;
    /**
     * Indicates if the spikes are hidden.
     */
    private boolean hidden;

    /**
     * GroundSpikes constructor.
     * This is the default constructor for the ground lava class.
     */
    public GroundSpikes() {
        super(TileType.GROUND_SPIKES, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, DEFAULT_GROUND_SPRITE_SHIFT));
        spikesSprite = new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_SPIKES_SPRITE_SHIFT);
        hidden = true;
    }

    /**
     * GroundSpikes full constructor.
     * This is the full constructor for the ground lava class.
     *
     * @param position The position of the tile
     */
    public GroundSpikes(Vector3 position) {
        super(
                TileType.GROUND_SPIKES,
                new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, DEFAULT_GROUND_SPRITE_SHIFT),
                position);
        spikesSprite = new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_SPIKES_SPRITE_SHIFT);
        hidden = true;
    }

    @Override
    public void update() {
        if (currentPlayer != null) {
            // TODO switch to Time singleton method
            if (System.currentTimeMillis() - lastDamageTime > DAMAGE_DELAY) {
                currentPlayer.takeDamage(DAMAGE_AMOUNT);
                lastDamageTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onPlayerEnter(Player p) {
        currentPlayer = p;

        if (hidden) {
            // TODO switch to Time singleton method
            lastDamageTime = System.currentTimeMillis(); // prevent the big damage from happening immediately

            // first time the player enter the tile we hit him immediately but only half the
            // damage
            currentPlayer.takeDamage(DAMAGE_AMOUNT / 2);
            setSprite(spikesSprite);
            hidden = false;
        }
    }

    @Override
    public void onPlayerExit(Player p) {
        currentPlayer = null;
    }
}
