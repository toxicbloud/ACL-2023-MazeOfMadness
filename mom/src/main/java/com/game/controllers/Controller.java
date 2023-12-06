package com.game.controllers;

import com.engine.Evolvable;
import com.game.Entity;

/**
 * Controller class.
 * This is the base class for all controllers.
 */
public abstract class Controller implements Evolvable {
    /** Controller's target. */
    private Entity target;

    /**
     * Controller constructor.
     * @param target The target of the controller.
     */
    public Controller(Entity target) {
        this.target = target;
        this.target.registerController(this);
    }

    /**
     * Makes the controller forget the target.
     * This method is called when the target is destroyed.
     * Or when the controller is destroyed.
     */
    public void unregister() {
        this.target = null;
    }

    /**
     * Update the controller.
     * This method is called every frame, before any render() call.
     */
    public abstract void update();

    @Override
    public void render() {}

    /**
     * Get the target of the controller.
     * @return The target.
     */
    public Entity getTarget() {
        return target;
    }
}
