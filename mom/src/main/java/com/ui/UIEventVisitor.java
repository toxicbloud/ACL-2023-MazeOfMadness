package com.ui;

import com.badlogic.gdx.Gdx;
import com.engine.events.EventGamepadMoved;
import com.engine.events.EventGamepadPressed;
import com.engine.events.EventGamepadReleased;
import com.engine.events.EventKeyPressed;
import com.engine.events.EventKeyReleased;
import com.engine.events.EventMouseMoved;
import com.engine.events.EventMousePressed;
import com.engine.events.EventMouseReleased;
import com.engine.events.EventMouseScrolled;
import com.engine.events.EventVisitor;
import com.engine.utils.Vector2;

/**
 * UI event visitor to dispatch events to the UI elements.
 */
public class UIEventVisitor implements EventVisitor {
    /**
     * The scene that contains the UI elements to dispatch the events to.
     */
    private MenuScene menuScene;

    /**
     * UIEventVisitor constructor.
     *
     * @param menuScene The scene that contains the UI elements to dispatch the
     *                  events to.
     */
    public UIEventVisitor(MenuScene menuScene) {
        this.menuScene = menuScene;
    }

    /**
     * Visits a mouse pressed event and dispatches it to the UI elements.
     *
     * @param eventMousePressed the mouse pressed event to visit
     */
    public void visit(EventMousePressed eventMousePressed) {
        final int x = Gdx.input.getX();
        final int y = Gdx.input.getY();
        menuScene.getElements().forEach(t -> {
            Button button = (Button) t;
            if (menuScene.isInside(new Vector2(x, y), button)) {
                button.onPressed(true);
                menuScene.setPressedElement(button);
            }
        });
    }

    /**
     * Visits a mouse released event and dispatches it to the UI elements.
     *
     * @param ev the mouse released event to visit
     */
    public void visit(EventMouseReleased ev) {
        Element pressedElement = menuScene.getPressedElement();
        final int x = Gdx.input.getX();
        final int y = Gdx.input.getY();
        if (pressedElement != null && menuScene.isInside(new Vector2(x, y), pressedElement)) {
            pressedElement.onPressed(false);
            menuScene.setPressedElement(null);
        }
    }

    /**
     * Visits a mouse moved event and dispatches it to the UI elements.
     *
     * @param ev the mouse moved event to visit
     */
    public void visit(EventMouseMoved ev) {
        final int x = Gdx.input.getX();
        final int y = Gdx.input.getY();
        final Element hoveredElement = menuScene.getHoveredElement();
        if (hoveredElement != null && !(menuScene.isInside(new Vector2(x, y), hoveredElement))) {
            hoveredElement.onHovered(false);
            menuScene.setHoveredElement(null);
        }
        menuScene.getElements().forEach(t -> {
            Button button = (Button) t;
            if (menuScene.isInside(new Vector2(x, y), t)
                    && hoveredElement != button) {
                menuScene.setHoveredElement(button);
                button.onHovered(true);
            }
        });
    }

    /**
     * Visits a Gamepad moved event and dispatches it to the UI elements.
     *
     * @param eventGamepadMoved the Gamepad moved event to visit
     */
    public void visit(EventGamepadMoved eventGamepadMoved) {
    }

    /**
     * Visits a gamepad pressed event and dispatches it to the UI elements.
     *
     * @param eventGamepadPressed the gamepad pressed event to visit
     */
    public void visit(EventGamepadPressed eventGamepadPressed) {
    }

    /**
     * Visits a gamepad released event and dispatches it to the UI elements.
     *
     * @param eventGamepadReleased the gamepad released event to visit
     */
    public void visit(EventGamepadReleased eventGamepadReleased) {
    }

    /**
     * Visits a key released event and dispatches it to the UI elements.
     *
     * @param eventKeyReleased the key released event to visit
     */
    public void visit(EventKeyReleased eventKeyReleased) {
    }

    /**
     * Visits a MouseScrolled event and dispatches it to the UI elements.
     *
     * @param eventMouseScrolled the MouseScrolled event to visit
     */
    public void visit(EventMouseScrolled eventMouseScrolled) {
    }

    /**
     * Visits a key pressed event and dispatches it to the UI elements.
     *
     * @param eventKeyPressed the key pressed event to visit
     */
    public void visit(EventKeyPressed eventKeyPressed) {

    }
}
