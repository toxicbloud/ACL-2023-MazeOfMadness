package com.game.particles;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;

/**
 *
 * Fire particle.
 */
public class Fire extends TemporaryParticle {
    /**
     *
     */
    private static final int FIRE_DURATION = 1000;

    /**
     * Fire constructor.
     *
     * @param position The position of the fire.
     */
    public Fire(Vector3 position) {
        super(new Sprite(new Texture("images/bomb.png"), SPRITE_SIZE, SPRITE_SIZE, 1 * SPRITE_SIZE),
                position, FIRE_DURATION);
    }

}
