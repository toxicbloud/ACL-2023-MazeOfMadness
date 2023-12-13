package com.game.particles;

import com.engine.Sprite;
import com.engine.utils.Time;
import com.engine.utils.Vector3;

/**
 * Temporary particle abstract class. A temporary particle is a particle that is destroyed after a
 * certain amount of time.
 */
public abstract class TemporaryParticle extends Particle {
    /**
     * Duration of the particle.
     */
    private long startTime;
    /**
     * Duration of the particle.
     */
    private long duration;

    /**
     * TemporaryParticle constructor.
     *
     * @implNote By using this constructor you accept to not reference this particle anywhere else
     *           than in the maze.
     *
     * @param sprite The sprite of the particle.
     * @param position The position of the particle.
     * @param duration time in milliseconds before the particle is destroyed.
     */
    protected TemporaryParticle(Sprite sprite, Vector3 position, int duration) {
        super(sprite, position);
        this.duration = duration;
        this.startTime = Time.getInstance().getCurrentTime();
    }

    @Override
    public void update() {
        super.update();
        if (Time.getInstance().getCurrentTime() - startTime > duration) {
            destroy();
        }
    }

}
