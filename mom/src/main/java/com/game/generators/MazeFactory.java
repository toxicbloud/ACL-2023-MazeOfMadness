package com.game.generators;

import com.game.Maze;
import com.game.tiles.Tile;

import java.util.Random;
import java.util.Stack;

/**
 * Factory for mazes.
 */
public class MazeFactory {

    /**
     * Default constructor.
     */
    public MazeFactory() {}

    /**
     * This function generates an initialized Maze Object that has been generated randomly.
     * @param height Height of the maze
     * @param width Width of the maze
     * @return Maze object initialized with a random maze
     */
    public Maze createMaze(int height, int width) {
        System.out.println("[DEBUG] - Generating maze.");
        System.out.println("[DEBUG] - Maze size : " + height + " x " + width);

        Tile[] maze = new Tile[height * width];
        Random random = new Random();
        Stack<Tile> stack = new Stack<>();

        // Getting a starting tile :


        return new Maze(height, width, maze);
    }

    /**
     * This function generates an initialized Maze Object that has been generated randomly with predetermined height
     * and width values.
     * @return Maze object initialized with a random 20 by 20 maze
     */
    public Maze createMaze() {
        final int heightWidth = 20;
        return this.createMaze(heightWidth, heightWidth);
    }

}
