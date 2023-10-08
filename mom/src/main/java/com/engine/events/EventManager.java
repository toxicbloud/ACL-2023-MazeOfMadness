package com.engine.events;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventManager class.
 * This is the event manager class.
 */
public class EventManager implements InputProcessor {
    /**
     * Events list
     * This is the list of events.
     */
    private List<Event> events = new ArrayList<Event>();
    /**
     * Key2Code map
     * This is the map of keys to key codes.
     */
    private Map<Integer, KeyCode> key2code = new HashMap<Integer, KeyCode>();
    /**
     * Btn2Code map
     * This is the map of mouse buttons to mouse button codes.
     */
    private Map<Integer, MouseBtn> btn2code = new HashMap<Integer, MouseBtn>();

    /**
     * EventManager constructor.
     */
    public EventManager() {
        this.events.clear();
        key2code.put(Keys.A, KeyCode.KEY_Q);
        key2code.put(Keys.B, KeyCode.KEY_B);
        key2code.put(Keys.C, KeyCode.KEY_C);
        key2code.put(Keys.D, KeyCode.KEY_D);
        key2code.put(Keys.E, KeyCode.KEY_E);
        key2code.put(Keys.F, KeyCode.KEY_F);
        key2code.put(Keys.G, KeyCode.KEY_G);
        key2code.put(Keys.H, KeyCode.KEY_H);
        key2code.put(Keys.I, KeyCode.KEY_I);
        key2code.put(Keys.J, KeyCode.KEY_J);
        key2code.put(Keys.K, KeyCode.KEY_K);
        key2code.put(Keys.L, KeyCode.KEY_L);
        key2code.put(Keys.M, KeyCode.KEY_M);
        key2code.put(Keys.N, KeyCode.KEY_N);
        key2code.put(Keys.O, KeyCode.KEY_O);
        key2code.put(Keys.P, KeyCode.KEY_P);
        key2code.put(Keys.Q, KeyCode.KEY_A);
        key2code.put(Keys.R, KeyCode.KEY_R);
        key2code.put(Keys.S, KeyCode.KEY_S);
        key2code.put(Keys.T, KeyCode.KEY_T);
        key2code.put(Keys.U, KeyCode.KEY_U);
        key2code.put(Keys.V, KeyCode.KEY_V);
        key2code.put(Keys.W, KeyCode.KEY_Z);
        key2code.put(Keys.X, KeyCode.KEY_X);
        key2code.put(Keys.Y, KeyCode.KEY_Y);
        key2code.put(Keys.Z, KeyCode.KEY_W);
        key2code.put(Keys.NUM_0, KeyCode.KEY_0);
        key2code.put(Keys.NUM_1, KeyCode.KEY_1);
        key2code.put(Keys.NUM_2, KeyCode.KEY_2);
        key2code.put(Keys.NUM_3, KeyCode.KEY_3);
        key2code.put(Keys.NUM_4, KeyCode.KEY_4);
        key2code.put(Keys.NUM_5, KeyCode.KEY_5);
        key2code.put(Keys.NUM_6, KeyCode.KEY_6);
        key2code.put(Keys.NUM_7, KeyCode.KEY_7);
        key2code.put(Keys.NUM_8, KeyCode.KEY_8);
        key2code.put(Keys.NUM_9, KeyCode.KEY_9);
        key2code.put(Keys.SPACE, KeyCode.KEY_SPACE);
        key2code.put(Keys.ENTER, KeyCode.KEY_ENTER);
        key2code.put(Keys.ESCAPE, KeyCode.KEY_ESCAPE);
        key2code.put(Keys.UP, KeyCode.KEY_UP);
        key2code.put(Keys.DOWN, KeyCode.KEY_DOWN);
        key2code.put(Keys.LEFT, KeyCode.KEY_LEFT);
        key2code.put(Keys.RIGHT, KeyCode.KEY_RIGHT);
        key2code.put(Keys.SHIFT_LEFT, KeyCode.KEY_SHIFT);
        key2code.put(Keys.SHIFT_RIGHT, KeyCode.KEY_SHIFT);
        key2code.put(Keys.CONTROL_LEFT, KeyCode.KEY_CTRL);
        key2code.put(Keys.CONTROL_RIGHT, KeyCode.KEY_CTRL);
        key2code.put(Keys.ALT_LEFT, KeyCode.KEY_ALT);
        key2code.put(Keys.ALT_RIGHT, KeyCode.KEY_ALT);
        key2code.put(Keys.TAB, KeyCode.KEY_TAB);
        key2code.put(Keys.BACKSPACE, KeyCode.KEY_BACKSPACE);
        key2code.put(Keys.DEL, KeyCode.KEY_DELETE);
        key2code.put(Keys.HOME, KeyCode.KEY_HOME);
        key2code.put(Keys.END, KeyCode.KEY_END);

        btn2code.put(Buttons.LEFT, MouseBtn.BTN_LEFT);
        btn2code.put(Buttons.RIGHT, MouseBtn.BTN_RIGHT);
        btn2code.put(Buttons.MIDDLE, MouseBtn.BTN_MIDDLE);
    }

