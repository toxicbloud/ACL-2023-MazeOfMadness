package com.engine.events;

/**
 * EventMouseScrolled class.
 * This is the mouse scrolled event class.
 */
public class EventMouseScrolled extends Event {
    /**
     * Delta float.
     * This is the delta of the mouse scroll.
     */
    private float delta;

    /**
     * EventMouseScrolled constructor.
     * @param delta The delta of the mouse scroll.
     */
    public EventMouseScrolled(float delta) {
        super(EventType.MOUSE_SCROLLED);
        this.delta = delta;
    }

    /**
     * Get the delta of the mouse scroll.
     * @return The delta of the mouse scroll.
     */
    public float getDelta() {
        return delta;
    }
}
