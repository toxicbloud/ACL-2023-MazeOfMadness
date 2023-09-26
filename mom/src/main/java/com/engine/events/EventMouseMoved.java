package com.engine.events;

public class EventMouseMoved extends Event {
    private int x;
    private int y;

    public EventMouseMoved(int x, int y) {
        super(EventType.MOUSE_MOVED);
        this.x = x;
        this.y = y;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
}
