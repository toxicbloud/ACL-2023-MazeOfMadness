package com.game.controllers;

import com.game.Entity;

/**
 * Controller class.
 * This is the base class for all controllers.
 */
public abstract class Controller {
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
     * Update the controller.
     * This method is called every frame, before any render() call.
     */
    public abstract void update();

    /**
     * Get the target of the controller.
     * @return The target.
     */
    public Entity getTarget() {
        return target;
    }
}
