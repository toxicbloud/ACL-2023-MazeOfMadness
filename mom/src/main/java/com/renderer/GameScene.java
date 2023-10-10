package com.renderer;

import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventKeyPressed;
import com.engine.events.EventMouseScrolled;
import com.engine.events.KeyCode;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.PlayerController;
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
    /** Maximum theorical maze size. */
    private static final int MAX_MAZE_SIZE = 1000;
    /** Theorical subdivision in one bloc for entity rendering. */
    private static final int BLOC_SUBDIVISION = 100;

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
              position.getX()
            + position.getY()
            + position.getZ() * MAX_MAZE_SIZE
            ) * BLOC_SUBDIVISION;
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
        if (Game.getInstance().getPlayer() == null) {
            Game.getInstance().setPlayer(new Player(new Vector3(2, 2, 1)));
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
                float delta = ((EventMouseScrolled) event).getDelta();
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
