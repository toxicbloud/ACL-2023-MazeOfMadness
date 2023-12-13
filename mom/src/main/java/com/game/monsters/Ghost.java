package com.game.monsters;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Score;
import com.game.tiles.Tile;
import com.game.weapons.GhostFist;
import com.game.weapons.ZombieFist;

/**
 * Ghost class.
 * This is the ghost class, representing a ghost.
 */
public class Ghost extends Monster {

    /** Ghost speed. */
    public static final float GHOST_SPEED = 1.2f;

    /** Default ghost health. */
    private static final int GHOST_HEALTH = 60;
    /** Default ghost max health. */
    private static final int GHOST_MAX_HEALTH = 60;
    /** Amount of points the player gets when killing a ghost. */
    private static final int GHOST_POINTS = 80;

    /**
     * Ghost default constructor.
     * Create a ghost with default values.
     */
    public Ghost() {
        super(
            new Sprite(new Texture("images/ghost.png"), SPRITE_SIZE, SPRITE_SIZE),
            MonsterType.MONSTER_GHOST,
            GHOST_HEALTH,
            GHOST_MAX_HEALTH);
        this.setWeapon(new GhostFist());
    }

    /**
     * Ghost constructor.
     * Create a ghost with custom values.
     * @param position The ghost position.
     */
    public Ghost(Vector3 position) {
        this();
        this.setPosition(position);
        this.setWeapon(new GhostFist());
    }

    @Override
    public void affectScore(Score score) {
        score.addPoints(GHOST_POINTS);
        score.addKill(MonsterType.MONSTER_GHOST);
    }

    @Override
    protected boolean tileCollides(Tile t) {
        return false; // Ghosts can fly through walls
    }
}
