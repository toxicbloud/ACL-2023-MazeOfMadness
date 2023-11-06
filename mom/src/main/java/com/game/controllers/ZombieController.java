package com.game.controllers;

import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.game.Entity;
import com.game.monsters.Zombie;

/**
 * PlayerController class.
 * This is the player controller class.
 */
public class ZombieController extends Controller {

    /** Maximum distance to walk before changing direction. */
    public static final double TRIGGER_DISTANCE = 8.0;
    /** Maximum time waiting without moving. */
    public static final double TRIGGER_TIMEOUT = 8.0;
    /** Percent of chance to idle instead of walking. */
    public static final double PERCENT_IDLE = 0.4;
    /** Percent of chance to move on the X axis (minus idle percent). */
    public static final double PERCENT_MOVEX = 0.3;
    /** One half constant. */
    public static final double HALF = 0.5;

    /** Controller's wanted target direction. (not normalized) */
    private Vector2 direction = new Vector2();

    /** Old zombie position when last decision has been made. */
    private Vector2 lastPosition = new Vector2();

    /**
     * Time counter since last decision has been made.
     */
    private float timeCounter;

    /**
     * ZombieController constructor.
     * @param zombie The zombie to control.
     */
    public ZombieController(Zombie zombie) {
        super(zombie);
        this.determineDirectionPattern();
    }

    @Override
    public void update() {
        Entity target = getTarget();
        Vector2 normalized = direction.normalize();
        Vector2 oldPosition = new Vector2(target.getPosition().x, target.getPosition().y);
        target.moveBy(
            new Vector2(normalized.x, normalized.y)
            .mul(Time.getInstance().getDeltaTime() * Zombie.ZOMBIE_SPEED)
        );
        Vector2 newPosition = new Vector2(target.getPosition().x, target.getPosition().y);
        timeCounter += Time.getInstance().getDeltaTime();

        boolean stuck = oldPosition.x == newPosition.x && oldPosition.y == newPosition.y;
        boolean tooFar = newPosition.dst(lastPosition) > (Math.random() * TRIGGER_DISTANCE + 2.0);
        boolean timeout = timeCounter > Math.random() * TRIGGER_TIMEOUT + 2.0;
        if (stuck && !direction.isZero() || tooFar || timeout && direction.isZero()) {
            lastPosition = newPosition;
            this.determineDirectionPattern();
        }
    }

    /**
     * Calculated if the Zombie should move in Y or X directions
     * for it's movement pattern.
     */
    private void determineDirectionPattern() {
        this.timeCounter = 0;
        double guess = Math.random();
        if (guess < PERCENT_IDLE) {
            direction.x = 0;
            direction.y = 0;
        } else if (guess < PERCENT_MOVEX) {
            direction.x = Math.random() > HALF ? 1 : -1;
            direction.y = 0;
        } else {
            direction.x = 0;
            direction.y = Math.random() > HALF ? 1 : -1;
        }
    }
}