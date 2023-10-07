package com.renderer;

import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventKeyPressed;
import com.engine.events.EventMouseScrolled;
import com.engine.events.KeyCode;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Maze;
import com.game.tiles.GroundGrass;
import com.game.tiles.GroundLava;
import com.game.tiles.GroundRock;
import com.game.tiles.GroundWater;
import com.game.tiles.Stair;
import com.game.tiles.StairGrass;
import com.game.tiles.StairRock;
import com.game.tiles.Tile;
import com.game.tiles.VoidTile;
import com.game.tiles.WallRock;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
    /** TEST_MAZE_WIDTH. */
    private static final int TEST_MAZE_WIDTH = 5;
    /** TEST_MAZE_HEIGHT. */
    private static final int TEST_MAZE_HEIGHT = 5;
    /** TEST_MAZE_DEPTH. */
    private static final int TEST_MAZE_DEPTH = 3;
    /** Mouse delta to camera zoom conversion. */
    private static final float DELTA_2_ZOOM = 0.1f;
    /** Camera zoom multiplier. */
    private static final float ZOOM_MULTIPLIER = 1.0f;
    /**
     * Camera object.
     * This is the scene camera.
     */
    private Camera camera;

    /**
     * GameScene constructor.
     */
    public GameScene() {
        super();
        camera = new Camera();
    }

    /**
     * Main method.
     * For testing purposes only.
     * @param args The arguments.
     */
    public static void main(String[] args) {
        Window win = new Window();
        win.setScene(new GameScene());
        win.run();
    }

    /**
     * Create the scene.
     */
    public void create() {
        Game.getInstance().setMaze(new Maze(TEST_MAZE_WIDTH, TEST_MAZE_HEIGHT, TEST_MAZE_DEPTH, new Tile[]{
            new WallRock(), new WallRock(), new WallRock(), new WallRock(), new WallRock(),
            new WallRock(), new GroundRock(), new GroundRock(), new GroundRock(), new WallRock(),
            new WallRock(), new GroundRock(), new GroundWater(), new GroundRock(), new WallRock(),
            new WallRock(), new GroundRock(), new GroundRock(), new GroundRock(), new WallRock(),
            new WallRock(), new WallRock(), new WallRock(), new WallRock(), new WallRock(),

            new WallRock(), new WallRock(), new WallRock(), new WallRock(), new WallRock(),
            new WallRock(), new GroundRock(), new GroundRock(), new GroundLava(), new WallRock(),
            new WallRock(), new StairRock(Stair.DIRECTION_Y), new VoidTile(), new VoidTile(), new WallRock(),
            new WallRock(), new VoidTile(), new VoidTile(), new VoidTile(), new WallRock(),
            new WallRock(), new VoidTile(), new VoidTile(), new VoidTile(), new WallRock(),

            new GroundGrass(), new StairGrass(Stair.DIRECTION_X), new VoidTile(), new VoidTile(), new VoidTile(),
            new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(),
            new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(),
            new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(),
            new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile(), new VoidTile()
        }));
    }

    /**
     * Update the scene.
     */
    public void update() {
        Maze maze = Game.getInstance().getMaze();
        if (maze == null) {
            return;
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
                float delta = ((EventMouseScrolled) event).getDelta();
                this.camera.setZoom(this.camera.getZoom() * (delta * DELTA_2_ZOOM + ZOOM_MULTIPLIER));
                break;
            case KEY_PRESSED:
                KeyCode key = ((EventKeyPressed) event).getKeyCode();
                camera.move(new Vector3(
                    (key == KeyCode.KEY_RIGHT ? 1 : 0) - (key == KeyCode.KEY_LEFT ? 1 : 0),
                    (key == KeyCode.KEY_UP ? 1 : 0) - (key == KeyCode.KEY_DOWN ? 1 : 0),
                    0
                ));
                break;
            default: break;
        }
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
