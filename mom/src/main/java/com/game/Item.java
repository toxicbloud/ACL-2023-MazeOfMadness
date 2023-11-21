package com.game;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import org.json.JSONObject;

/**
 * Item class.
 * This is the base class for all items.
 */
public abstract class Item extends Entity {
    /**
     * Item constructor.
     * @param sprite The sprite to use.
     */
    protected Item(Sprite sprite) {
        super(sprite, new Vector3(), new Vector3(1, 1, 1));
    }

    /**
     * Item constructor.
     * @param sprite The sprite to use.
     * @param position The position of the item.
     * @param size The size of the item.
     */
    protected Item(Sprite sprite, Vector3 position, Vector3 size) {
        super(sprite, position, size);
    }

    /**
     * Returns a json representation of the item.
     * @return A JSONObject representing the item.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        // json.put("type", this.type);
        json.put("position", getPosition().toJSON());

        return json;
    }
}
