package com.engine.events;

/**
 * EventKeyReleased class.
 * This is the key released event class.
 */
public class EventKeyReleased extends Event {
    /**
     * KeyCode enum.
     * This is the key code.
     */
    private KeyCode keyCode;

    /**
     * EventKeyReleased constructor.
     * @param keyCode The key code.
     */
    public EventKeyReleased(KeyCode keyCode) {
        super(EventType.KEY_RELEASED);
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
