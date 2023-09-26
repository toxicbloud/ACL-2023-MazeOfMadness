package com.engine.events;

public class EventGamepadPressed extends Event {
    private GamepadBtn btn;

    public EventGamepadPressed(GamepadBtn btn) {
        super(EventType.GAMEPAD_PRESSED);
        this.btn = btn;
    }

    public GamepadBtn getBtn() {
        return this.btn;
    }
}
