package com.game;

import com.game.tiles.GroundGrass;
import com.game.tiles.GroundLava;
import com.game.tiles.GroundRock;
import com.game.tiles.GroundWater;
import com.game.tiles.StairGrass;
import com.game.tiles.StairRock;
import com.game.tiles.Tile;
import com.game.tiles.VoidTile;
import com.game.tiles.WallRock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Level class.
 */
public class Level {
    /**
     * The name of the level.
     */
    private String name;
    /**
     * The description of the level.
     */
    private String description;
    /**
     * The author of the level.
     */
    private String author;
    /**
     * The version of the level.
     */
    private String version;
    /**
     * The maze of the level.
     */
    private Maze maze;
    /**
     * The player data of the level.
     */
    private PlayerData playerData;

    /**
     * Construct a new Level object from a JSON object.
     *
     * @param json The JSON object to construct the Level object from.
     */
    public Level(JSONObject json) {
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.author = json.getString("author");
        this.version = json.getString("version");
        this.maze = parseMaze(json.getJSONObject("maze"));
        this.playerData = new PlayerData(json.getJSONObject("player"));
    }

    private Maze parseMaze(JSONObject mazeJsonObject) {
        int width = mazeJsonObject.getInt("width");
        int height = mazeJsonObject.getInt("height");
        int depth = mazeJsonObject.getInt("depth");
        return new Maze(width, height, depth, parseTiles(mazeJsonObject.getJSONArray("tiles")));
    }

    private Tile parseTile(JSONObject tileJsonObject) throws IllegalArgumentException, JSONException {
        String type = tileJsonObject.getString("type");
        switch (type) {
            case "StairGrass":
                return new StairGrass(tileJsonObject.getInt("direction"));
            case "StairRock":
                return new StairRock(tileJsonObject.getInt("direction"));
            case "GroundRock":
                return new GroundRock();
            case "GroundLava":
                return new GroundLava();
            case "GroundWater":
                return new GroundWater();
            case "GroundGrass":
                return new GroundGrass();
            case "WallRock":
                return new WallRock();
            case "VoidTile":
                return new VoidTile();
            default:
                throw new IllegalArgumentException("Unknown tile type: " + type);
        }
    }

    private Tile[] parseTiles(JSONArray tilesJsonArray) {
        Tile[] tiles = new Tile[tilesJsonArray.length()];
        for (int i = 0; i < tilesJsonArray.length(); i++) {
            tiles[i] = parseTile(tilesJsonArray.getJSONObject(i));
            // TODO set position
        }
        return tiles;
    }

    /**
     * Get the name of the level.
     *
     * @return The name of the level.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the level.
     *
     * @param name The name of the level.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the level.
     *
     * @return The description of the level.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the level.
     *
     * @param description The description of the level.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the author of the level.
     *
     * @return The author of the level.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the level.
     *
     * @param author The author of the level.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the version of the level.
     *
     * @return The version of the level.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version of the level.
     *
     * @param version The version of the level.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get the maze of the level.
     *
     * @return The maze of the level.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Set the maze of the level.
     *
     * @param maze The maze of the level.
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Get the player data of the level.
     *
     * @return The player data of the level.
     */
    public PlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Set the player data of the level.
     *
     * @param playerData The player data of the level.
     */
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
}
