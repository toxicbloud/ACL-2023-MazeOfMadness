package com.engine.utils;

/**
 * Time class.
 * This class is used to get the time.
 */
public final class Time {
    /** Number of milliseconds in a second. */
    private static final float NB_MILLIS_IN_SECOND = 1000.0f;
    /** Time instanced class. */
    private static Time instance;

    /** time at app launch. */
    private long launchTime;
    /** current app time. */
    private long currentTime;
    /** last frame app time. */
    private long lastTime;
    /** delta time between now and last frame. */
    private float deltaTime;
    /** delta time between now and game startup. */
    private float absTime;

    /**
     * Time constructor.
     */
    private Time() {
        launchTime = System.currentTimeMillis();
        currentTime = launchTime;
        lastTime = currentTime;
        deltaTime = 0.0f;
        absTime = 0.0f;
    }

    /**
     * Convert milliseconds to seconds.
     * @param millis The milliseconds.
     * @return The seconds.
     */
    private float millis2seconds(long millis) {
        return millis / NB_MILLIS_IN_SECOND;
    }

    /**
     * Get the time instance.
     * @return The time instance.
     */
    public static Time getInstance() {
        if (Time.instance == null) {
            Time.instance = new Time();
        }
        return Time.instance;
    }

    /**
     * Get the absolute time since app launch.
     * @return The absolute time since app launch.
     */
    public float getAbsTime() {
        return absTime;
    }

    /**
     * Get the delta time between now and last frame.
     * @return The delta time between now and last frame.
     */
    public float getDeltaTime() {
        return deltaTime;
    }

    /**
     * Update the time.
     * Calculate the delta time between now and last frame.
     * (Do before each frame cycle)
     */
    public void update() {
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        deltaTime = millis2seconds(currentTime - lastTime);
        absTime = millis2seconds(currentTime - launchTime);
    }
}
