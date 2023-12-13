package com.game.particles;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;

/**
 * Bomb particle.
 */
public class BombThrown extends TemporaryParticle {
    /**
     *
     */
    private static final int EXPLOSION_DELAY = 5000;

    /**
     * Bomb particle.
     *
     * @param position The position of the particle.
     */
    public BombThrown(Vector3 position) {
        super(new Sprite(new Texture("images/bomb.png"), SPRITE_SIZE, SPRITE_SIZE, 0), position,
                EXPLOSION_DELAY);
    }

}
