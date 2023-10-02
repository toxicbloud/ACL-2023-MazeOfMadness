package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventType;
import com.engine.utils.Vector2;
import com.game.Game;
import com.game.Maze;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;
import com.game.tiles.VoidTile;
import com.game.tiles.WallRock;
import com.renderer.GameScene;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu scene.
 */
public class MenuScene extends Scene {
    /**
     * Button width.
     */
    public static final int BUTTON_WIDTH = 20;

    /**
     * Button height.
     */
    public static final int BUTTON_HEIGHT = 10;
    /**
     * Button position x.
     */
    public static final float BUTTON_POSITION_X = 0.5f;
    /**
     * Button position y.
     */
    public static final float BUTTON_POSITION_Y = 0.45f;
    /** TEST_MAZE_WIDTH. */
    private static final int TEST_MAZE_WIDTH = 5;
    /** TEST_MAZE_HEIGHT. */
    private static final int TEST_MAZE_HEIGHT = 5;
    /** TEST_MAZE_DEPTH. */
    private static final int TEST_MAZE_DEPTH = 2;
    /**
     * The menu elements to update and render.
     */
    private List<Element> elements;
    /**
     * The current hovered element.
     * used to check if the mouse is still hovering the same element
     */
    private Element hoveredElement;
    /**
     * The current pressed element.
     * used to check if the mouse is still pressed on the same element
     */
    private Element pressedElement;

    /**
     * The logo sprite.
     */
    private Sprite logo;
    /**
     * The button hover sound.
     * beep sound
     */
    private Sound buttonHover;
    /**
     * The button click sound.
     * punch sound
     */
    private Sound buttonClick;

    private Batch batch;

    /**
     * Default constructor.
     */
    public MenuScene() {
        super();
        this.elements = new ArrayList<>();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render() {
        // render background
        batch.begin();
        // draw logo center x
        batch.draw(logo, Window.getInstance().getWidth() / 2 - logo.getWidth() / 2,
                Window.getInstance().getHeight() - logo.getHeight() - 50);
        batch.end();
        elements.forEach(Element::render);
    }

    private boolean isInside(Vector2 point, Element element) {
        Vector2 size = new Vector2(element.getSize());
        Vector2 position = new Vector2(element.getPosition());
        position.x *= Window.getInstance().getWidth();
        position.x -= size.x / 2;
        position.y *= Window.getInstance().getHeight();
        return point.x >= position.x && point.x <= position.x + size.x
                && point.y >= position.y && point.y <= position.y + size.y;
    }

    @Override
    public void onEvent(Event ev) {
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        if (ev.getType() == EventType.MOUSE_PRESSED) {
            elements.forEach(t -> {
                Button button = (Button) t;
                if (isInside(new Vector2(x, y), button)) {
                    button.onPressed(true);
                    pressedElement = button;
                }
            });
        }
        if (ev.getType() == EventType.MOUSE_RELEASED) {
            if (pressedElement != null && isInside(new Vector2(x, y), pressedElement)) {
                pressedElement.onPressed(false);
                pressedElement = null;
            }
        }
        if (ev.getType() == EventType.MOUSE_MOVED) {
            if (hoveredElement != null && !(isInside(new Vector2(x, y), hoveredElement))) {
                hoveredElement.onHovered(false);
                hoveredElement = null;
            }
            elements.forEach(t -> {
                Button button = (Button) t;
                if (isInside(new Vector2(x, y), t)
                        && hoveredElement != button) {
                    hoveredElement = button;
                    button.onHovered(true);
                }
            });
        }

    }

    /**
     *
     * @param element
     */
    public void addElement(Element element) {
        elements.add(element);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        logo = new Sprite(new Texture(Gdx.files.internal("images/menus/logo.png")));
        buttonClick = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        buttonHover = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/beep.mp3"));
        Button start = new Button(new Vector2(BUTTON_POSITION_X, BUTTON_POSITION_Y),
                new Vector2(BUTTON_WIDTH, BUTTON_HEIGHT));
        start.create();
        start.addListener(new ButtonListener() {
            @Override
            public void onPressed() {
                buttonClick.play();
                Game.getInstance().setMaze(new Maze(TEST_MAZE_WIDTH, TEST_MAZE_HEIGHT,
                TEST_MAZE_DEPTH, new Tile[] {
                new WallRock(), new WallRock(), new WallRock(), new WallRock(), new
                WallRock(),
                new WallRock(), new GroundRock(), new GroundRock(), new GroundRock(), new
                WallRock(),
                new WallRock(), new GroundRock(), new GroundRock(), new GroundRock(), new
                WallRock(),
                new WallRock(), new GroundRock(), new GroundRock(), new GroundRock(), new
                WallRock(),
                new WallRock(), new WallRock(), new WallRock(), new WallRock(), new
                WallRock(),

                new WallRock(), new WallRock(), new WallRock(), new WallRock(), new
                WallRock(),
                new WallRock(), new GroundRock(), new GroundRock(), new GroundRock(), new
                WallRock(),
                new WallRock(), new VoidTile(), new VoidTile(), new VoidTile(), new
                WallRock(),
                new WallRock(), new VoidTile(), new VoidTile(), new VoidTile(), new
                WallRock(),
                new WallRock(), new VoidTile(), new VoidTile(), new VoidTile(), new
                WallRock()
                }));
                Window.getInstance().setScene(new GameScene());
            }

            @Override
            public void onReleased() {
                // TODO Auto-generated method stub
                System.out.println("Button released");
            }

            @Override
            public void onHovered() {
                // TODO Auto-generated method stub
                long id = buttonHover.play();
                buttonHover.setVolume(id, 1.0f);
            }
        });
        TextButton test = new TextButton(new Vector2(BUTTON_POSITION_X, BUTTON_POSITION_Y - 0.2f),
                new Vector2(BUTTON_WIDTH, BUTTON_HEIGHT), "Test");
        test.create();
        addElement(start);
        addElement(test);
        Thread thread = new Thread(() -> {
            Sound sound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/menu.mp3"));
            long id = sound.play();
            sound.setVolume(id, 0.1f);
        });

        // load sound asynchronously
        thread.start();
    }

}
