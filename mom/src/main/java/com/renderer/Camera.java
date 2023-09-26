package com.renderer;

import com.engine.utils.Vector3;

public class Camera {
    private Vector3 position;
    private float zoom;

    public Camera() {
        position = new Vector3();
        zoom = 1.0f;
    }

    public Camera(Vector3 position, float zoom) {
        this.position = position;
        this.zoom = zoom;
    }

    public Vector3 getPosition() {
        return position;
    }

    public float getZoom() {
        return zoom;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
