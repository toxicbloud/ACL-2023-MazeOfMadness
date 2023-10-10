package com.engine.events;

/**
 * EventVisitor interface.
 * this visitor is used to dispatch events
 */
public interface EventVisitor {

    /**
     * Visits a mouse pressed event and dispatches it.
     *
     * @param eventMousePressed the mouse pressed event to visit
     */
    void visit(EventMousePressed eventMousePressed);

    /**
     * Visits a mouse released event and dispatches it.
     *
     * @param ev the mouse released event to visit
     */
    void visit(EventMouseReleased ev);

    /**
     * Visits a mouse moved event and dispatches it.
     *
     * @param ev the mouse moved event to visit
     */
    void visit(EventMouseMoved ev);

    /**
     * Visits a Gamepad moved event and dispatches it.
     *
     * @param eventGamepadMoved the Gamepad moved event to visit
     */
    void visit(EventGamepadMoved eventGamepadMoved);

    /**
     * Visits a gamepad pressed event and dispatches it.
     *
     * @param eventGamepadPressed the gamepad pressed event to visit
     */
    void visit(EventGamepadPressed eventGamepadPressed);

    /**
     * Visits a gamepad released event and dispatches it.
     *
     * @param eventGamepadReleased the gamepad released event to visit
     */
    void visit(EventGamepadReleased eventGamepadReleased);

    /**
     * Visits a key released event and dispatches it.
     *
     * @param eventKeyReleased the key released event to visit
     */
    void visit(EventKeyReleased eventKeyReleased);

    /**
     * Visits a MouseScrolled event and dispatches it.
     *
     * @param eventMouseScrolled the MouseScrolled event to visit
     */
    void visit(EventMouseScrolled eventMouseScrolled);

    /**
     * Visits a key pressed event and dispatches it.
     *
     * @param eventKeyPressed the key pressed event to visit
     */
    void visit(EventKeyPressed eventKeyPressed);
}
