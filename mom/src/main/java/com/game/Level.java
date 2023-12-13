package com.game;

import com.engine.Window;
import com.engine.utils.Vector3;
import com.game.exceptions.InvalidItemException;
import com.game.exceptions.InvalidMonsterException;
import com.game.exceptions.InvalidSchemaException;
import com.game.exceptions.InvalidTileException;
import com.game.items.potions.HealthPotion;
import com.game.items.potions.SpeedPotion;
import com.game.items.potions.StrengthPotion;
import com.game.monsters.Ghost;
import com.game.monsters.Monster;
import com.game.monsters.Zombie;
import com.game.tiles.End;
import com.game.tiles.GroundGrass;
import com.game.tiles.GroundLava;
import com.game.tiles.GroundRock;
import com.game.tiles.GroundSpikes;
import com.game.tiles.GroundWater;
import com.game.tiles.Next;
import com.game.tiles.StairGrass;
import com.game.tiles.StairRock;
import com.game.tiles.Tile;
import com.game.tiles.VoidTile;
import com.game.tiles.WallRock;
import org.json.JSONArray;
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
        try {
            verifyJSON(json, "name");
            verifyJSON(json, "description");
            verifyJSON(json, "author");
            verifyJSON(json, "version");
            verifyJSON(json, "maze");
            verifyJSON(json, "player");

            this.name = json.getString("name");
            this.description = json.getString("description");
            this.author = json.getString("author");
            this.version = json.getString("version");
            this.maze = parseMaze(json.getJSONObject("maze"));
            this.playerData = new PlayerData(json.getJSONObject("player"));
        } catch (InvalidSchemaException e) {
            System.err.println("Error : Cannot load level : " + e.getMessage());
        }
    }

    /**
     * Construct a new Level object from all its attributes.
     * @param name The level's name.
     * @param description The level's description.
     * @param author The level's author.
     * @param version The level's version.
     * @param maze The level's maze.
     * @param player The level's player.
     */
    public Level(String name, String description, String author, String version, Maze maze, Player player) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.version = version;
        this.maze = maze;
        this.playerData = new PlayerData(player);
    }

    /**
     * Main method.
     * For testing purposes only.
     * @param args The arguments.
     */
    public static void main(String[] args) {
        Window win = new Window();
        win.setScene(new LevelTestScene("src/main/resources/maps/test.json"));
        win.run();
    }

    private Maze parseMaze(JSONObject mazeJsonObject) throws InvalidSchemaException {
        verifyJSON(mazeJsonObject, "width");
        verifyJSON(mazeJsonObject, "height");
        verifyJSON(mazeJsonObject, "depth");

        int width = mazeJsonObject.getInt("width");
        int height = mazeJsonObject.getInt("height");
        int depth = mazeJsonObject.getInt("depth");

        Tile[] tiles;
        Monster[] monsters;
        WorldItem[] items;

        try {
            tiles = parseTiles(mazeJsonObject.getJSONArray("tiles"));
            monsters = parseMonsters(mazeJsonObject.getJSONArray("monsters"));
            items = parseItems(mazeJsonObject.getJSONArray("items"));
        } catch (InvalidMonsterException | InvalidItemException | InvalidTileException e) {
            System.err.println("Error : Cannot load level : " + e.getMessage());
            return null;
        }

        return new Maze(
            width,
            height,
            depth,
            tiles,
            monsters,
            items,
            false);
    }

    /**
     * Parse a tile from a JSON object.
     * @param tileJsonObject The JSON object to parse the tile from.
     * @return The parsed tile.
     * @throws InvalidTileException If the tile is invalid.
     * @throws InvalidSchemaException If the JSON object is invalid.
     */
    public static Tile parseTile(JSONObject tileJsonObject) throws InvalidTileException, InvalidSchemaException {
        verifyJSON(tileJsonObject, "type");
        verifyJSON(tileJsonObject, "position");

        String type = tileJsonObject.getString("type");
        Vector3 position = parsePosition(tileJsonObject.getJSONObject("position"));
        switch (type) {
            case "STAIR_GRASS":
                return new StairGrass(position, tileJsonObject.getInt("direction"));
            case "STAIR_ROCK":
                return new StairRock(position, tileJsonObject.getInt("direction"));
            case "GROUND_ROCK":
                return new GroundRock(position);
            case "GROUND_LAVA":
                return new GroundLava(position);
            case "GROUND_WATER":
                return new GroundWater(position);
            case "GROUND_SPIKES":
                return new GroundSpikes(position);
            case "GroundGrass":
                return new GroundGrass(position);
            case "WALL_ROCK":
                return new WallRock(position);
            case "VOID":
                return new VoidTile(position);
            case "GROUND_NEXT":
                return new Next(position);
            case "GROUND_END":
                return new End(position);
            default:
                throw new InvalidTileException("Unknown tile type: " + type);
        }
    }

    /**
     * Verify that a JSON object has a key.
     * @param jsonObject The JSON object to verify.
     * @param key The key to verify.
     * @throws InvalidSchemaException If the JSON object does not have the key.
     */
    public static void verifyJSON(JSONObject jsonObject, String key) throws InvalidSchemaException {
        if (!jsonObject.has(key)) {
            throw new InvalidSchemaException("Missing key [" + key + "] in JSON object.");
        }
    }

    /**
     * Parse a position from a JSON object.
     * @param positionJsonObject The JSON object to parse the position from.
     * @return The parsed position.
     * @throws InvalidSchemaException If the JSON object is invalid.
     */
    public static Vector3 parsePosition(JSONObject positionJsonObject) throws InvalidSchemaException {
        verifyJSON(positionJsonObject, "x");
        verifyJSON(positionJsonObject, "y");
        verifyJSON(positionJsonObject, "z");

        return new Vector3(
            positionJsonObject.getFloat("x"),
            positionJsonObject.getFloat("y"),
            positionJsonObject.getFloat("z"));
    }

    /**
     * Parse a monster from a JSON object.
     * @param monsterJsonObject The JSON object to parse the monster from.
     * @return The parsed monster.
     * @throws InvalidMonsterException If the monster is invalid.
     * @throws InvalidSchemaException If the JSON object is invalid.
     */
    public static Monster parseMonster(JSONObject monsterJsonObject)
        throws InvalidMonsterException, InvalidSchemaException {
        verifyJSON(monsterJsonObject, "type");
        verifyJSON(monsterJsonObject, "position");

        String type = monsterJsonObject.getString("type");
        Vector3 position = parsePosition(monsterJsonObject.getJSONObject("position"));
        switch (type) {
            case "MONSTER_ZOMBIE":
                return new Zombie(position);
            case "MONSTER_GHOST":
                return new Ghost(position);
            // case "MONSTER_BOSS":
            //     return new Boss(position);
            default:
                throw new InvalidMonsterException("[" + type + "] monster does not exist (invalid monster type).");
        }
    }

    /**
     * Parse an item from a JSON object.
     * @param itemJsonObject The JSON object to parse the item from.
     * @return The parsed item.
     * @throws InvalidItemException If the item is invalid.
     * @throws InvalidSchemaException If the JSON object is invalid.
     */
    public static WorldItem parseItem(JSONObject itemJsonObject) throws InvalidItemException, InvalidSchemaException {
        verifyJSON(itemJsonObject, "type");
        verifyJSON(itemJsonObject, "position");

        String type = itemJsonObject.getString("type");
        Vector3 position = parsePosition(itemJsonObject.getJSONObject("position"));
        switch (type) {
            case "SWORD":
                return new com.game.items.weapons.Sword(position);
            case "ITEM_HEALTH_POTION":
                return new HealthPotion(position);
            case "ITEM_STRENGTH_POTION":
                return new StrengthPotion(position);
            case "ITEM_SPEED_POTION":
                return new SpeedPotion(position);
            default:
                throw new InvalidItemException("[" + type + "] item does not exist (invalid item type).");
        }
    }

    private Tile[] parseTiles(JSONArray tilesJsonArray)
        throws InvalidTileException, InvalidSchemaException {
        Tile[] tiles = new Tile[tilesJsonArray.length()];
        for (int i = 0; i < tilesJsonArray.length(); i++) {
            tiles[i] = parseTile(tilesJsonArray.getJSONObject(i));
            // TODO set position
        }
        return tiles;
    }

    private Monster[] parseMonsters(JSONArray monstersJsonArray)
        throws InvalidMonsterException, InvalidSchemaException {
        Monster[] monsters = new Monster[monstersJsonArray.length()];
        for (int i = 0; i < monstersJsonArray.length(); i++) {
            monsters[i] = parseMonster(monstersJsonArray.getJSONObject(i));
        }
        return monsters;
    }

    private WorldItem[] parseItems(JSONArray itemsJsonArray)
        throws InvalidItemException, InvalidSchemaException {
        WorldItem[] items = new WorldItem[itemsJsonArray.length()];
        for (int i = 0; i < itemsJsonArray.length(); i++) {
            items[i] = parseItem(itemsJsonArray.getJSONObject(i));
        }
        return items;
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
