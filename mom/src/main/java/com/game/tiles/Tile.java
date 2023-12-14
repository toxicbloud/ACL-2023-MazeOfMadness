package com.game.tiles;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Game;
import com.game.Player;
import org.json.JSONObject;

/**
 * Tile class. This is the tile class.
 */
public abstract class Tile extends Entity {

    /** Tiles default sprite. */
    protected static final Texture TILE_TEXTURE = new Texture("images/tiles.png");
    /**
     * How many neighbours a tile can have.
     */
    private static final int NEIGHBOURS_COUNTS = 4;
    /** The default frame time for Tiles animations (15 fps). */
    private static final float TILE_SPRITE_FRAME_TIME = 1 / 15.0f;

    /** The type of the tile. */
    private TileType type;

    /** If the tile is solid. */
    private boolean solid;

    /**
     * Tile constructor. This is the default constructor for the tile class. It sets the solid to
     * false. And the position to (0, 0, 0).
     *
     * @param t The type of the tile.
     * @param s The sprite of the tile.
     */
    public Tile(TileType t, Sprite s) {
        super(s, new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        this.type = t;
        this.solid = false;
    }

    /**
     * Tile constructor. This is the constructor for the tile class. And the position to (0, 0, 0).
     *
     * @param t The type of the tile.
     * @param s The sprite of the tile.
     * @param solid If the tile is solid.
     */
    public Tile(TileType t, Sprite s, boolean solid) {
        super(s, new Vector3(0, 0, 0), new Vector3(1, 1, 1));
        this.type = t;
        this.solid = solid;
    }

    /**
     * Tile constructor. This is the constructor for the tile class. It sets the solid to false.
     *
     * @param t The type of the tile.
     * @param s The sprite of the tile.
     * @param p The position of the tile.
     */
    public Tile(TileType t, Sprite s, Vector3 p) {
        super(s, p, new Vector3(1, 1, 1));
        this.type = t;
        this.solid = false;
    }

    /**
     * Tile constructor. This is the constructor for the tile class.
     *
     * @param t The type of the tile.
     * @param s The sprite of the tile.
     * @param p The position of the tile.
     * @param solid If the tile is solid.
     */
    public Tile(TileType t, Sprite s, Vector3 p, boolean solid) {
        super(s, p, new Vector3(1, 1, 1));
        s.setFrameTime(TILE_SPRITE_FRAME_TIME);
        this.type = t;
        this.solid = solid;
    }

    /**
     * Getter to get if the tile is solid.
     *
     * @return If the tile is solid.
     */
    public boolean isSolid() {
        return solid;
    }

    /**
     * Get the type of the tile.
     *
     * @return The type of the tile.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Call when the player enter the tile.
     *
     * @param p The player that enter the tile.
     */
    public void onPlayerEnter(Player p) {

    }

    /**
     * Call when the player exit the tile.
     *
     * @param p The player that exit the tile.
     */
    public void onPlayerExit(Player p) {

    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("type", type.toString());
        json.put("position", getPosition().toJSON());

        return json;
    }

    /**
     * Returns the neighbours of the tile.
     *
     * @return The neighbours of the tile.
     */
    public Tile[] getNeighbours() {
        final Tile[] neighbours = new Tile[NEIGHBOURS_COUNTS];
        int x = (int) getPosition().x;
        int y = (int) getPosition().y;
        int z = (int) getPosition().z;

        // verify that neighbours are in the maze
        if (x > 0) {
            neighbours[0] = Game.getInstance().getMaze().getTile(x - 1, y, z);
        }
        if (x < Game.getInstance().getMaze().getWidth() - 1) {
            neighbours[1] = Game.getInstance().getMaze().getTile(x + 1, y, z);
        }
        if (y > 0) {
            neighbours[2] = Game.getInstance().getMaze().getTile(x, y - 1, z);
        }
        if (y < Game.getInstance().getMaze().getHeight() - 1) {
            final int i = 3;
            neighbours[i] = Game.getInstance().getMaze().getTile(x, y + 1, z);
        }

        return neighbours;
    }

    @Override
    protected void remove() {
        // CANNOT REMOVE TILES FROM MAZE
    }
}
