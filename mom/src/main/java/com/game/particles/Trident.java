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
 * Trident particle class.
 */
public class Trident extends Particle {
    /**
     *
     */
    private static final int TRIDENT_MAX_FLY_DISTANCE = 4;
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
     * Kill distance.
     */
    private static final float KILL_DISTANCE = 0.5f;
    /**
     * Trident sound played when the trident hits a enemy.
     */
    private static final Sound ENEMY_HIT_SOUND = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));

    /**
     * Damage of the trident.
     */
    private static final int DAMAGE = 100;

    /**
     * Distance at which the trident is moved.
     */
    private Vector2 direction;
    /**
     * Start position of the trident.
     */
    private Vector3 startPos;

    /**
     * Trident particle constructor.
     *
     * @param position  The position of the particle.
     * @param direction The direction where the particle is going.
     */
    public Trident(Vector3 position, Direction direction) {
        super(new Sprite(new Texture("images/trident.png"), SPRITE_SIZE, SPRITE_SIZE,
                direction.ordinal() * SPRITE_SIZE + SPRITE_SHIFT * SPRITE_SIZE),
                position);
        this.direction = getDirectionVector(direction);
        this.startPos = position;
    }

    @Override
    public void update() {
        Vector2 normalized = direction.normalize();
        Vector3 lastPosition = this.getPosition();
        this.moveBy(new Vector2(normalized.x, normalized.y)
                .mul(Time.getInstance().getDeltaTime() * TRIDENT_SPEED));

        Arrays.asList(Game.getInstance().getMaze().getMonsters()).forEach(monster -> {
            if (monster.getPosition().dst(this.getPosition()) < KILL_DISTANCE) {
                ENEMY_HIT_SOUND.play();
                monster.takeDamage(DAMAGE);
            }
        });
        if (lastPosition.epsilonEquals(this.getPosition(), EPSILON)
                || this.startPos.dst(this.getPosition()) > TRIDENT_MAX_FLY_DISTANCE) {
            Game.getInstance().getMaze().addItem(new com.game.items.weapons.Trident(this.getPosition()));
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
