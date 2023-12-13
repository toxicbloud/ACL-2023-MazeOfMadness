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
import com.ui.EndScene;

/**
 * GameScene class.
 * This is the game scene class.
 */
public class GameScene extends Scene {
    /** Amount of shift in x (screen) direction for block next to of others. */
    public static final float TILE_X_SHIFT = 0.25f;
    /** Amount of shift in y (screen) direction for block on top of others. */
    public static final float TILE_Y_SHIFT = 0.5f;
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

    /** Button padding. */
    private static final int BUTTON_PADDING = 20;

    /** Scene camera. */
    private Camera camera;
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
    /** Pause menu. */
    private Stage pauseMenu;
    /** boolean to know if the game is paused. */
    private boolean isPaused;
    /**
     * The maze to update and render.
     */
    private Maze maze;

    /**
     * GameScene constructor.
     *
     * @param maze The maze to render.
     */
    public GameScene(Maze maze) {
        super();
        camera = new Camera();
        isPaused = false;
        this.maze = maze;
    }

    /**
     * GameScene constructor.
     *
     * @param maze     The maze to render.
     * @param editMode Indicates if the scene is in edit mode.
     */
    public GameScene(Maze maze, boolean editMode) {
        this(maze);
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
                (pos.x - pos.y) / 2.0f,
                pos.z * TILE_Y_SHIFT - (pos.x + pos.y) * TILE_X_SHIFT)
                .mul(zoom)
                .add(new Vector2(gscene.getWidth() / 2, gscene.getHeight() / 2))
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
        Game game = Game.getInstance();
        Player player = game.getPlayer();
        if (!editMode) {
            buildMenu();
            buildHUD();
            new PlayerController(player);
        }
    }

    /**
     * Update the scene.
     */
    public void update() {
        Game game = Game.getInstance();
        if (maze == null) {
            return;
        }

        Player p = Game.getInstance().getPlayer();
        if (p != null) {
            if (p.isDead()) {
                Window.getInstance().setScene(new EndScene(Game.getInstance().end(), false));
                return;
            }
            this.camera.setTargetPosition(p.getPosition());
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
        Player player = Game.getInstance().getPlayer();
        if (player != null) {
            PlayerController controller = (PlayerController) player.getController();
            if (controller != null) {
                event.accept(controller);
            }
        }
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
                new Skin(Gdx.files.internal("skins/pixthulhu-ui.json")), "subtitle");
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
                Window.getInstance().setScene(new EndScene(Game.getInstance().end(), false));
            }
        });
        // max int value to be sure that
        root.add(exitButton).center().padBottom(BUTTON_PADDING).row();
    }

    private void drawHUD() {
        hud.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        hud.getViewport().apply(true);
        hud.draw();
    }

    /**
     * Set the maze of the scene.
     *
     * @param maze The maze.
     */
    protected void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Get the maze of the scene.
     *
     * @return The maze.
     */
    protected Maze getMaze() {
        return maze;
    }
}
