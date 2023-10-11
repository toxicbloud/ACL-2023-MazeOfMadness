package com.game.controllers;

import com.engine.events.EventGamepadMoved;
import com.engine.events.EventGamepadPressed;
import com.engine.events.EventGamepadReleased;
import com.engine.events.EventKeyPressed;
import com.engine.events.EventKeyReleased;
import com.engine.events.EventMouseMoved;
import com.engine.events.EventMousePressed;
import com.engine.events.EventMouseReleased;
import com.engine.events.EventMouseScrolled;
import com.engine.events.EventVisitor;
import com.engine.events.GamepadAxis;
import com.engine.events.KeyCode;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Player;

/**
 * PlayerController class.
 * This is the player controller class.
 */
public class PlayerController extends Controller implements EventVisitor {
    /** DOWN direction movement vector. */
    private static final Vector2 MOVE_VECTOR_DOWN = new Vector2(0.5f, 0.5f);
    /** LEFT direction movement vector. */
    private static final Vector2 MOVE_VECTOR_LEFT = new Vector2(-0.5f, 0.5f);
    /** RIGHT direction movement vector. */
    private static final Vector2 MOVE_VECTOR_RIGHT = new Vector2(0.5f, -0.5f);
    /** UP direction movement vector. */
    private static final Vector2 MOVE_VECTOR_UP = new Vector2(-0.5f, -0.5f);

    /** Controller's wanted target direction. (not normalized) */
    private Vector2 direction = new Vector2();

    /**
     * PlayerController constructor.
     * @param player The player to control.
     */
    public PlayerController(Player player) {
        super(player);
    }

    @Override
    public void update() {
        Entity target = getTarget();
        Vector2 normalized = direction.normalize();
        target.moveBy(
            new Vector2(normalized.x, normalized.y)
            .mul(Time.getInstance().getDeltaTime() * Player.PLAYER_SPEED)
        );
    }

    /**
     * Move the target by a vector.
     * @param v The vector.
     */
    public void moveBy(Vector2 v) {
        getTarget().setPosition(getTarget().getPosition().add(new Vector3(v.x, v.y, 0)));
    }

    @Override
    public void visit(EventMousePressed ev) {

    }

    @Override
    public void visit(EventMouseReleased ev) {

    }

    @Override
    public void visit(EventMouseMoved ev) {

    }

    @Override
    public void visit(EventMouseScrolled ev) {

    }

    @Override
    public void visit(EventGamepadMoved ev) {
        GamepadAxis axis = ev.getAxis();
        if (axis == GamepadAxis.AXIS_LEFT) {
            direction = new Vector2(ev.getX(), ev.getY());
        }
    }

    @Override
    public void visit(EventGamepadPressed ev) {

    }

    @Override
    public void visit(EventGamepadReleased ev) {

    }

    @Override
    public void visit(EventKeyPressed ev) {
        KeyCode code = ev.getKeyCode();
        if (code == null) {
            return;
        }

        switch (code) {
            case KEY_LEFT:
            case KEY_Q:
                direction = direction.add(MOVE_VECTOR_LEFT);
                break;
            case KEY_RIGHT:
            case KEY_D:
                direction = direction.add(MOVE_VECTOR_RIGHT);
                break;
            case KEY_UP:
            case KEY_Z:
                direction = direction.add(MOVE_VECTOR_UP);
                break;
            case KEY_DOWN:
            case KEY_S:
                direction = direction.add(MOVE_VECTOR_DOWN);
                break;
            default: break;
        }
    }

    @Override
    public void visit(EventKeyReleased ev) {
        KeyCode code = ev.getKeyCode();
        if (code == null) {
            return;
        }

        switch (code) {
            case KEY_LEFT:
            case KEY_Q:
                direction = direction.sub(MOVE_VECTOR_LEFT);
                break;
            case KEY_RIGHT:
            case KEY_D:
                direction = direction.sub(MOVE_VECTOR_RIGHT);
                break;
            case KEY_UP:
            case KEY_Z:
                direction = direction.sub(MOVE_VECTOR_UP);
                break;
            case KEY_DOWN:
            case KEY_S:
                direction = direction.sub(MOVE_VECTOR_DOWN);
                break;
            default: break;
        }
    }
}
