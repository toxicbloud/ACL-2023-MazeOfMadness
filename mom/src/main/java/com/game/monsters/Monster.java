package com.game.monsters;

import com.engine.Sprite;
import com.engine.Texture;
import com.game.Entity;

/**
 * Monster class.
 * This is the monster class.
 */
public abstract class Monster extends Entity {
    /** Default monster sprite. */
    protected static final Sprite MONSTER_SPRITE =
        new Sprite(new Texture("images/monster.png"), SPRITE_SIZE, SPRITE_SIZE);

    /** The monster type. */
    private MonsterType type;

    protected Monster(MonsterType t) {
        super(MONSTER_SPRITE);
        this.type = t;
    }

    /**
     * Get the monster type.
     * @return The monster type.
     */
    public MonsterType getType() {
        return type;
    }
}
