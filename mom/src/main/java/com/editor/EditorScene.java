package com.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventMouseMoved;
import com.engine.events.EventMouseReleased;
import com.engine.events.EventType;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Game;
import com.game.Level;
import com.game.LevelLoader;
import com.game.LevelSaver;
import com.game.Maze;
import com.game.Player;
import com.game.WorldItem;
import com.game.items.potions.HealthPotion;
import com.game.items.potions.SpeedPotion;
import com.game.items.potions.StrengthPotion;
import com.game.monsters.Ghost;
import com.game.monsters.Monster;
import com.game.monsters.Zombie;
import com.game.tiles.End;
import com.game.tiles.GroundLava;
import com.game.tiles.GroundRock;
import com.game.tiles.GroundSpikes;
import com.game.tiles.GroundWater;
import com.game.tiles.Next;
import com.game.tiles.Tile;
import com.game.tiles.TileType;
import com.game.tiles.WallRock;
import com.renderer.GameScene;
import com.ui.MenuScene;

import java.awt.FileDialog;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Editor scene.
 * This is the scene used to edit a game level.
 */
public class EditorScene extends GameScene {
    /** Height of the editor tab. */
    private static final int EDITOR_TAB_HEIGHT = 200;
    /** Width of the size buttons zones. */
    private static final int SIDE_BUTTONS_ZONE = 250;
    /** Selected entity preview tile size. */
    private static final int SELECTED_ENTITY_SIZE = 120;
    /** Padding between editor buttons. */
    private static final int EDITOR_BUTTON_PADDING = 4;
    /** Mouse movement deadzone for click trigger. */
    private static final int CLICK_DST_EDITOR = 4;
    /** Color of the editor tab background. */
    private static final Color TAB_BACKGROUND_COLOR = new Color(0.2f, 0.22f, 0.25f, 1.0f);

    /** Mouse pan sensivity in game scene. */
    private static final float PAN_SENSIVITY = 0.05f;

    /** Whether the mouse is in the game scene zone. */
    private boolean inGameSceneZone;
    /** Is the mouse pressed or not. */
    private boolean mousePressed;
    /** Last action's mouse position on screen. */
    private Vector2 lastMousePosition = new Vector2();
    /** Last click's mouse position on screen. */
    private Vector2 lastMouseClickPosition = new Vector2();
    /** Current mmouse position on screen. */
    private Vector2 mousePosition = new Vector2();

    /** Placeholder block. For 3D cursor visualization. */
    private PlaceholderBlock placeholderBlock;

    /** Stage for Editor top interface. */
    private Stage stage;

    /** All entities that can be added through editor. */
    private Entity[] selectableEntities;

    /** Index of entity selected to be added through editor. */
    private int selectedEntityIndex;

    /** Current Z level for editor raycast selection. */
    private int editorZLevel;

    /** List of entities in the maze. */
    private List<Entity> mazeEntities = new ArrayList<Entity>();

    /** fake Player entity. */
    private Player player = new Player(new Vector3(0, 0, 1));

    /**
     * EditorScene constructor.
     */
    public EditorScene() {
        super(null, true);
        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        selectableEntities = new Entity[] {
            new GroundRock(),
            new WallRock(),
            new End(),
            new Next(),
            new GroundSpikes(),
            new GroundWater(),
            new GroundLava(),
            new HealthPotion(),
            new StrengthPotion(),
            new SpeedPotion(),
            new Zombie(),
            new Ghost(),
            new Player()
        };
    }

