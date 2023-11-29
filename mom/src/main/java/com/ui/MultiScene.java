package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

        createMain();
        createServer();
        createClient();
    }

    /**
     * Create the main menu.
     */
    private void createMain() {
        Table root = new Table();
        root.setFillParent(true);
        mainMenu.addActor(root);

        // title table
        Table titleTable = new Table();
        root.add(titleTable).grow().center().row();
        // title
        titleTable.setSkin(new Skin(Gdx.files.internal("skins/pixthulhu-ui.json")));
        titleTable.add("Multiplayer").center();

        // main table
        Table mainTable = new Table();
        root.add(mainTable).grow().center().row();

        // server button
        TextButton serverBtn = new TextButton("Server", skin);
        mainTable.add(serverBtn).expandX();

        serverBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                currentStage = serverMenu;
                Gdx.input.setInputProcessor(serverMenu);
            }
        });

        // client button
        TextButton clientBtn = new TextButton("Client", skin);
        mainTable.add(clientBtn).expandX();

        clientBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                currentStage = clientMenu;
                Gdx.input.setInputProcessor(clientMenu);
            }
        });

        // back button
        TextButton backBtn = new TextButton("Back", skin);
        // table pour mettre le bouton dans le bas gauche
        Table backTable = new Table();
        backTable.add(backBtn).left().bottom().padLeft(PADDING_BACK_BUTTON).padBottom(PADDING_BACK_BUTTON);
        root.add(backTable).expandX().bottom().left();
        // back button listener
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                Window.getInstance().setScene(new MenuScene());
            }
        });
    }

    /**
     * Create the server menu.
     */
    private void createServer() {
        Table root = new Table();
        root.setFillParent(true);
        serverMenu.addActor(root);

        // back button
        TextButton backBtn = new TextButton("Back", skin);
        // table pour mettre le bouton dans le bas gauche
        Table backTable = new Table();
        backTable.add(backBtn).left().bottom().padLeft(PADDING_BACK_BUTTON).padBottom(PADDING_BACK_BUTTON);
        backTable.add(new Table()).grow();
        // back button listener
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                currentStage = mainMenu;
                Gdx.input.setInputProcessor(mainMenu);
            }
        });

        // main table
        Table mainTable = new Table();
        root.add(mainTable).grow().top().row();
        root.add(backTable).grow().bottom();

        // infos table
        Table infosTable = new Table();
        // ip zone
        infosTable.setSkin(skin);
        infosTable.add("IP: ").left();
        String ip = "[IP]";
        infosTable.add(ip).left().row();

        // port zone
        infosTable.add("Port: ").left();
        String port = "[PORT]";
        infosTable.add(port).left().row();

        // start button
        Table btnTable = new Table();
        TextButton startBtn = new TextButton("Start", skin);
        btnTable.add(startBtn).right();

        mainTable.add(infosTable).expandX().center();
        mainTable.add(btnTable).expandX().center();

        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                TextButton playBtn = new TextButton("Play", skin);
                btnTable.clear();
                btnTable.add(playBtn).right();

                playBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buttonClick.play();
                        startNetworkGame(ip, port);
                    }
                });
            }
        });
    }

    /**
     * Create the client menu.
     */
    private void createClient() {
        Table root = new Table();
        root.setFillParent(true);
        clientMenu.addActor(root);

        // back button
        TextButton backBtn = new TextButton("Back", skin);
        // table pour mettre le bouton dans le bas gauche
        Table backTable = new Table();
        backTable.add(backBtn).left().bottom().padLeft(PADDING_BACK_BUTTON).padBottom(PADDING_BACK_BUTTON);
        // back button listener
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                currentStage = mainMenu;
                Gdx.input.setInputProcessor(mainMenu);
            }
        });

        // main table
        Table mainTable = new Table();
        root.add(mainTable).grow().top().row();
        root.add(backTable).expandX().bottom().left();

        TextField ipField = new TextField("IP", skin);
        TextField portField = new TextField("Port", skin);
        TextButton connectBtn = new TextButton("Connect", skin);

        mainTable.add(ipField).width(MIN_WORLD_WIDTH / 2).center().padBottom(PADDING_BACK_BUTTON).row();
        mainTable.add(portField).width(MIN_WORLD_WIDTH / 2).center().padBottom(PADDING_BACK_BUTTON).row();
        mainTable.add(connectBtn).width(MIN_WORLD_WIDTH / 2).center().padBottom(PADDING_BACK_BUTTON).row();

        connectBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick.play();
                mainTable.clear();
                mainTable.setSkin(skin);
                mainTable.add("Waiting for server...").center();

                listenForServer(ipField.getText(), portField.getText());
            }
        });
    }

    private void startNetworkGame(String ip, String port) {
        System.out.println("Starting network game on " + ip + ":" + port);
    }

    private void listenForServer(String ip, String port) {
        System.out.println("Listening for server on " + ip + ":" + port);
    }
}
