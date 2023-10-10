package com.renderer;

import com.engine.utils.Time;
import com.engine.utils.Vector3;

/**
 * Camera class.
 * This is the camera class.
 */
public class Camera {
    /** Default camera zoom. */
    private static final float DEFAULT_CAMERA_ZOOM = 60.0f;
    /** Default camera speed. */
    private static final float CAMERA_SPEED = 4.0f;
    /** Camera shift from target point. */
    private static final Vector3 CAMERA_SHIFT = new Vector3(0.0f, 0.0f, 0.0f);

    /** The position of the camera. */
    private Vector3 position;
    /** The target position of the camera. */
    private Vector3 targetPos;
    /** The zoom of the camera. */
    private float zoom;

    /**
     * Camera constructor.
     */
    public Camera() {
        position = new Vector3();
        this.targetPos = this.position;
        zoom = DEFAULT_CAMERA_ZOOM;
    }

    /**
     * Camera constructor.
     * @param position The position of the camera.
     * @param zoom The zoom of the camera.
     */
    public Camera(Vector3 position, float zoom) {
        this.position = position;
        this.targetPos = this.position;
        this.zoom = zoom;
    }

    /**
     * Updates the camera.
     */
    public void update() {
        this.position = new Vector3(
            this.position.x + (this.targetPos.x - this.position.x) * Time.getInstance().getDeltaTime() * CAMERA_SPEED,
            this.position.y + (this.targetPos.y - this.position.y) * Time.getInstance().getDeltaTime() * CAMERA_SPEED,
            this.position.z + (this.targetPos.z - this.position.z) * Time.getInstance().getDeltaTime() * CAMERA_SPEED
        );
    }

    /**
     * Move camera of delta position.
     * @param delta movement delta
     */
    public void move(Vector3 delta) {
        this.targetPos = this.targetPos.add(delta);
    }

    /**
     * Get the position of the camera.
     * @return The position of the camera.
     */
    public Vector3 getPosition() {
        return this.position;
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
        this.targetPos = position;
    }

    /**
     * Set the target position of the camera.
     * @param pos The target position of the camera.
     */
    public void setTargetPosition(Vector3 pos) {
        this.targetPos = pos.add(CAMERA_SHIFT);
    }

    /**
     * Set the zoom of the camera.
     * @param zoom The zoom of the camera.
     */
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
