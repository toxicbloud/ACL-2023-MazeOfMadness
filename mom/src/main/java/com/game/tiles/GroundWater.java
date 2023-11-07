package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import com.game.Player;

/**
 * GroundWater class.
 * This is the ground water class.
 */
public class GroundWater extends Ground {
    /** Ground water sprite shift from texture top. */
    private static final int GROUND_WATER_SPRITE_SHIFT = SPRITE_SIZE * 6;

    /** Speed factor by which the player is slowed. */
    private static final float SPEED_FACTOR = 1.5f;

    /**
     * GroundWater constructor.
     * This is the default constructor for the ground water class.
     */
    public GroundWater() {
        super(TileType.GROUND_WATER, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_WATER_SPRITE_SHIFT));
    }

    /**
     * GroundWater full constructor.
     * This is the full constructor for the ground water class.
     *
     * @param position The position of the tile
     */
    public GroundWater(Vector3 position) {
        super(
                TileType.GROUND_WATER,
                new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, GROUND_WATER_SPRITE_SHIFT),
                position);
    }

    @Override
    public void update() {

    }

    /**
     * Slow the player when he enter the tile.
     */
    @Override
    public void onPlayerEnter(Player p) {
        p.setSpeed(p.getSpeed() / SPEED_FACTOR);
    }

    /**
     * Restore the player speed when he exit the tile.
     */
    @Override
    public void onPlayerExit(Player p) {
        p.setSpeed(p.getSpeed() * SPEED_FACTOR);
    }
}
