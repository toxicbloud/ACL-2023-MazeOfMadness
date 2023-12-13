package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Music;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.editor.EditorScene;
import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.game.Game;
import com.game.Level;
import com.game.LevelLoader;

/**
 * Menu scene.
 */
public class MenuScene extends Scene {
    /**
     *
     */
    private static final int PAD_BOTTOM = 50;
    /**
     * Minimum world height used in the viewport.
     */
    private static final int MIN_WORLD_HEIGHT = 800;
    /**
     * Minimum world width used in the viewport.
     */
    private static final int MIN_WORLD_WIDTH = 800;
    /**
     * Music volume.
     */
    private static final float MUSIC_VOLUME = 0.1f;
    /** Editor button padding from screen border. */
    private static final float PADDING_EDITOR_BUTTON = 16f;
    /**
     * Maze of Madness logo
     * Copyright : Antonin Rousseau.
     */
    private Image logo;
    /**
     * Stage used to draw the main menu.
     */
    private Stage mainMenu;
    /**
     * Stage used to draw the campaign menu.
     */
    private Stage campaignMenu;
    /**
     * current displayed stage.
     */
    private Stage currenStage;
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
     * The music.
     */
    private Music music;

    /**
     * Default constructor.
     */
    public MenuScene() {
        super();
    }

    @Override
    public void update() {
        currenStage.act();
    }

    @Override
    public void render() {
        currenStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        currenStage.getViewport().apply(true);
        currenStage.draw();
    }

    @Override
    public void onEvent(Event ev) {
        // EventVisitor visitor = new UIEventVisitor(this);
        // ev.accept(visitor);
    }

    @Override
    public void create() {
        mainMenu = new Stage(new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT));
        campaignMenu = new Stage(new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT));
        currenStage = mainMenu;
        skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        logo = new Image(new Sprite(new Texture(Gdx.files.internal("images/menus/logo.png"))));
        buttonClick = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        Gdx.input.setInputProcessor(mainMenu);

        Table root = new Table();
        root.setFillParent(true);
        mainMenu.addActor(root);

        root.add(new Table()).expandY().top().row();

        // Table pour centrer le logo
        Table logoTable = new Table();
        logoTable.add(logo).center().padBottom(PAD_BOTTOM).row();
        root.add(logoTable).expandY().expandX().top().row();

        // Table pour les boutons
        Table buttonTable = new Table();

        TextButton campaign = new TextButton(
                "Campaign", skin);
        buttonTable.add(campaign).center().padBottom(PAD_BOTTOM).row();
        TextButton free = new TextButton(
                "Free", skin);
        buttonTable.add(free).center().padBottom(PAD_BOTTOM).row();
        root.add(buttonTable).center().row();
        free.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.stop();
                music.dispose();
                Game.getInstance().loadNew();
            }
        });
        /* CAMPAIGN MENU SECTION */
        campaign.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.stop();
                music.dispose();
                currenStage = campaignMenu;
                Gdx.input.setInputProcessor(campaignMenu);
            }
        });

        // bouton editor
        TextButton editor = new TextButton("Editor", skin);
        // table pour mettre le bouton dans le bas gauche
        Table bottomTable = new Table();
        Table editorTable = new Table();
        bottomTable.add(editorTable).left().grow();
        editorTable.add(editor).left().padLeft(PADDING_EDITOR_BUTTON).padBottom(PADDING_EDITOR_BUTTON);
        editorTable.add(new Table()).grow();
        // Editor button listener
        editor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.stop();
                music.dispose();
                Window.getInstance().setScene(new EditorScene());
            }
        });

        // bouton multi
        TextButton multi = new TextButton("Multiplayer", skin);
        // table pour mettre le bouton dans le bas droit
        bottomTable.add(multi).right().padRight(PADDING_EDITOR_BUTTON).padBottom(PADDING_EDITOR_BUTTON);
        root.add(bottomTable).grow().bottom();
        // Multi button listener
        multi.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.stop();
                music.dispose();
                Window.getInstance().setScene(new MultiScene());
            }
        });

        Table rootCampaign = new Table();
        rootCampaign.setFillParent(true);
        campaignMenu.addActor(rootCampaign);
        TextButton back = new TextButton(
                "Back", skin);
        campaignMenu.addActor(back);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.play();
                currenStage = mainMenu;
                Gdx.input.setInputProcessor(mainMenu);
            }
        });

        // text : Level selection
        Table levelSelectionTable = new Table(skin);
        levelSelectionTable.add("Level selection").center().padBottom(PAD_BOTTOM).row();
        rootCampaign.add(levelSelectionTable).center().row();

        Level level1 = LevelLoader.load(Gdx.files.internal("maps/level1.json"));
        // TextButton : level 1
        TextButton level1btn = new TextButton(
                level1.getName(), skin);
        level1btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.stop();
                music.dispose();
                Game.getInstance().loadFromLevel(level1);
            }
        });
        levelSelectionTable.add(level1btn).center().padBottom(PAD_BOTTOM).row();

        Level level2 = LevelLoader.load(Gdx.files.internal("maps/level2.json"));
        // TextButton : level 2
        TextButton level2btn = new TextButton(
                level2.getName(), skin);
        level2btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                music.stop();
                music.dispose();
                Game.getInstance().loadFromLevel(level2);
            }
        });
        levelSelectionTable.add(level2btn).center().padBottom(PAD_BOTTOM).row();

        music = (Music) Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
        music.play();
        music.setVolume(MUSIC_VOLUME);
    }
}
