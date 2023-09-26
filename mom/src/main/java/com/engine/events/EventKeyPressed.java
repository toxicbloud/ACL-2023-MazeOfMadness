package com.engine.events;

public class EventKeyPressed extends Event {
    private KeyCode keyCode;

    public EventKeyPressed(KeyCode keyCode) {
        super(EventType.KEY_PRESSED);
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode() {
        return this.keyCode;
    }
}
