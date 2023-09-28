package com.ui;

import com.badlogic.gdx.Gdx;
import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.engine.events.EventType;
import com.engine.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu scene.
 */
public class MenuScene extends Scene {
    /**
     * Button width.
     */
    public static final int BUTTON_WIDTH = 200;

    /**
     * Button height.
     */
    public static final int BUTTON_HEIGHT = 100;
    /**
     * Button position x.
     */
    public static final float BUTTON_POSITION_X = 0.5f;
    /**
     * Button position y.
     */
    public static final float BUTTON_POSITION_Y = 0.0f;
    /**
     * The menu elements to update and render.
     */
    private List<Element> elements;
    /**
     * The current hovered element.
     */
    private Element hoveredElement;

    /**
     * Default constructor.
     */
    public MenuScene() {
        super();
        this.elements = new ArrayList<>();
        addElement(new Button(new Vector2(BUTTON_POSITION_X, BUTTON_POSITION_Y),
                new Vector2(BUTTON_WIDTH, BUTTON_HEIGHT)));
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render() {
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
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'onEvent'");
        if (ev.getType() == EventType.MOUSE_PRESSED) {
            System.out.println("Mouse button pressed");
            // get current mouse position
            System.out.println("Mouse position: " + Gdx.input.getX() + ", " + Gdx.input.getY());

            elements.forEach(t -> {
                Button button = (Button) t;
                int x = Gdx.input.getX();
                int y = Gdx.input.getY();

                if (isInside(new Vector2(x, y), button)) {
                    System.out.println("Button clicked");
                }
            });
        }
        if (ev.getType() == EventType.MOUSE_MOVED) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
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

}
