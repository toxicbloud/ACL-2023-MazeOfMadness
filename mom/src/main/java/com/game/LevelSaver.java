package com.game;

import com.badlogic.gdx.files.FileHandle;
import com.engine.utils.Vector3;
import com.game.monsters.Monster;
import com.game.tiles.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * LevelLoader class.
 */
public final class LevelSaver {

    private LevelSaver() {
    }

    /**
     * Load a level from a file.
     *
     * @param file The path to the file.
     * @param level The level to save.
     * @return If the level was saved.
     */
    public static boolean save(FileHandle file, Level level) {
        JSONObject json = level2json(level);

        file.writeString(json.toString(), false);
        return true;
    }

    private static JSONObject level2json(Level level) {
        JSONObject json = new JSONObject();

        json.put("name", level.getName());
        json.put("description", level.getDescription());
        json.put("author", level.getAuthor());
        json.put("version", level.getVersion());
        json.put("maze", maze2json(level.getMaze(), level.getPlayerData()));
        json.put("player", level.getPlayerData().toJSON());

        return json;
    }

    private static JSONObject maze2json(Maze maze, PlayerData playerData) {
        JSONObject json = new JSONObject();

        int minX = 0;
        int minY = 0;
        int minZ = 0;
        for (Tile t : maze.getTiles()) {
            if (t.getPosition().getX() < minX) {
                minX = (int) t.getPosition().getX();
            }
            if (t.getPosition().getY() < minY) {
                minY = (int) t.getPosition().getY();
            }
            if (t.getPosition().getZ() < minZ) {
                minZ = (int) t.getPosition().getZ();
            }
        }
        Vector3 shift = new Vector3(-minX, -minY, -minZ);
        for (Tile t : maze.getTiles()) {
            t.setPosition(t.getPosition().add(shift));
        }
        for (Monster m : maze.getMonsters()) {
            m.setPosition(m.getPosition().add(shift));
        }
        for (Item i : maze.getItems()) {
            i.setPosition(i.getPosition().add(shift));
        }
        playerData.setPosition(playerData.getPosition().add(shift));

        json.put("width", maze.getWidth());
        json.put("height", maze.getHeight());
        json.put("depth", maze.getDepth());
        json.put("tiles", mazeTiles2json(maze.getTiles()));
        json.put("monsters", mazeMonsters2json(maze.getMonsters()));
        json.put("items", mazeitems2json(maze.getItems()));

        return json;
    }

    private static JSONArray mazeTiles2json(Tile[] tiles) {
        JSONArray json = new JSONArray();

        for (int i = 0; i < tiles.length; i++) {
            json.put(tiles[i].toJSON());
        }

        return json;
    }

    private static JSONArray mazeMonsters2json(Monster[] monsters) {
        JSONArray json = new JSONArray();

        for (int i = 0; i < monsters.length; i++) {
            json.put(monsters[i].toJSON());
        }

        return json;
    }

    private static JSONArray mazeitems2json(Item[] items) {
        JSONArray json = new JSONArray();

        for (int i = 0; i < items.length; i++) {
            json.put(items[i].toJSON());
        }

        return json;
    }
}
