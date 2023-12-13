package com.game;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import org.json.JSONObject;

/**
 * Item class.
 * This is the base class for all items.
 */
public abstract class Item extends Entity {

    /** The type of the item. */
    private ItemType type;

    /**
     * Item constructor.
     * @param sprite The sprite to use.
     * @param t The type of the item.
     */
    protected Item(Sprite sprite, ItemType t) {
        super(sprite, new Vector3(), new Vector3(1, 1, 1));
        this.type = t;
    }

    /**
     * Item constructor.
     * @param sprite The sprite to use.
     * @param position The position of the item.
     * @param size The size of the item.
     * @param t The type of the item.
     */
    protected Item(Sprite sprite, Vector3 position, Vector3 size, ItemType t) {
        super(sprite, position, size);
        this.type = t;
    }

    /**
     * Getter for the itemType.
     * @return Item's type.
     */
    public ItemType getItemType() {
        return type;
    }

    /**
     * Returns a json representation of the item.
     * @return A JSONObject representing the item.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("type", this.type);
        json.put("position", getPosition().toJSON());

        return json;
    }

    /**
     * This method calls the interaction method of the Item.
     *
     * @param player player to use for the interaction.
     */
    public abstract void interact(Player player);
}
