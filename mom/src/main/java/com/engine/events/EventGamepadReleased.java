package com.engine.events;

public class EventGamepadReleased extends Event {
    private GamepadBtn btn;

    public EventGamepadReleased(GamepadBtn btn) {
        super(EventType.GAMEPAD_RELEASED);
        this.btn = btn;
    }

    public GamepadBtn getBtn() {
        return this.btn;
    }
}
