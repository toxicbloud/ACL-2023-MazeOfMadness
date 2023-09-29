package com.game.tiles;

import com.engine.Sprite;
import com.engine.Texture;
import com.game.Entity;

/**
 * Tile class.
 * This is the tile class.
 */
public abstract class Tile extends Entity {
    /** Tiles default sprite. */
    protected static final Texture TILE_TEXTURE = new Texture("images/tiles.png");

    /** The type of the tile. */
    private TileType type;

    /** If the tile is solid. */
    private boolean solid;

    /**
     * Tile constructor.
     * This is the default constructor for the tile class.
     * It sets the solid to false.
     * @param t The type of the tile.
     * @param s The sprite of the tile.
     */
    public Tile(TileType t, Sprite s) {
        super(s);
        this.type = t;
        this.solid = false;
    }

    /**
     * Tile constructor.
     * This is the constructor for the tile class.
     * @param t The type of the tile.
     * @param s The sprite of the tile.
     * @param solid If the tile is solid.
     */
    public Tile(TileType t, Sprite s, boolean solid) {
        super(s);
        this.type = t;
        this.solid = solid;
    }

    /**
     * Getter to get if the tile is solid.
     * @return If the tile is solid.
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * Get the type of the tile.
     * @return The type of the tile.
     */
    public TileType getType() {
        return type;
    }
}
