package com.renderer;

import com.engine.utils.Vector3;

/**
 * Camera class.
 * This is the camera class.
 */
public class Camera {
    /** The position of the camera. */
    private Vector3 position;
    /** The zoom of the camera. */
    private float zoom;

    /**
     * Camera constructor.
     */
    public Camera() {
        position = new Vector3();
        zoom = 1.0f;
    }

    /**
     * Camera constructor.
     * @param position The position of the camera.
     * @param zoom The zoom of the camera.
     */
    public Camera(Vector3 position, float zoom) {
        this.position = position;
        this.zoom = zoom;
    }

    /**
     * Get the position of the camera.
     * @return The position of the camera.
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Get the zoom of the camera.
     * @return The zoom of the camera.
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * Set the position of the camera.
     * @param position The position of the camera.
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Set the zoom of the camera.
     * @param zoom The zoom of the camera.
     */
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
