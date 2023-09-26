package com.engine.events;

public class EventMouseScrolled extends Event {
    private float delta;

    public EventMouseScrolled(float delta) {
        super(EventType.MOUSE_SCROLLED);
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }
}
