package com.renderer;

import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.game.Game;
import com.game.Maze;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
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
        Game.getInstance().setMaze(new Maze(1, 2, new Tile[]{new GroundRock()}));
    }

    /**
     * Create the scene.
     */
    public void create() {

    }

    /**
     * Update the scene.
     */
    public void update() {
        Maze maze = Game.getInstance().getMaze();
        if (maze == null) {
            return;
        }
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
        // TODO
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
