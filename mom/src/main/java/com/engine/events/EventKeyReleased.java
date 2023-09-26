package com.engine.events;

public class EventKeyReleased extends Event {
    private KeyCode keyCode;

    public EventKeyReleased(KeyCode keyCode) {
        super(EventType.KEY_RELEASED);
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode() {
        return this.keyCode;
    }
}
