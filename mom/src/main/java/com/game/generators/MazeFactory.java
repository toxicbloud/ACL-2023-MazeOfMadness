package com.game.generators;

import com.game.Maze;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;
import com.game.tiles.WallRock;

import java.util.Random;
import java.util.Stack;

/**
 * Factory for mazes.
 */
public class MazeFactory {

    private final Random RNG;

    /**
     * Default constructor.
     */
    public MazeFactory() {
        this.RNG = new Random();
    }

    /**
     * This function generates an initialized Maze Object that has been generated randomly.
     * WARNING - This method is made to generate mazes that are 1 block high
     * @param height Height of the maze
     * @param width Width of the maze
     * @param depth Depth of the maze
     * @return Maze object initialized with a random maze
     */
    public Maze createMaze(int height, int width, int depth) {
        System.out.println("[DEBUG] - Generating maze.");
        System.out.println("[DEBUG] - Maze size : " + height + " x " + width + " x " + depth);

        Tile[] maze = new Tile[height * width * depth];

        // Getting a starting tile :
        int start = this.RNG.nextInt(height * width * depth);
        System.out.println("[DEBUG] - Starting Tile : " + start);

        for (int i = 0; i < height*width*depth; i++) {
            maze[i] = new WallRock();
        }

        return new Maze(height, width, 1, maze);
    }

    /**
     * This function generates an initialized Maze Object that has been generated randomly with predetermined height
     * and width values.
     * @return Maze object initialized with a random 20 by 20 maze
     */
    public Maze createMaze() {
        final int heightWidth = 20;
        return this.createMaze(heightWidth, heightWidth, 1);
    }

}
