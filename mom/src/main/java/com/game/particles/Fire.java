package com.game.particles;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 *
 * Fire particle.
 */
public class Fire extends Particle {
    /**
     * Fire constructor.
     *
     * @param sprite   The sprite of the fire.
     * @param position The position of the fire.
     */
    public Fire(Sprite sprite, Vector3 position) {
        super(sprite, position);
    }

}
