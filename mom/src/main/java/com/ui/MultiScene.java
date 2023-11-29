package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;

/**
 * Menu scene.
 */
public class MultiScene extends Scene {
    /**
     * Minimum world height used in the viewport.
     */
    private static final int MIN_WORLD_HEIGHT = 800;
    /**
     * Minimum world width used in the viewport.
     */
    private static final int MIN_WORLD_WIDTH = 800;
    /** Editor button padding from screen border. */
    private static final float PADDING_BACK_BUTTON = 16f;
    /**
     * Stage used to draw the main menu.
     */
    private Stage mainMenu;
    /**
     * Stage used to draw the campaign menu.
     */
    private Stage serverMenu;
    /**
     * current displayed stage.
     */
    private Stage clientMenu;
    /**
     * current displayed stage.
     */
    private Stage currentStage;
    /**
     * UI skin style.
     */
    private Skin skin;

    /**
     * The button click sound.
     * punch sound
     */
    private Sound buttonClick;

    /**
     * Default constructor.
     */
    public MultiScene() {
        super();
    }

    @Override
    public void update() {
        currentStage.act();
    }

    @Override
    public void render() {
        currentStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        currentStage.getViewport().apply(true);
        currentStage.draw();
    }

    @Override
    public void onEvent(Event ev) {
        // EventVisitor visitor = new UIEventVisitor(this);
        // ev.accept(visitor);
    }

    @Override
    public void create() {
        mainMenu = new Stage(new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT));
        serverMenu = new Stage(new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT));
        clientMenu = new Stage(new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT));
        currentStage = mainMenu;
        skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        buttonClick = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        Gdx.input.setInputProcessor(mainMenu);

        Table root = new Table();
        root.setFillParent(true);
        mainMenu.addActor(root);

        // bouton editor
        TextButton backBtn = new TextButton("Back", skin);
        // table pour mettre le logo dans le bas gauche
        Table backTable = new Table();
        backTable.add(backBtn).left().bottom().padLeft(PADDING_BACK_BUTTON).padBottom(PADDING_BACK_BUTTON);
        root.add(backTable).expandY().bottom().left();
        // Editor button listener
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                Window.getInstance().setScene(new MenuScene());
            }
        });
    }
}
