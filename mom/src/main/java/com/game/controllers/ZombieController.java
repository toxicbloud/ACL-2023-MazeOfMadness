package com.game.controllers;

import com.engine.utils.Movement;
import com.engine.utils.PathFinder;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Game;
import com.game.Player;
import com.game.monsters.Zombie;

/**
 * ZombieController class.
 * This is the zombie controller class.
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

    /** Distance from movement step position to trigger next movement. */
    private static final float MOVEMENT_THRESHOLD = 0.2f;
    /** Movement mode (random). */
    private static final boolean MODE_RANDOM = false;
    /** Movement mode (smart). */
    private static final boolean MODE_PATHFINDING = true;
    /** The last time when the zombie attacks. */
    private long lastAttackTime;

    /** Controller's wanted target direction. (not normalized) */
    private Vector2 direction = new Vector2();

    /** Old zombie position when last decision has been made. */
    private Vector2 lastPosition = new Vector2();

    /** Time counter since last decision has been made. */
    private float timeCounter;
    /** Current movement mode (smart or random). */
    private boolean mode;
    /** Path to follow in smart mode. */
    private Movement path;

    /**
     * ZombieController constructor.
     *
     * @param zombie The zombie to control.
     */
    public ZombieController(Zombie zombie) {
        super(zombie);
        this.mode = MODE_PATHFINDING;

        if (this.mode == MODE_RANDOM) {
            this.determineDirectionPattern();
        }
    }

    @Override
    public void update() {
        Player player = Game.getInstance().getPlayer();
        Entity target = getTarget();
        Zombie zombie = (Zombie) target;

        if (zombie == null || player == null) {
            return;
        }

        if (zombie.findPlayer(player)
            && Time.getInstance().getCurrentTime() - lastAttackTime > zombie.getWeapon().getCooldown()) {
            zombie.getWeapon().setPosition(target.getPosition());
            zombie.getWeapon().attack(player);
            lastAttackTime = Time.getInstance().getCurrentTime();
        }

        if (this.mode == MODE_RANDOM) {
            if (target.distance(player) < Zombie.VIEW_DISTANCE) {
                this.mode = MODE_PATHFINDING;
                return;
            }

            Vector2 normalized = direction.normalize();
            Vector2 oldPosition = new Vector2(target.getPosition().x, target.getPosition().y);
            target.moveBy(
                    new Vector2(normalized.x, normalized.y)
                            .mul(Time.getInstance().getDeltaTime() * Zombie.ZOMBIE_SPEED));
            Vector2 newPosition = new Vector2(target.getPosition().x, target.getPosition().y);
            timeCounter += Time.getInstance().getDeltaTime();

            boolean stuck = oldPosition.x == newPosition.x && oldPosition.y == newPosition.y;
            boolean tooFar = newPosition.dst(lastPosition) > (Math.random() * TRIGGER_DISTANCE + 2.0);
            boolean timeout = timeCounter > Math.random() * TRIGGER_TIMEOUT + 2.0;
            if (stuck && !direction.isZero() || tooFar || timeout && direction.isZero()) {
                lastPosition = newPosition;
                this.determineDirectionPattern();
            }
        } else {
            if (player == null) {
                return;
            }

            if (path == null) {
                PathFinder finder = PathFinder.fromMaze(target.getPosition(), player.getPosition());
                if (finder == null || !finder.findPath()) {
                    this.mode = MODE_RANDOM;
                    return;
                } else {
                    path = finder.getPath();
                }
            }

            if (path == null) {
                this.mode = MODE_RANDOM;
                return;
            } else {
                Vector3 movementPos = new Vector3(path.getX(), path.getY(), target.getPosition().z);
                if (target.getPosition().dst(movementPos) < MOVEMENT_THRESHOLD) {
                    // OLD : Complete one entire path before checking for new movements
                    // path = path.getNext();

                    // NEW : Each time we complete one step of a path, we check for new movements
                    // (if player isn't too far)
                    if (target.distance(player) > Zombie.LEAVE_DISTANCE) {
                        this.mode = MODE_RANDOM;
                        return;
                    }

                    PathFinder finder = PathFinder.fromMaze(target.getPosition(), player.getPosition());
                    if (finder == null || !finder.findPath()) {
                        this.mode = MODE_RANDOM;
                        return;
                    } else {
                        path = finder.getPath();
                    }
                }

                Vector3 normalized = new Vector3(
                        movementPos.x,
                        movementPos.y,
                        target.getPosition().z).sub(target.getPosition()).nor();
                target.moveBy(
                        new Vector2(normalized.x, normalized.y)
                                .mul(Time.getInstance().getDeltaTime() * Zombie.ZOMBIE_SPEED));
            }
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
