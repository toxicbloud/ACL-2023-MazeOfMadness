package com.engine.utils;

public class Time {
    private static Time Instance = null;

    public static Time GetInstance() {
        if (Time.Instance == null)
            Time.Instance = new Time();
        return Time.Instance;
    }

    private float launchTime;
    private float currentTime;
    private float lastTime;
    private float deltaTime;

    private float millis2seconds(long millis) { return millis / 1000.0f; }

    private Time() {
        launchTime = millis2seconds(System.currentTimeMillis());
        currentTime = launchTime;
        lastTime = currentTime;
        deltaTime = 0.0f;
    }

    public float getAbsTime() {
        return currentTime - launchTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void update() {
        lastTime = currentTime;
        currentTime = millis2seconds(System.currentTimeMillis());
        deltaTime = currentTime - lastTime;
    }
}
