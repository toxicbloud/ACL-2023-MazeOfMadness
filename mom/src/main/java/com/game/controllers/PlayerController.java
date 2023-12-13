package com.game.controllers;

import com.engine.SoundManager;
import com.engine.SoundManager.SoundList;
import com.engine.events.*;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.game.*;

import java.util.List;

/**
 * PlayerController class.
 * This is the player controller class.
 */
public class PlayerController extends Controller implements EventVisitor {
    /** KEY_LEFT index in arrows array. */
    private static final int KEY_LEFT_INDEX = 0;
    /** KEY_RIGHT index in arrows array. */
    private static final int KEY_RIGHT_INDEX = 1;
    /** KEY_UP index in arrows array. */
    private static final int KEY_UP_INDEX = 2;
    /** KEY_DOWN index in arrows array. */
    private static final int KEY_DOWN_INDEX = 3;
    /** To know if the player attacks or no. */
    private boolean attack;
    /** The last time when the player attacks. */
    private long lastAttackTime;
    /** To know if the player tries to interact with an item or not. */
    private boolean interact;

    /** Controller's wanted target direction. (not normalized) */
    private Vector2 direction = new Vector2();

    /** Controller's pressed arrows for direction. */
    private boolean[] arrows = new boolean[4];

    /**
     * PlayerController constructor.
     *
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
                        .mul(Time.getInstance().getDeltaTime() * ((Living) target).getSpeed()));
        if (attack
                && Time.getInstance().getCurrentTime() - lastAttackTime > ((Player) target).getWeapon().getCooldown()) {
            List<Living> enemies = ((Player) target).findEnemies();
            for (Living enemy : enemies) {
                ((Player) target).getWeapon().setPosition(target.getPosition());
                ((Player) target).getWeapon().attack(enemy);
            }
            lastAttackTime = Time.getInstance().getCurrentTime();
            SoundManager.getInstance().play(SoundList.ATTACK);
        }
        if (interact) {
            Item item = ((Player) getTarget()).findItemInRange();
            if (item != null) {
                item.interact((Player) getTarget());
                Game.getInstance().getMaze().removeItem(item);
                SoundManager.getInstance().play(SoundList.POTION);
            }
        }
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
                arrows[KEY_LEFT_INDEX] = true;
                break;
            case KEY_RIGHT:
            case KEY_D:
                arrows[KEY_RIGHT_INDEX] = true;
                break;
            case KEY_UP:
            case KEY_Z:
                arrows[KEY_UP_INDEX] = true;
                break;
            case KEY_DOWN:
            case KEY_S:
                arrows[KEY_DOWN_INDEX] = true;
                break;
            case KEY_SPACE:
                attack = true;
                break;
            case KEY_F:
                interact = true;
                break;
            default:
                break;
        }
        this.updateDirectionVector();
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
                arrows[KEY_LEFT_INDEX] = false;
                break;
            case KEY_RIGHT:
            case KEY_D:
                arrows[KEY_RIGHT_INDEX] = false;
                break;
            case KEY_UP:
            case KEY_Z:
                arrows[KEY_UP_INDEX] = false;
                break;
            case KEY_DOWN:
            case KEY_S:
                arrows[KEY_DOWN_INDEX] = false;
                break;
            case KEY_SPACE:
                attack = false;
                break;
            case KEY_F:
                interact = false;
                break;
            default:
                break;
        }
        this.updateDirectionVector();
    }

    private void updateDirectionVector() {
        this.direction = new Vector2(
                (arrows[KEY_RIGHT_INDEX] ? 1 : 0) - (arrows[KEY_LEFT_INDEX] ? 1 : 0),
                (arrows[KEY_DOWN_INDEX] ? 1 : 0) - (arrows[KEY_UP_INDEX] ? 1 : 0)).normalize();
    }
}
