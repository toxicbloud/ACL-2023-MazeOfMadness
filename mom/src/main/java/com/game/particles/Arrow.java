package com.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Living.Direction;

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
     * Arrow sound played when the arrow hits a wall.
     */
    private static final Sound WALL_HIT_SOUND = (Sound) Gdx.audio
            .newSound(Gdx.files.internal("sounds/arrowImpact.mp3"));

    /**
     * Arrow sound played when the arrow hits an enemy.
     */
    private static final Sound ENEMY_HIT_SOUND = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));

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
     * @param direction The direction of the arrow.
     * @param position  The position of the arrow.
     * @param damage    The damage of the arrow.
     */
    public Arrow(Direction direction, Vector3 position, int damage) {
        super(new Sprite(new Texture("images/arrow.png"), SPRITE_SIZE, SPRITE_SIZE,
                direction.ordinal() * SPRITE_SIZE), position);
        this.direction = getDirectionVector(direction).normalize();
        this.damage = damage;
    }

    @Override
    public void update() {
        super.update();
        Vector2 normalized = direction.normalize();
        Vector3 lastPosition = this.getPosition();
        this.moveBy(new Vector2(normalized.x, normalized.y)
                .mul(Time.getInstance().getDeltaTime() * ARROW_SPEED));
        if (lastPosition.epsilonEquals(this.getPosition(), EPSILON)) {
            WALL_HIT_SOUND.play();
            this.destroy();
        }
        Arrays.asList(Game.getInstance().getMaze().getMonsters()).forEach(monster -> {
            if (monster.getPosition().dst(this.getPosition()) < KILL_DISTANCE) {
                ENEMY_HIT_SOUND.play();
                monster.takeDamage(this.damage);
                this.destroy();
            }
        });
    }

    /**
     * Get the direction vector from a direction.
     *
     * @param direction The direction.
     * @return The direction vector.
     */
    public static Vector2 getDirectionVector(Direction direction) {
        switch (direction) {
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
