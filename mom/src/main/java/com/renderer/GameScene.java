package com.renderer;

import com.engine.Scene;
import com.engine.events.Event;
import com.engine.events.EventMouseScrolled;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.PlayerController;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
    /** Mouse delta to camera zoom conversion. */
    private static final float DELTA_2_ZOOM = 0.1f;
    /** Camera zoom multiplier. */
    private static final float ZOOM_MULTIPLIER = 1.0f;
    /** Maximum theorical maze size. */
    private static final int MAX_MAZE_SIZE = 1000;
    /** Theorical subdivision in one bloc for entity rendering. */
    private static final int BLOC_SUBDIVISION = 100;
    /** Z order multiplier. */
    private static final float Z_ORDER_MULTIPLIER = 4.0f;

    /** Scene camera. */
    private Camera camera;
    /** Player controller. */
    private PlayerController playerController;

    /**
     * GameScene constructor.
     */
    public GameScene() {
        super();
        camera = new Camera();
    }

    /**
     * Get the drawing order of an object.
     * @param position The position of the object.
     * @return The drawing order of the object.
     */
    public static int getObjectDrawingOrder(Vector3 position) {
        return (int) (
              position.getX() * 2
            + position.getY() * 2
            + position.getZ() * Z_ORDER_MULTIPLIER
            ) * BLOC_SUBDIVISION;
    }

    /**
     * Create the scene.
     */
    public void create() {
        if (Game.getInstance().getPlayer() == null) {
            Game.getInstance().setPlayer(
                new Player(Game.getInstance().getMaze().getSpawnPoint())
            );
        }
        this.playerController = new PlayerController(Game.getInstance().getPlayer());
    }

    /**
     * Update the scene.
     */
    public void update() {
        Maze maze = Game.getInstance().getMaze();
        if (maze == null) {
            return;
        }

        if (this.playerController != null) {
            playerController.update();
        }

        Player p = Game.getInstance().getPlayer();
        if (p != null) {
            this.camera.setTargetPosition(p.getPosition());
        }

        this.camera.update();
        maze.update();
    }

    /**
     * Render the scene.
     */
    public void render() {
        Maze maze = Game.getInstance().getMaze();
        if (maze == null) {
            return;
        }
        maze.render();
    }

    /**
     * Handle an event.
     * @param event The event.
     */
    public void onEvent(Event event) {
        switch (event.getType()) {
            case MOUSE_SCROLLED:
                float delta = -((EventMouseScrolled) event).getDelta();
                this.camera.setZoom(this.camera.getZoom() * (delta * DELTA_2_ZOOM + ZOOM_MULTIPLIER));
                break;
            default: break;
        }
        event.accept(playerController);
    }

    /**
     * Get the scene camera.
     * @return The scene camera.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Set the scene camera.
     * @param camera The scene camera.
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
