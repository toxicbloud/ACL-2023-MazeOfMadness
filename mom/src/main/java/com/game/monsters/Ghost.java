package com.game.monsters;

import com.engine.Sprite;
import com.engine.Texture;
import com.game.Score;

/**
 * Ghost class.
 * This is the ghost class, representing a ghost.
 */
public class Ghost extends Monster {

    /** Ghost speed. */
    public static final float GHOST_SPEED = 1.2f;

    /** Default ghost health. */
    private static final int GHOST_HEALTH = 100;
    /** Default ghost max health. */
    private static final int GHOST_MAX_HEALTH = 150;
    /** Amount of points the player gets when killing a ghost. */
    private static final int GHOST_POINTS = 40;

    protected Ghost() {
        super(
            new Sprite(new Texture("images/ghost.png"), SPRITE_SIZE, SPRITE_SIZE),
            MonsterType.MONSTER_GHOST,
            GHOST_HEALTH,
            GHOST_MAX_HEALTH);
    }

    @Override
    public void affectScore(Score score) {
        score.addPoints(GHOST_POINTS);
        score.addKill(MonsterType.MONSTER_GHOST);
    }
}
