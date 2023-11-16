package com.editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.engine.Evolvable;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventMouseMoved;
import com.engine.events.EventType;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Entity;
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
    private Vector3 cursorPos;

    /**
     * EditorScene constructor.
     */
    public EditorScene() {
        super();
    }

    @Override
    public void create() {
        super.create();
        Game.getInstance().setMaze(new Maze(2, 1, 1, new Tile[]{new GroundRock(), new WallRock()}));
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        super.render();
        if (cursorPos != null) {
            new Sprite(
                new Texture("images/debug.png"),
                16,
                16,
                0
            ).render(cursorPos, new Vector3(1, 1, 1));
        }
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
                    cursorPos = getBlocAtCursor(new Vector2(event.getX(), event.getY()), 0);
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
            Math.round(x),
            Math.round(y),
            Math.round(z)
        );
    }
}
