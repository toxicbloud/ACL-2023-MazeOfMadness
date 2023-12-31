package com.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.engine.events.Event;
import com.engine.events.EventManager;
import com.engine.utils.Time;
import org.lwjgl.opengl.GL20;

/**
 * Window class.
 * This is the window class for the game.
 */
public class Window extends Game {
    /** The window instance. */
    private static Window instance;

    /** The window width. */
    private int width = 1280;
    /** The window height. */
    private int height = 720;
    /** The window title. */
    private String title = "Maze Of Madness";
    /** Is the window created. */
    private boolean created;
    /** The current scene. */
    private Scene scene;
    /** The event manager. */
    private EventManager events;
    /** The application. */
    private Lwjgl3Application app;
    /** The canvas. */
    private SpriteBatch canvas;
    /** The hud. */
    private ShapeRenderer hud;

    /**
     * Window constructor.
     * Initialize the window.
     */
    public Window() {
        this.events = new EventManager();
        this.setInstance();
    }

    /**
     * Window constructor.
     * Initialize the window.
     *
     * @param title The window title.
     */
    public Window(String title) {
        this();
        this.title = title;
        this.setInstance();
    }

    /**
     * Window constructor.
     * Initialize the window.
     *
     * @param title  The window title.
     * @param width  The window width.
     * @param height The window height.
     */
    public Window(String title, int width, int height) {
        this(title);
        this.width = width;
        this.height = height;
        this.setInstance();
    }

    private void setInstance() {
        if (Window.instance != null) {
            return;
        } else {
            Window.instance = this;
        }
    }

    /**
     * Window instance getter.
     *
     * @return The window instance.
     */
    public static Window getInstance() {
        return Window.instance;
    }

    /**
     * Initialize the window.
     */
    public void init() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(this.title);
        config.setWindowedMode(this.width, this.height);
        config.useVsync(true);
        config.setResizable(true);

        this.app = new Lwjgl3Application(this, config);
    }

    /**
     * Set the window title.
     *
     * @param title The window title.
     */
    public void setTitle(String title) {
        this.title = title;
        this.app.getGraphics().setTitle(title);
    }

    /**
     * Set the window width.
     *
     * @param width The window width.
     */
    public void setWidth(int width) {
        this.width = width;
        this.app.getGraphics().setWindowedMode(this.width, this.height);
    }

    /**
     * Set the window height.
     *
     * @param height The window height.
     */
    public void setHeight(int height) {
        this.height = height;
        this.app.getGraphics().setWindowedMode(this.width, this.height);
    }

    /**
     * Set the window scene.
     *
     * @param scene The window scene.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
        if (this.created) {
            Gdx.input.setInputProcessor(this.events);
            this.scene.create();
        }
    }

    /**
     * Get the window title.
     *
     * @return The window title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the window width.
     *
     * @return The window width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the window height.
     *
     * @return The window height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Get the window scene.
     *
     * @return The window scene.
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Get the window canvas.
     *
     * @return The window canvas.
     */
    public SpriteBatch getCanvas() {
        return this.canvas;
    }

    /**
     * Get the window HUD.
     *
     * @return The window HUD ShapeRenderer.
     */
    public ShapeRenderer getHUD() {
        return this.hud;
    }

    /**
     * Get the window event manager.
     * @return The window event manager.
     */
    public EventManager getEventManager() {
        return this.events;
    }

    /**
     * Close the window.
     */
    public void close() {
        this.app.exit();
    }

    /**
     * Create the game.
     * This is called once on startup.
     */
    public void create() {
        this.canvas = new SpriteBatch();
        this.hud = new ShapeRenderer();
        this.hud.setAutoShapeType(true);

        Gdx.input.setInputProcessor(this.events);
        Controllers.addListener(this.events);
        if (this.scene != null) {
            this.scene.create();
        }
        this.created = true;
    }

    /**
     * Render the game.
     * This is called once per frame.
     */
    public void render() {
        super.render();

        Event[] evs = this.events.getEvents();
        Time.getInstance().update();

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (this.scene != null) {
            for (Event ev : evs) {
                this.scene.onEvent(ev);
            }
            this.scene.update();
            this.canvas.begin();
            this.scene.render();
            this.canvas.end();
        }
    }

    /**
     * Resize the game.
     * This is called once on startup and every time the window is resized.
     *
     * @param w The new window width.
     * @param h The new window height.
     */
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        this.canvas.getProjectionMatrix().setToOrtho2D(0, 0, this.width, this.height);
        this.hud.getProjectionMatrix().setToOrtho2D(0, 0, this.width, this.height);
        this.hud.updateMatrices();
        if (this.scene != null) {
            this.scene.setWidth(this.width);
            this.scene.setHeight(this.height);
        }
    }

    /**
     * Dispose of the game.
     * This is called once on shutdown.
     */
    public void dispose() {
        super.dispose();
    }

    /**
     * Run the main application.
     */
    public void run() {
        init();
    }
}
