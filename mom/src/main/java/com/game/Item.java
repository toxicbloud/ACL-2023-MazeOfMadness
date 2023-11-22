package com.game;

import com.engine.Sprite;
import com.engine.utils.Vector3;

/**
 * Item class.
 * This is the base class for all items.
 */
public abstract class Item extends Entity {

    /** Type of the item. */
    private final ItemType itemType;

    /**
     * Item constructor.
     * @param sprite The sprite to use.
     * @param t Item's type.
     */
    protected Item(Sprite sprite, ItemType t) {
        super(sprite, new Vector3(), new Vector3(1, 1, 1));
        this.itemType = t;
    }

    /**
     * Item constructor.
     * @param sprite The sprite to use.
     * @param position The position of the item.
     * @param size The size of the item.
     * @param t Item's type.
     */
    protected Item(Sprite sprite, Vector3 position, Vector3 size, ItemType t) {
        super(sprite, position, size);
        this.itemType = t;
    }

    /**
     * Getter for the itemType.
     * @return Item's type.
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * This method calls the interaction method of the Item.
     *
     * @param player player to use for the interaction.
     */
    public abstract void interact(Player player);
}
