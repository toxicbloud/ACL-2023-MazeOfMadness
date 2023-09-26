package com.engine;

import com.engine.events.Event;

public abstract class Scene implements Evolvable {
    protected Scene() {

    }

    public abstract void update();

    public abstract void render();

    public abstract void onEvent(Event ev);
}
