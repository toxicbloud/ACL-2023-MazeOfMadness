package com.engine.events;

/**
 * EventKeyPressed class.
 * This is the key pressed event class.
 */
public class EventKeyPressed extends Event {
    /**
     * KeyCode enum.
     * This is the key code.
     */
    private KeyCode keyCode;

    /**
     * EventKeyPressed constructor.
     * @param keyCode The key code.
     */
    public EventKeyPressed(KeyCode keyCode) {
        super(EventType.KEY_PRESSED);
        this.keyCode = keyCode;
    }

    /**
     * Get the key code.
     * @return The key code.
     */
    public KeyCode getKeyCode() {
        return this.keyCode;
    }
}
