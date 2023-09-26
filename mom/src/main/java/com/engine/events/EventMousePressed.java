package com.engine.events;

public class EventMousePressed extends Event {
    private MouseBtn btn;

    public EventMousePressed(MouseBtn btn) {
        super(EventType.MOUSE_PRESSED);
        this.btn = btn;
    }

    public MouseBtn getBtn() {
        return this.btn;
    }
}
