package com.engine.events;

/**
 * EventMouseMoved class.
 * This is the mouse moved event class.
 */
public class EventMouseMoved extends Event {
    /**
     * X coordinate.
     * X coordinate of the mouse, in pixels.
     */
    private int x;
    /**
     * Y coordinate.
     * Y coordinate of the mouse, in pixels.
     */
    private int y;

    /**
     * EventMouseMoved constructor.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public EventMouseMoved(int x, int y) {
        super(EventType.MOUSE_MOVED);
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x coordinate.
     * @return The x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the y coordinate.
     * @return The y coordinate.
     */
    public int getY() {
        return this.y;
    }
}
