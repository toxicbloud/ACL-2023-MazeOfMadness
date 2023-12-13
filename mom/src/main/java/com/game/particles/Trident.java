package com.game.particles;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Living.Direction;

/**
 * Trident particle class.
 */
public class Trident extends Particle {
    /**
     * SPRITE_SHIFT.
     */
    private static final int SPRITE_SHIFT = 4;
    /**
     * Speed at which the trident moves.
     */
    private static final float TRIDENT_SPEED = 5.5f;
    /**
     * epsilon for the distance.
     */
    private static final float EPSILON = 0.01f;

    /**
     * Distance at which the trident is moved.
     */
    private Vector2 direction;
    /**
     * The weapon from which the particle is launched.
     */
    private com.game.weapons.Trident trident;

    /**
     * Trident particle constructor.
     *
     * @param position  The position of the particle.
     * @param direction The direction where the particle is going.
     * @param trident   The particle origin.
     */
    public Trident(Vector3 position, Direction direction, com.game.weapons.Trident trident) {
        super(new Sprite(new Texture("images/trident.png"), SPRITE_SIZE, SPRITE_SIZE,
                direction.ordinal() * SPRITE_SIZE + SPRITE_SHIFT * SPRITE_SIZE),
                position);
        this.direction = getDirectionVector(direction);
        this.trident = trident;
    }

    @Override
    public void update() {
        Vector2 normalized = direction.normalize();
        Vector3 lastPosition = this.getPosition();
        this.moveBy(new Vector2(normalized.x, normalized.y)
                .mul(Time.getInstance().getDeltaTime() * TRIDENT_SPEED));
        if (lastPosition.epsilonEquals(this.getPosition(), EPSILON)) {
            // WALL_HIT_SOUND.play();
            trident.setPosition(this.getPosition());
            this.destroy();
        }
    }

    /**
     * Get the direction vector.
     *
     * @param dir The direction.
     * @return The direction vector of the entity.
     */
    public Vector2 getDirectionVector(Direction dir) {
        switch (dir) {
            case RIGHT:
                return new Vector2(1, 0);
            case UP:
                return new Vector2(0, 1);
            case LEFT:
                return new Vector2(-1, 0);
            case DOWN:
                return new Vector2(0, -1);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }
}