    @Override
    public void create() {
        super.create();
        this.placeholderBlock = new PlaceholderBlock();
        mazeEntities.add(new GroundRock());
        generateMazeFromList();

        this.buildEditorTab();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(Window.getInstance().getEventManager());
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void update() {
        stage.act();
        super.update();
    }

    @Override
    public void render() {
        Maze maze = super.getMaze();
        if (maze != null) {
            if (this.inGameSceneZone) {
                maze.addTemporaryEntity(this.placeholderBlock);
            }
            super.getMaze().addTemporaryEntity(this.player);
        }
        super.render();
        renderEditorTab();
        Entity selectedEntity = selectableEntities[selectedEntityIndex];
        if (selectedEntity != null) {
            Vector2 size = new Vector2(SELECTED_ENTITY_SIZE, SELECTED_ENTITY_SIZE);
            selectedEntity.getSprite().render(
                    new Vector2(
                            Window.getInstance().getWidth() / 2,
                            Window.getInstance().getHeight() - EDITOR_TAB_HEIGHT / 2)
                            .sub(size.div(2)),
                    size);
        }
    }

    @Override
    public void onEvent(Event ev) {
        if (ev.getType() == EventType.MOUSE_MOVED) {
            EventMouseMoved event = (EventMouseMoved) ev;
            this.inGameSceneZone = event.getY() > EDITOR_TAB_HEIGHT;
            this.mousePosition = new Vector2(event.getX(), event.getY());
        }

        if (this.inGameSceneZone) {
            this.handleGameSceneEvent(ev);
        }
    }

    @Override
    public int getHeight() {
        return super.getHeight() - EDITOR_TAB_HEIGHT;
    }

    private void buildEditorTab() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Skin skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        TextButton loadBtn = new TextButton("Load", skin);
        TextButton saveBtn = new TextButton("Save", skin);
        TextButton backBtn = new TextButton("Back", skin);
        TextButton prevBtn = new TextButton("<", skin);
        TextButton nextBtn = new TextButton(">", skin);
        TextButton upBtn = new TextButton("^", skin);
        TextButton downBtn = new TextButton("v", skin);

        Table mainTable = new Table();

        Table loadSaveButtons = new Table();
        loadSaveButtons.add(loadBtn)
                .height(EDITOR_TAB_HEIGHT / 2 - EDITOR_BUTTON_PADDING * 2)
                .pad(EDITOR_BUTTON_PADDING);
        loadSaveButtons.row();
        loadSaveButtons.add(saveBtn)
                .height(EDITOR_TAB_HEIGHT / 2 - EDITOR_BUTTON_PADDING * 2)
                .pad(EDITOR_BUTTON_PADDING);

        Table upDownButtons = new Table();
        upDownButtons.add(upBtn)
                .height(EDITOR_TAB_HEIGHT / 2 - EDITOR_BUTTON_PADDING * 2)
                .pad(EDITOR_BUTTON_PADDING);
        upDownButtons.add(downBtn)
                .height(EDITOR_TAB_HEIGHT / 2 - EDITOR_BUTTON_PADDING * 2)
                .pad(EDITOR_BUTTON_PADDING);

        Table backButtons = new Table();
        backButtons.add(backBtn)
                .height(EDITOR_TAB_HEIGHT / 2 - EDITOR_BUTTON_PADDING * 2)
                .pad(EDITOR_BUTTON_PADDING);
        backButtons.row();
        backButtons.add(upDownButtons)
                .height(EDITOR_TAB_HEIGHT / 2 - EDITOR_BUTTON_PADDING * 2)
                .pad(EDITOR_BUTTON_PADDING);

        Table selectorTable = new Table();
        Table placeholderSelected = new Table();
        selectorTable.add(prevBtn).pad(EDITOR_BUTTON_PADDING);
        selectorTable.add(placeholderSelected).width(EDITOR_TAB_HEIGHT);
        selectorTable.add(nextBtn).pad(EDITOR_BUTTON_PADDING);

        mainTable.add(backButtons).width(SIDE_BUTTONS_ZONE);
        mainTable.add(selectorTable).growX();
        mainTable.add(loadSaveButtons).width(SIDE_BUTTONS_ZONE);
        root.add(mainTable).expand().growX().top();

        loadBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                FileDialog dialog = new FileDialog((java.awt.Frame) null, "Select File to Open");
                dialog.setFilenameFilter((dir, name) -> name.endsWith(".json"));
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                String file = dialog.getFile();
                String path = dialog.getDirectory();

                if (file == null) {
                    return;
                }
                loadFile(path + file);
            }
        });
        saveBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Level level = new Level(
                        "level name",
                        "level description",
                        "level author",
                        "level version",
                        getMaze(),
                        player);

                FileDialog dialog = new FileDialog((java.awt.Frame) null, "Select File to Save");
                dialog.setFilenameFilter((dir, name) -> name.endsWith(".json"));
                dialog.setMode(FileDialog.SAVE);
                dialog.setVisible(true);
                String file = dialog.getFile();
                String path = dialog.getDirectory();

                if (file == null) {
                    return;
                }
                LevelSaver.save(new FileHandle(new File(path + file)), level);

                loadFile(path + file);
            }
        });
        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Window.getInstance().setScene(new MenuScene());
                Game.getInstance().end();
            }
        });
        upBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                editorZLevel++;
            }
        });
        downBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                editorZLevel = Math.max(0, editorZLevel - 1);
            }
        });
        prevBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedEntityIndex = Math.max(0, selectedEntityIndex - 1);
            }
        });
        nextBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedEntityIndex = Math.min(selectableEntities.length - 1, selectedEntityIndex + 1);
            }
        });
    }

    private void onAddEntity(Vector3 pos) {
        boolean alreadyExists = mazeEntities.stream().anyMatch(e -> e.getPosition().equals(pos));
        if (alreadyExists) {
            return;
        }

        Entity selectedEntity = selectableEntities[selectedEntityIndex];

        if (selectedEntity instanceof Player) {
            this.player.setPosition(pos);
            return;
        }

        Entity newEntity = null;
        try {
            Constructor<?> constructor = selectedEntity.getClass().getDeclaredConstructor();
            newEntity = (Entity) constructor.newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (newEntity != null) {
            newEntity.setPosition(pos);
            mazeEntities.add(newEntity);
        }
        generateMazeFromList();
    }

    private void onDelEntity(Vector3 pos) {
        for (Entity e : mazeEntities) {
            if (e.getPosition().equals(pos)) {
                mazeEntities.remove(e);
                break;
            }
        }
        generateMazeFromList();
    }

    private void renderEditorTab() {
        SpriteBatch canvas = Window.getInstance().getCanvas();
        ShapeRenderer hud = Window.getInstance().getHUD();

        canvas.end();
        hud.begin(ShapeType.Filled);

        hud.setColor(TAB_BACKGROUND_COLOR);
        hud.rect(
                0,
                super.getHeight() - EDITOR_TAB_HEIGHT,
                super.getWidth(),
                EDITOR_TAB_HEIGHT);

        hud.end();

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.getViewport().apply(true);
        stage.draw();
        canvas.begin();
    }

    private void handleGameSceneEvent(Event ev) {
        switch (ev.getType()) {
            case MOUSE_SCROLLED:
                super.onEvent(ev);
                break;
            case MOUSE_PRESSED:
                this.mousePressed = true;
                lastMousePosition = mousePosition;
                lastMouseClickPosition = mousePosition;
                break;
            case MOUSE_RELEASED:
                float distance = lastMouseClickPosition.dst(mousePosition);
                if (distance < CLICK_DST_EDITOR) {
                    EventMouseReleased event = (EventMouseReleased) ev;
                    switch (event.getBtn()) {
                        case BTN_LEFT:
                            this.onAddEntity(this.placeholderBlock.getPosition());
                            break;
                        case BTN_RIGHT:
                            this.onDelEntity(this.placeholderBlock.getPosition());
                            break;
                        default:
                            break;
                    }
                }
                this.mousePressed = false;
                lastMousePosition = mousePosition;
                break;
            case MOUSE_MOVED:
                if (mousePressed) {
                    Vector2 delta = new Vector2(
                            (lastMousePosition.x - mousePosition.x) * getHeight(),
                            (lastMousePosition.y - mousePosition.y) * getWidth());

                    this.getCamera().setPosition(
                            this.getCamera().getPosition()
                                    .add(new Vector3(
                                            (delta.x / 2 + delta.y / 2) / getCamera().getZoom() * PAN_SENSIVITY,
                                            (delta.y / 2 - delta.x / 2) / getCamera().getZoom() * PAN_SENSIVITY,
                                            0)));
                    lastMousePosition = mousePosition;
                } else {
                    EventMouseMoved event = (EventMouseMoved) ev;
                    Vector3 cursorPos = getBlocAtCursor(new Vector2(event.getX(), event.getY()), editorZLevel);
                    this.placeholderBlock.setPosition(cursorPos);
                }
                break;
            default:
                break;
        }
    }

    private Vector3 getBlocAtCursor(Vector2 pos, float z) {
        Vector2 screenPos = pos.sub(new Vector2(getWidth() / 2, getHeight() / 2 + EDITOR_TAB_HEIGHT));
        Vector2 worldPos = screenPos.div(getCamera().getZoom());
        worldPos = new Vector2(worldPos.x, -worldPos.y);

        float a = worldPos.x;
        float b = worldPos.y;
        float y = ((z * GameScene.TILE_Y_SHIFT - b) / GameScene.TILE_X_SHIFT - 2 * a) / 2f;
        float x = y + 2 * a;

        return new Vector3(
                Math.round(x + getCamera().getPosition().x),
                Math.round(y + getCamera().getPosition().y),
                Math.round(z + getCamera().getPosition().z));
    }

    private void generateMazeFromList() {
        Maze maze = Maze.fromList(mazeEntities);
        super.setMaze(maze);
    }

    private void loadFile(String file) {
        Level level = LevelLoader.load(new FileHandle(new File(file)));
        Maze maze = level.getMaze();
        if (maze == null) {
            return;
        }

        this.mazeEntities = new ArrayList<Entity>();
        for (Tile t : maze.getTiles()) {
            if (t.getType() != TileType.VOID) {
                this.mazeEntities.add(t);
            }
        }
        for (Monster m : maze.getMonsters()) {
            this.mazeEntities.add(m);
        }
        for (WorldItem i : maze.getItems()) {
            this.mazeEntities.add(i);
        }

        this.player.setPosition(level.getPlayerData().getPosition());
        generateMazeFromList();
    }
}
