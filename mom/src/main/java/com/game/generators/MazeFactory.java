package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Maze;
import com.game.generators.tree.Leaf;
import com.game.tiles.Tile;

import java.security.SecureRandom;

/**
 * Factory for mazes.
 */
public final class MazeFactory {
    /** MAX_LEAF_SIZE : Constant that allows us to define maximum Leaf size. */
    public static final int MAX_LEAF_SIZE = 5;

    /** MIN_SIZE : Constant for the minimum size of randomly generated mazes. */
    public static final int MIN_SIZE = 25;

    /** MAX_SIZE : Constant for the maximum size of randomly generated mazes. */
    public static final int MAX_SIZE = 45;

    /**
     * Private default constructor.
     */
    private MazeFactory() {
    }

    /**
     * This function generates an initialized Maze Object that has been generated
     * randomly with predetermined height
     * and width values. These are randomly generated inside the function.
     *
     * @return Maze object initialized with a random maze.
     */
    public static Maze createMaze() {
        // We pick randomly dimensions between MIN_SIZE and MAX_SIZE values.
        int width = MazeFactory.randomInt(MIN_SIZE, MAX_SIZE);
        int height = MazeFactory.randomInt(MIN_SIZE, MAX_SIZE);
        return MazeFactory.createMaze(width, height, 2);
    }

    /**
     * This function generates an initialized Maze Object that has been generated
     * randomly.
     * WARNING - This method is made to generate mazes that are 1 block high.
     *
     * @param width  Width of the maze
     * @param height Height of the maze
     * @param depth  Depth of the maze
     * @return Maze object initialized with a random maze
     */
    public static Maze createMaze(int width, int height, int depth) {
        if (depth <= 0) {
            throw new IllegalArgumentException("[ERROR] - depth parameter must be greater than 0");
        }
        // We prepare the variable for the spawnpoint computed during maze generation.
        Vector3 spawnpoint = new Vector3(0, 0, 0);

        // We call the room generation to get the maze array
        Tile[] maze = Leaf.generateMazeArray(width, height, depth, spawnpoint);

        // Returning the maze.
        Maze m = new Maze(width, height, depth, maze);
        m.setSpawnPoint(spawnpoint);
        return m;
    }

    /**
     * Superset of the RNG method that allows to provide a random int between 2
     * numbers.
     * It's only purpose is to facilitate the RNG method call.
     *
     * @param min Lower bound.
     * @param max Upper bound.
     * @return Random int between min and max parameters.
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("[ERROR] - max parameter must be greater than min parameter");
        }
        SecureRandom sr = new SecureRandom();
        return sr.nextInt((max - min) + 1) + min;
    }
}
