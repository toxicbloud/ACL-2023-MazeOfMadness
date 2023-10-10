package com.engine.events;

/**
 * EventMousePressed class.
 * This is the mouse pressed event class.
 */
public class EventMousePressed extends Event {
    /**
     * MouseBtn enum.
     * This is the mouse button.
     */
    private MouseBtn btn;

    /**
     * EventMousePressed constructor.
     * @param btn The mouse button.
     */
    public EventMousePressed(MouseBtn btn) {
        super(EventType.MOUSE_PRESSED);
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
