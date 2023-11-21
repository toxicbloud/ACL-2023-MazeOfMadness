package com.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
import com.game.monsters.Monster;
import com.game.tiles.Tile;
import com.ui.EndScene;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
    /**
     * Score padding.
     */
    private static final int SCORE_PADDING = 10;
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
    /** Button padding. */
    private static final int BUTTON_PADDING = 20;

    /** Scene camera. */
    private Camera camera;
    /** Player controller. */
    private PlayerController playerController;
    /** Last entered tile by the player. */
    private Tile enteredTile;
    /** Pause menu. */
    private Stage pauseMenu;
    /** boolean to know if the game is paused. */
    private boolean isPaused;
    /**
     * Stage for the UI.
     */
    private Stage hud;
    /**
     * Label for the score.
     */
    private Label scoreLabel;
    /**
     * Indicates if the scene is in edit mode.
     */
    private boolean editMode;

    /**
     * GameScene constructor.
     */
    public GameScene() {
        super();
        camera = new Camera();
        isPaused = false;
    }

    /**
     * GameScene constructor.
     *
     * @param editMode Indicates if the scene is in edit mode.
     */
    public GameScene(boolean editMode) {
        this();
        this.editMode = editMode;
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
        this.playerController = new PlayerController(Game.getInstance().getPlayer());
        if (!editMode) {
            buildMenu();
            buildHUD();
        }
    }

    /**
     * Update the scene.
     */
    public void update() {
        Game game = Game.getInstance();
        Maze maze = game.getMaze();
        if (maze == null) {
            return;
        }

        if (this.playerController != null) {
            playerController.update();
        }

        Player p = game.getPlayer();
        if (p != null) {
            if (p.isDead()) {
                game.setPlayer(null);
                game.setMaze(null);
                Window.getInstance().setScene(new EndScene(false));
                return;
            }
            this.camera.setTargetPosition(p.getPosition());
            handleTileCollision(maze, p);
        }

        // delete monsters that are dead
        for (Monster monster : maze.getMonsters()) {
            if (monster.isDead()) {
                monster.affectScore(game.getScore());
                maze.removeMonster(monster);
            }
        }

        this.camera.update();
        maze.update();
        if (!editMode) {
            pauseMenu.act();
        }
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

        Window.getInstance().getCanvas().end();
        Window.getInstance().getCanvas().begin();

        if (!editMode) {
            drawHUD();
            if (isPaused) {
                pauseMenu.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
                pauseMenu.draw();
            }
        }
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
            case KEY_PRESSED:
                if (((EventKeyPressed) event).getKeyCode() == KeyCode.KEY_ESCAPE) {
                    isPaused = !isPaused;
                    if (isPaused) {
                        Gdx.input.setInputProcessor(pauseMenu);
                    } else {
                        Gdx.input.setInputProcessor(Window.getInstance().getEventManager());
                    }
                }
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
     * Build the HUD UI.
     */
    private void buildHUD() {
        Game game = Game.getInstance();
        hud = new Stage(new ScreenViewport());
        // add score in top left corner
        scoreLabel = new Label("Score: " + Game.getInstance().getScore().getPoints(),
                new Skin(Gdx.files.internal("skins/pixthulhu-ui.json")));
        game.getScore().addPropertyChangeListener("points", evt -> {
            scoreLabel.setText("Score: " + evt.getNewValue());
        });
        Table root = new Table();
        root.top().left();
        root.setFillParent(true);
        root.add(scoreLabel).pad(SCORE_PADDING).row();
        hud.addActor(root);
    }

    /**
     * Build the menu UI.
     */
    private void buildMenu() {
        Skin skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        pauseMenu = new Stage(new ScreenViewport());
        Table root = new Table();
        root.setFillParent(true);
        pauseMenu.addActor(root);
        // continue button
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                Gdx.input.setInputProcessor(Window.getInstance().getEventManager());
            }
        });
        root.add(continueButton).center().padBottom(BUTTON_PADDING).row();
        // exit button
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getInstance().setMaze(null);
                Game.getInstance().setPlayer(null);
                Window.getInstance().setScene(new EndScene(false));
            }
        });
        // max int value to be sure that
        root.add(exitButton).center().padBottom(BUTTON_PADDING).row();
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

    private void drawHUD() {
        hud.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        hud.getViewport().apply(true);
        hud.draw();
    }
}
