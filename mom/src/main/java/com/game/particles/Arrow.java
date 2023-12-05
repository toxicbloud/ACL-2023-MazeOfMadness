package com.game.particles;

import com.engine.Sprite;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Game;

import java.util.Arrays;

/**
 * Arrow particle launch by the bow.
 */
public class Arrow extends Particle {
    /**
     *
     */
    private static final float KILL_DISTANCE = 0.5f;
    /**
     *
     */
    private static final float EPSILON = 0.01f;
    /**
     * Speed at which the arrow moves.
     */
    private static final float ARROW_SPEED = 4.5f;
    /**
     * Arrow direction.
     */
    private Vector2 direction;
    /**
     * Arrow damage.
     */
    private int damage;

    /**
     * Arrow constructor.
     *
     * @param sprite    The sprite of the arrow.
     * @param direction The direction of the arrow.
     * @param position  The position of the arrow.
     * @param damage    The damage of the arrow.
     */
    public Arrow(Sprite sprite, Vector2 direction, Vector3 position, int damage) {
        super(sprite, direction, position);
        this.direction = direction.normalize();
        this.damage = damage;
    }

    @Override
    public void update() {
        super.update();
        Vector2 normalized = direction.normalize();
        Vector3 lastPosition = this.getPosition();
        this.moveBy(
                new Vector2(normalized.x, normalized.y)
                        .mul(Time.getInstance().getDeltaTime() * ARROW_SPEED));
        if (lastPosition.epsilonEquals(this.getPosition(), EPSILON)) {
            this.destroy();
        }
        Arrays.asList(Game.getInstance().getMaze().getMonsters()).forEach(monster -> {
            if (monster.getPosition().dst(this.getPosition()) < KILL_DISTANCE) {
                monster.takeDamage(this.damage);
                this.destroy();
            }
        });
    }
}
