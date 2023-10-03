package com.engine.events;

/**
 * EventGamepadReleased class.
 * This is the gamepad released event class.
 */
public class EventGamepadReleased extends Event {
    /**
     * GamepadBtn enum.
     * This is the gamepad button.
     */
    private GamepadBtn btn;

    /**
     * EventGamepadReleased constructor.
     *
     * @param btn The gamepad button.
     */
    public EventGamepadReleased(GamepadBtn btn) {
        super(EventType.GAMEPAD_RELEASED);
        this.btn = btn;
    }

    /**
     * Get the gamepad button.
     *
     * @return The gamepad button.
     */
    public GamepadBtn getBtn() {
        return this.btn;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }
}
