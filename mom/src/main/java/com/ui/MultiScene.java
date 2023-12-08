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
import com.game.Entity;
import com.game.Game;
import com.game.Item;
import com.game.Maze;
import com.game.generators.MazeFactory;
import com.game.monsters.Monster;
import com.game.tiles.Tile;
import com.network.GameSceneClient;
import com.network.GameSceneServer;
import com.network.MultiManager;
import com.network.NetworkClient;
import com.network.NetworkDialogs;
import com.network.NetworkMazeBuilder;
import com.network.NetworkServer;

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

    /** Multiplayer manager. */
    private MultiManager manager;

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
        this.manager = new MultiManager();
    }

    @Override
    public void update() {
        currentStage.act();
        manager.update();
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
        String ip = "localhost";
        infosTable.add(ip).left().row();

        // port zone
        infosTable.add("Port: ").left();
        int port = NetworkServer.PORT;
        infosTable.add(Integer.toString(port)).left().row();

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

                Maze maze = MazeFactory.createMaze();
                server = createNetworkServer(ip, port, maze);

                playBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buttonClick.play();
                        startServerGame(server, maze);
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

        TextField ipField = new TextField("localhost", skin);
        TextField portField = new TextField("5621", skin);
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
                setTextStatus("Connecting to server...");

                try {
                    int portNbr = Integer.parseInt(portField.getText());
                    listenForServer(ipField.getText(), portNbr);
                } catch (NumberFormatException e) {
                    setTextStatus("Error : Invalid port number !");
                }
            }
        });
    }

    /**
     * Set the status text.
     * @param text Text to set.
     * @param shouldClear Clear the text before setting it.
     */
    private void setTextStatus(String text, boolean shouldClear) {
        Table root = (Table) clientMenu.getActors().get(0);
        Table debugText = (Table) root.getChild(0);
        if (shouldClear) {
            debugText.getChild(debugText.getChildren().size - 1).remove();
        }
        if (text != null) {
            debugText.add(text).center().row();
        }
    }

    private void setTextStatus(String text) {
        setTextStatus(text, false);
    }

    private NetworkServer createNetworkServer(String ip, int port, Maze maze) {
        NetworkServer serv = new NetworkServer(port);
        serv.when(
            (data, infos) -> {
                return data.length > 0 && data[0] == NetworkDialogs.REGISTER_RQT;
            },
            (data, infos) -> {
                serv.sendData(new byte[]{NetworkDialogs.REGISTER_RSP}, infos);
            }
        );
        serv.when(
            (data, infos) -> {
                return data.length > 0 && data[0] == NetworkDialogs.MAZE_RQT;
            },
            (data, infos) -> {
                int mazeDataLength =
                    maze.getTiles().length
                    + maze.getMonsters().length
                    + maze.getItems().length;
                byte[] mazeDataNbPaquets = new byte[]{NetworkDialogs.MAZE_LGH, 0, 0};
                NetworkDialogs.encodeIntValue(mazeDataLength, mazeDataNbPaquets, 1);
                serv.sendData(mazeDataNbPaquets, infos);

                for (Tile t : maze.getTiles()) {
                    byte[] tileData = NetworkDialogs.encodeTileValue(t, 1);
                    tileData[0] = NetworkDialogs.MAZE_ADD;
                    serv.sendData(tileData, infos);
                }
                for (Monster m : maze.getMonsters()) {
                    byte[] monsterData = NetworkDialogs.encodeMonsterValue(m, 1);
                    monsterData[0] = NetworkDialogs.MAZE_ADD;
                    serv.sendData(monsterData, infos);
                }
                for (Item i : maze.getItems()) {
                    byte[] itemData = NetworkDialogs.encodeItemValue(i, 1);
                    itemData[0] = NetworkDialogs.MAZE_ADD;
                    serv.sendData(itemData, infos);
                }
            }
        );
        return serv;
    }

    private void startServerGame(NetworkServer serv, Maze m) {
        Game.getInstance().loadFrom(m);

        serv.sendData(new byte[]{NetworkDialogs.GAME_STR});
        Window.getInstance().setScene(new GameSceneServer(
            Game.getInstance().getMaze(),
            serv));
    }

    private void listenForServer(String ip, int port) {
        System.out.println("Listening for server on " + ip + ":" + port);
        this.client = new NetworkClient(ip, port);

        setTextStatus("Registering ...");
        if (!this.client.sendData(new byte[]{NetworkDialogs.REGISTER_RQT})) {
            setTextStatus("Error : Failed to register !");
        }

        this.client.once(
            (data, infos) -> {
                return data.length > 0 && data[0] == NetworkDialogs.REGISTER_RSP;
            },
            (data, infos) -> {
                setTextStatus("Registering ... OK", true);
                askForMaze();
            }
        );
    }

    /**
     * Ask the server for the maze data.
     */
    private void askForMaze() {
        setTextStatus("Getting maze data ...");
        if (!this.client.sendData(new byte[]{NetworkDialogs.MAZE_RQT})) {
            setTextStatus("Error : Failed to get maze data !");
        }

        NetworkMazeBuilder builder = new NetworkMazeBuilder();
        this.client.once(
            (data, infos) -> {
                return data.length > 2 && data[0] == NetworkDialogs.MAZE_LGH;
            },
            (data, infos) -> {
                builder.setDataLength(NetworkDialogs.getIntValue(data, 1));
                infos.setTemp(0);
            }
        );
        this.client.when(
            (data, infos) -> {
                if (data[0] != NetworkDialogs.MAZE_ADD) {
                    System.out.println("Invalid packet type (" + data[0] + ") | " + new String(data));
                }
                return data.length > 2 && data[0] == NetworkDialogs.MAZE_ADD;
            },
            (data, infos) -> {
                Entity e = NetworkDialogs.getEntityFromData(data, 1);
                if (e == null) {
                    System.out.println("Error getting entity from data");
                    return;
                }
                builder.addEntity(e);
                int nbEntities = builder.getEntitiesNumber();
                int totalNb = builder.getDataLength();
                int percent = nbEntities * NetworkDialogs.HUNDRED / totalNb;
                setTextStatus("Getting maze data ... " + percent + "%", true);

                if (nbEntities == totalNb) {
                    waitForGameStart(builder);
                }
            }
        );
    }

    /**
     * Wait for the game to start.
     * @param builder Maze builder.
     */
    private void waitForGameStart(NetworkMazeBuilder builder) {
        setTextStatus("Getting maze data ... OK", true);
        setTextStatus("Waiting for game to start ...");

        this.client.once(
            (data, infos) -> {
                return data.length > 0 && data[0] == NetworkDialogs.GAME_STR;
            },
            (data, infos) -> {
                setTextStatus("Waiting for game to start ... OK", true);
                startClientGame(builder);
            }
        );
    }

    private void startClientGame(NetworkMazeBuilder builder) {
        System.out.println("Starting network game on client");

        Maze maze = builder.build();
        Game.getInstance().loadFrom(maze);
        Window.getInstance().setScene(new GameSceneClient(
            Game.getInstance().getMaze(),
            this.client));
    }
}
