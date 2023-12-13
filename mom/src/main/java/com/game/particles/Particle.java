package com.game.particles;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Game;
import com.game.Maze;
import org.json.JSONObject;

/**
 * Particle class.
 */
public abstract class Particle extends Entity {
    /**
     * Particle constructor.
     *
     * @param sprite The sprite of the particle.
     * @param position The position of the particle.
     */
    protected Particle(Sprite sprite, Vector3 position) {
        super(sprite, position, new Vector3(1.f, 1.f, 1.f));
    }

    @Override
    protected void remove() {
        Maze maze = Game.getInstance().getMaze();
        if (maze != null) {
            maze.removeParticle(this);
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("position", getPosition().toJSON());

        return json;
    }
}
