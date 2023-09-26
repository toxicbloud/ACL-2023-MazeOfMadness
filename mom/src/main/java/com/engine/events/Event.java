package com.engine.events;

public abstract class Event {
    private EventType type;

    protected Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return this.type;
    }
}
