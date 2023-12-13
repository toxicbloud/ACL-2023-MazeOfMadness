package com.game.monsters;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Score;
import com.game.controllers.ZombieController;
import com.game.weapons.ZombieFist;

/**
 * Zombie class.
 * This is the zombie class.
 */
public class Zombie extends Monster {

    /** Zombie speed. */
    public static final float ZOMBIE_SPEED = 1.5f;
    /** Zombie view distance to chase player. */
    public static final float VIEW_DISTANCE = 3f;
    /** Zombie distance at which it will leave the player alone. */
    public static final float LEAVE_DISTANCE = 6f;
    /** Default zombie health. */
    private static final int ZOMBIE_HEALTH = 150;
    /** Default zombie max health. */
    private static final int ZOMBIE_MAX_HEALTH = 150;
    /** Amount of points the player gets when killing a zombie. */
    private static final int ZOMBIE_POINTS = 40;

    /**
     * Zombie constructor.
     * This is the default constructor for the zombie class.
     */
    public Zombie() {
        super(new Sprite(new Texture("images/zombie.png"), SPRITE_SIZE, SPRITE_SIZE),
                MonsterType.MONSTER_ZOMBIE, ZOMBIE_HEALTH, ZOMBIE_MAX_HEALTH);
        this.setWeapon(new ZombieFist());
        this.registerController(new ZombieController(this));
    }

    /**
     * Zombie constructor.
     * This is the constructor for the zombie class.
     *
     * @param position The position of the zombie.
     */
    public Zombie(Vector3 position) {
        super(new Sprite(new Texture("images/zombie.png"), SPRITE_SIZE, SPRITE_SIZE),
                MonsterType.MONSTER_ZOMBIE, position, ZOMBIE_HEALTH, ZOMBIE_MAX_HEALTH);
        this.setWeapon(new ZombieFist());
        this.registerController(new ZombieController(this));
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public int getPoints() {
        return ZOMBIE_POINTS;
    }

    /**
     * Affect the score.
     *
     * @param score The score object to accept.
     */
    @Override
    public void affectScore(Score score) {
        score.addPoints(ZOMBIE_POINTS);
        score.addKill(MonsterType.MONSTER_ZOMBIE);
    }
}
