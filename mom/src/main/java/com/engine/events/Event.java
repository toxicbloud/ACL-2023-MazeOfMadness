package com.engine.events;

/**
 * Event class.
 * This is the base class for all events.
 */
public abstract class Event {
    /**
     * EventType enum.
     * This is the type of event.
     */
    private EventType type;

    /**
     * Event constructor.
     * @param type The type of event.
     */
    protected Event(EventType type) {
        this.type = type;
    }

    /**
     * Get the type of event.
     * @return The type of event.
     */
    public EventType getType() {
        return this.type;
    }
}
