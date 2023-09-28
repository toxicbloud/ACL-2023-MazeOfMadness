package com.engine.events;

/**
 * EventType enum.
 * This is the type of event.
 */
public enum EventType {
    /** Key pressed event. */
    KEY_PRESSED,
    /** Key released event. */
    KEY_RELEASED,
    /** Mouse moved event. */
    MOUSE_MOVED,
    /** Mouse pressed event. */
    MOUSE_PRESSED,
    /** Mouse released event. */
    MOUSE_RELEASED,
    /** Mouse scrolled event. */
    MOUSE_SCROLLED,
    /** Gamepad pressed event. */
    GAMEPAD_PRESSED,
    /** Gamepad released event. */
    GAMEPAD_RELEASED,
    /** Gamepad moved event. */
    GAMEPAD_MOVED
}
