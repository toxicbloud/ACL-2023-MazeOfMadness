package com.engine.events;

/**
 * EventMouseReleased class.
 * This is the mouse released event class.
 */
public class EventMouseReleased extends Event {
    /**
     * MouseBtn enum.
     * This is the mouse button.
     */
    private MouseBtn btn;

    /**
     * EventMouseReleased constructor.
     * @param btn The mouse button.
     */
    public EventMouseReleased(MouseBtn btn) {
        super(EventType.MOUSE_RELEASED);
        this.btn = btn;
    }

    /**
     * Get the mouse button.
     * @return The mouse button.
     */
    public MouseBtn getBtn() {
        return this.btn;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }
}
