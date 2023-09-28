package com.engine.events;

/**
 * EventGamepadPressed class.
 * This is the gamepad pressed event class.
 */
public class EventGamepadPressed extends Event {
    /**
     * GamepadBtn enum.
     * This is the gamepad button.
     */
    private GamepadBtn btn;

    /**
     * EventGamepadPressed constructor.
     * @param btn The gamepad button.
     */
    public EventGamepadPressed(GamepadBtn btn) {
        super(EventType.GAMEPAD_PRESSED);
        this.btn = btn;
    }

    /**
     * Get the gamepad button.
     * @return The gamepad button.
     */
    public GamepadBtn getBtn() {
        return this.btn;
    }
}
