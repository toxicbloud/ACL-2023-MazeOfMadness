package com.game.tiles;

import com.engine.Sprite;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Game;

/**
 * Stair class.
 * This is the stair class.
 */
public abstract class Stair extends Tile {
    /** Stair direction x. */
    public static final int DIRECTION_X = 1;
    /** Stair direction y. */
    public static final int DIRECTION_Y = 2;

    /** Stairs' player action zone (in bloc unit). */
    private static final float STAIR_ACTION_ZONE = 0.9f;
    /** Activation zone up border for stairs. */
    private static final float ZONE_DOWN = 0.05f;
    /** Activation zone down border for stairs. */
    private static final float ZONE_UP = 1.05f;
    /** Activation zone distance for stairs. */
    private static final float ZONE_DIST = 1.5f;

    /** Stair direction. */
    private int direction;

    /**
     * Stair constructor.
     * This is the default constructor for the stair class.
     * @param type The type of the stair.
     * @param s The sprite of the stair.
     * @param dir The direction of the stair.
     */
    public Stair(TileType type, Sprite s, int dir) {
        super(type, s, false);
        this.direction = dir;
    }

    /**
     * Stair constructor.
     * This is the full constructor for the stair class.
     * @param type The type of the stair.
     * @param s The sprite of the stair.
     * @param p The position of the ground.
     * @param dir The direction of the stair.
     */
    public Stair(TileType type, Sprite s, Vector3 p, int dir) {
        super(type, s, p, false);
        this.direction = dir;
    }

    /**
     * Get the direction of the stair.
     * @return The direction of the stair.
     */
    public int getDirection() {
        return direction;
    }

    @Override
    public void update() {
        Vector3 playerPos = Game.getInstance().getPlayer().getPosition();
        Vector3 thisPos = this.getPosition();
        Vector2 playerPos2D = new Vector2(playerPos.x, playerPos.y);
        Vector2 thisPos2D = new Vector2(thisPos.x, thisPos.y);
        if (playerPos2D.dst(thisPos2D) > ZONE_DIST
            || playerPos.z < thisPos.z - ZONE_DOWN
            || playerPos.z > thisPos.z + ZONE_UP) {
            return;
        }
        Vector3 diffPos = thisPos.sub(playerPos);
        if (Math.abs(diffPos.x) > STAIR_ACTION_ZONE) {
            Game.getInstance().getPlayer().setPosition(new Vector3(
                playerPos.x,
                playerPos.y,
                (int) playerPos.z
            ));
            return;
        }

        Game.getInstance().getPlayer().setPosition(new Vector3(
            playerPos.x,
            playerPos.y,
            thisPos.z + (Math.max(0, Math.min(1, 1 + diffPos.y)))
        ));
    }
}
