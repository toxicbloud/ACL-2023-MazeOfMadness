package com.engine.events;

public class EventGamepadMoved extends Event {
    private GamepadAxis axis;
    private float x;
    private float y;

    public EventGamepadMoved(GamepadAxis axis, float x, float y) {
        super(EventType.GAMEPAD_MOVED);
        this.axis = axis;
    }

    public GamepadAxis getAxis() {
        return this.axis;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
