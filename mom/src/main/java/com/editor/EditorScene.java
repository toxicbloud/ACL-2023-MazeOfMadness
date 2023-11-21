package com.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.engine.events.EventType;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Maze;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;
import com.game.tiles.WallRock;
import com.renderer.GameScene;

/**
 * Editor scene.
 * This is the scene used to edit a game level.
 */
public class EditorScene extends GameScene {
    /** Height of the editor tab. */
    private static final int EDITOR_TAB_HEIGHT = 200;
    /** Padding between editor buttons. */
    private static final int EDITOR_BUTTON_PADDING = 4;
    /** Color of the editor tab background. */
    private static final Color TAB_BACKGROUND_COLOR = new Color(0.2f, 0.22f, 0.25f, 1.0f);

    /** Mouse pan sensivity in game scene. */
    private static final float PAN_SENSIVITY = 0.05f;

    /** Whether the mouse is in the game scene zone. */
    private boolean inGameSceneZone;
    /** Is the mouse pressed or not. */
    private boolean mousePressed;
    /** Last action's mouse position on screen. */
    private Vector2 lastMousePosition;
    /** Current mmouse position on screen. */
    private Vector2 mousePosition;

    /** Placeholder block. For 3D cursor visualization. */
    private PlaceholderBlock placeholderBlock;

    /** Stage for Editor top interface. */
    private Stage stage;

    /**
     * EditorScene constructor.
     */
    public EditorScene() {
        super();
        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void create() {
        super.create();
        this.placeholderBlock = new PlaceholderBlock();
        Game.getInstance().setMaze(new Maze(2, 1, 1, new Tile[]{new GroundRock(), new WallRock()}));

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
        if (this.inGameSceneZone) {
            Game.getInstance().getMaze().addTemporaryEntity(this.placeholderBlock);
        }
        super.render();
        renderEditorTab();
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
        } else {
            this.handleEditorEvent(ev);
        }
    }

    @Override
    public int getHeight() {
        return super.getHeight() - EDITOR_TAB_HEIGHT;
    }

    private void buildEditorTab() {
        Table root = new Table();
        root.setFillParent(true);
        root.setHeight(EDITOR_TAB_HEIGHT);
        stage.addActor(root);

        Skin skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        TextButton loadBtn = new TextButton("Load", skin);
        TextButton saveBtn = new TextButton("Save", skin);
        TextButton backBtn = new TextButton("Back", skin);
        TextButton upBtn = new TextButton("/\\", skin);
        TextButton downBtn = new TextButton("\\/", skin);

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

        mainTable.add(backButtons);
        mainTable.add(selectorTable).growX();
        mainTable.add(loadSaveButtons);
        root.add(mainTable).expand().growX().top();

        loadBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("loadBtn click");
            }
        });
        saveBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("saveBtn click");
            }
        });
        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("backBtn click");
            }
        });
        upBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("upBtn click");
            }
        });
        downBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("downBtn click");
            }
        });
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
            EDITOR_TAB_HEIGHT
        );

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
                break;
            case MOUSE_RELEASED:
                this.mousePressed = false;
                break;
            case MOUSE_MOVED:
                if (mousePressed) {
                    Vector2 delta = new Vector2(
                        (lastMousePosition.x - mousePosition.x) * getHeight(),
                        (lastMousePosition.y - mousePosition.y) * getWidth()
                    );

                    this.getCamera().setPosition(
                        this.getCamera().getPosition()
                        .add(new Vector3(
                            (delta.x / 2 + delta.y / 2) / getCamera().getZoom() * PAN_SENSIVITY,
                            (delta.y / 2 - delta.x / 2) / getCamera().getZoom() * PAN_SENSIVITY,
                            0
                        ))
                    );
                    lastMousePosition = mousePosition;
                } else {
                    EventMouseMoved event = (EventMouseMoved) ev;
                    Vector3 cursorPos = getBlocAtCursor(new Vector2(event.getX(), event.getY()), 0);
                    this.placeholderBlock.setPosition(cursorPos);
                }
                break;
            default:
                break;
        }
    }

    private void handleEditorEvent(Event ev) {

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
            Math.round(z + getCamera().getPosition().z)
        );
    }
}
