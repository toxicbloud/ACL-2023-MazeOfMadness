package com.engine.events;

/**
 * EventGamepadMoved class.
 * This is the gamepad moved event class.
 */
public class EventGamepadMoved extends Event {
    /**
     * GamepadAxis enum.
     * This is the gamepad axis.
     */
    private GamepadAxis axis;
    /**
     * X coordinate.
     * Represented by a float of range [-1, 1].
     */
    private float x;
    /**
     * Y coordinate.
     * Represented by a float of range [-1, 1].
     */
    private float y;

    /**
     * EventGamepadMoved constructor.
     * @param axis The gamepad axis.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public EventGamepadMoved(GamepadAxis axis, float x, float y) {
        super(EventType.GAMEPAD_MOVED);
        this.axis = axis;
    }

    /**
     * Get the gamepad axis.
     * @return The gamepad axis.
     */
    public GamepadAxis getAxis() {
        return this.axis;
    }

    /**
     * Get the x coordinate.
     * @return The x coordinate.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Get the y coordinate.
     * @return The y coordinate.
     */
    public float getY() {
        return this.y;
    }
}
