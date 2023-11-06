package com.game.monsters;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;

/**
 * Zombie class.
 * This is the zombie class.
 */
public class Zombie extends Monster {

    /**
     * Zombie speed.
     */
    public static final float ZOMBIE_SPEED = 1.5f;

    /** Default zombie health. */
    private static final int ZOMBIE_HEALTH = 100;
    /** Default zombie max health. */
    private static final int ZOMBIE_MAX_HEALTH = 150;

    /**
     * Zombie constructor.
     * This is the default constructor for the zombie class.
     */
    public Zombie() {
        super(
                new Sprite(new Texture("images/zombie.png"), SPRITE_SIZE, SPRITE_SIZE),
                MonsterType.MONSTER_ZOMBIE, ZOMBIE_HEALTH, ZOMBIE_MAX_HEALTH);
    }

    /**
     * Zombie constructor.
     * This is the constructor for the zombie class.
     *
     * @param position The position of the zombie.
     */
    public Zombie(Vector3 position) {
        super(
                new Sprite(new Texture("images/zombie.png"), SPRITE_SIZE, SPRITE_SIZE),
                MonsterType.MONSTER_ZOMBIE,
                position, ZOMBIE_HEALTH, ZOMBIE_MAX_HEALTH);
    }

    @Override
    public void update() {
        super.update();
    }
}
