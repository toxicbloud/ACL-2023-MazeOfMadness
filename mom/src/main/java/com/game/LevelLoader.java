package com.game;

import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * LevelLoader class.
 */
public final class LevelLoader {

    private LevelLoader() {
    }

    /**
     * Load a level from a file.
     *
     * @param level The path to the file.
     * @return The level.
     */
    public static Level load(FileHandle level) {
        return new Level(new JSONObject(level.readString()));
    }

    /**
     * Load a level from a file.
     *
     * @param level Pair of level name and level file.
     * @return The level.
     */
    public static Level load(Pair<String, FileHandle> level) {
        return new Level(new JSONObject(level.getSecond().readString()));
    }

    /**
     * Get the list of levels.
     *
     * @return Pairs of level name and level file.
     */
    public static ArrayList<Pair<String, FileHandle>> getLevels() {
        throw new UnsupportedOperationException("Not implemented yet");

        // TODO: implement this method , directory listing doesn't work on classpath
        // resources

        /*
         * // list json files in maps folder
         * FileHandle mapsFolder = Gdx.files.internal("maps/");
         * FileHandle[] files = mapsFolder.list();
         * ArrayList<Pair<String, FileHandle>> jsonFiles = new ArrayList<>();
         * for (FileHandle file : files) {
         * if (file.extension().equals("json") || file.isDirectory()) {
         * jsonFiles.add(new Pair<>(getLevelName(file), file));
         * }
         * }
         * return jsonFiles;
         *
         */
    }

    /**
     * Get the name of a level from its file.
     *
     * @param file The file.
     * @return The name of the level.
     */
    public static String getLevelName(FileHandle file) {
        JSONObject json = new JSONObject(file.readString());
        return json.getString("name");
    }
}
