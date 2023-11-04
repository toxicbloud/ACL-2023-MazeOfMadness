package com.renderer;

import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventMouseScrolled;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.PlayerController;
import com.game.tiles.Tile;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
    /** Mouse delta to camera zoom conversion. */
    private static final float DELTA_2_ZOOM = 0.1f;
    /** Camera zoom multiplier. */
    private static final float ZOOM_MULTIPLIER = 1.0f;
    /** Theorical subdivision in one bloc for entity rendering. */
    private static final int BLOC_SUBDIVISION = 100;
    /** Z order multiplier. */
    private static final float Z_ORDER_MULTIPLIER = 4.0f;
    /** Amount of shift in x (screen) direction for block on top of others. */
    private static final float TILE_X_SHIFT = 0.25f;
    /** Amount of shift in y (screen) direction for block on top of others. */
    private static final float TILE_Y_SHIFT = 0.5f;

    /** Scene camera. */
    private Camera camera;
    /** Player controller. */
    private PlayerController playerController;

    private Tile enteredTile;

    /**
     * GameScene constructor.
     */
    public GameScene() {
        super();
        camera = new Camera();
    }

    /**
     * Get the drawing order of an object.
     *
     * @param position The position of the object.
     * @return The drawing order of the object.
     */
    public static int getObjectDrawingOrder(Vector3 position) {
        return (int) (position.getX() * 2
                + position.getY() * 2
                + position.getZ() * Z_ORDER_MULTIPLIER) * BLOC_SUBDIVISION;
    }

    /**
     * Convert a world coordinate to a screen coordinate.
     *
     * @param coord The world coordinate.
     * @return The screen coordinate.
     */
    public static Vector2 getWorldToScreenCoordinates(Vector3 coord) {
        Window window = Window.getInstance();
        GameScene gscene = (GameScene) window.getScene();
        Camera cam = gscene.getCamera();
        float zoom = cam.getZoom();

        Vector3 pos = coord.sub(cam.getPosition());

        return new Vector2(
                // coord.x - coord.z / 2,
                // coord.y - coord.z / 2
                (pos.x - pos.y) / 2.0f,
                pos.z * TILE_Y_SHIFT - (pos.x + pos.y) * TILE_X_SHIFT)
                .mul(zoom)
                .add(new Vector2(window.getWidth() / 2, window.getHeight() / 2))
                .sub(zoom / 2);
    }

    /**
     * Convert a world size to a screen size.
     *
     * @param size The world size.
     * @return The screen size.
     */
    public static Vector2 getWorldToScreenSize(Vector3 size) {
        GameScene scene = (GameScene) Window.getInstance().getScene();
        float zoom = scene.getCamera().getZoom();

        return new Vector2(
                size.x + size.z,
                size.y + size.z).mul(zoom / 2);
    }

    /**
     * Create the scene.
     */
    public void create() {
        if (Game.getInstance().getPlayer() == null) {
            Game.getInstance().setPlayer(
                    new Player(Game.getInstance().getMaze().getSpawnPoint()));
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
            handleTileCollision(maze, p);
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
     *
     * @param event The event.
     */
    public void onEvent(Event event) {
        switch (event.getType()) {
            case MOUSE_SCROLLED:
                float delta = -((EventMouseScrolled) event).getDelta();
                this.camera.setZoom(this.camera.getZoom() * (delta * DELTA_2_ZOOM + ZOOM_MULTIPLIER));
                break;
            default:
                break;
        }
        event.accept(playerController);
    }

    /**
     * Get the scene camera.
     *
     * @return The scene camera.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Set the scene camera.
     *
     * @param camera The scene camera.
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Trigger the tile when the player enters it and trigger the
     * tile when the player exits it.
     *
     * @param maze   The maze.
     * @param player The player.
     */
    private void handleTileCollision(Maze maze, Player player) {
        // find the tile under the player
        Vector3 pos = player.getPosition();
        int x = Math.round(pos.x);
        int y = Math.round(pos.y);
        int z = Math.round(pos.z);
        Tile tile = maze.getTile(x, y, z - 1);

        if (tile != null && tile != enteredTile) {
            tile.onPlayerEnter(player);
            if (enteredTile != null) {
                enteredTile.onPlayerExit(player);
            }
            enteredTile = tile;
        }
    }
}
