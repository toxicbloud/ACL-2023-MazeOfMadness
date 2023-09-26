package com.engine.events;

public class EventMouseReleased extends Event {
    private MouseBtn btn;

    public EventMouseReleased(MouseBtn btn) {
        super(EventType.MOUSE_RELEASED);
        this.btn = btn;
    }

    public MouseBtn getBtn() {
        return this.btn;
    }
}