    /**
     * Get the events.
     *
     * @return The events.
     */
    public Event[] getEvents() {
        if (this.events.size() == 0) {
            return new Event[0];
        }

        Event[] result = (Event[]) (this.events.toArray(new Event[this.events.size()]));
        this.events.clear();
        return result;
    }

    /**
     * Add an event.
     *
     * @param ev The event.
     */
    private void addEvent(Event ev) {
        this.events.add(ev);
    }

    /**
     * Key down event listener.
     *
     * @param keycode The key code.
     * @return true if the event was handled, false otherwise.
     */
    public boolean keyDown(int keycode) {
        addEvent(new EventKeyPressed(key2code.get(keycode)));
        return false;
    }

    /**
     * Key up event listener.
     *
     * @param keycode The key code.
     * @return true if the event was handled, false otherwise.
     */
    public boolean keyUp(int keycode) {
        addEvent(new EventKeyReleased(key2code.get(keycode)));
        return false;
    }

    /**
     * Key typed event listener.
     *
     * @param character The character.
     * @return true if the event was handled, false otherwise.
     */
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Mouse button down event listener.
     *
     * @param x       The x coordinate.
     * @param y       The y coordinate.
     * @param pointer The pointer.
     * @param button  The mouse button.
     * @return true if the event was handled, false otherwise.
     */
    public boolean touchDown(int x, int y, int pointer, int button) {
        addEvent(new EventMousePressed(btn2code.get(button)));
        return false;
    }

    /**
     * Mouse button up event listener.
     *
     * @param x       The x coordinate.
     * @param y       The y coordinate.
     * @param pointer The pointer.
     * @param button  The mouse button.
     * @return true if the event was handled, false otherwise.
     */
    public boolean touchUp(int x, int y, int pointer, int button) {
        addEvent(new EventMouseReleased(btn2code.get(button)));
        return false;
    }

    /**
     * Mouse drag event listener.
     *
     * @param x       The x coordinate.
     * @param y       The y coordinate.
     * @param pointer The pointer.
     * @return true if the event was handled, false otherwise.
     */
    public boolean touchDragged(int x, int y, int pointer) {
        addEvent(new EventMouseMoved(x, y));
        return false;
    }

    /**
     * Mouse move event listener.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return true if the event was handled, false otherwise.
     */
    public boolean mouseMoved(int x, int y) {
        addEvent(new EventMouseMoved(x, y));
        return false;
    }

    /**
     * Mouse scroll event listener.
     *
     * @param amountX The x amount.
     * @param amountY The y amount.
     * @return true if the event was handled, false otherwise.
     */
    public boolean scrolled(float amountX, float amountY) {
        addEvent(new EventMouseScrolled(amountY));
        return false;
    }

    @Override
    public boolean touchCancelled(int arg0, int arg1, int arg2, int arg3) {
        return false;
    }
}
