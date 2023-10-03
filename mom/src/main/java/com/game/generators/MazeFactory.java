package com.game.generators;

import com.game.Maze;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;

import java.util.Random;

/**
 * Factory for mazes.
 */
public class MazeFactory {

    public static final Random RNG = new Random();
    public static final int min_room_size = 5;

    /**
     * Default constructor.
     */
    public MazeFactory() {}

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

        // We fill the array using WALL_ROCK
        for (int i = 0; i< height * width * depth; i++) {
            maze[i] = new GroundRock();
        }

        // We call the room-splitting method on the array
        this.generateRooms(maze, width, height);


        return new Maze(height, width, 1, maze);
    }

    /**
     * Recursive Method, calls itself to split the maze in rooms.
     * It uses a tree-like structure to generate the rooms.
     * @param maze Maze to split.
     */
    private void generateRooms(Tile[] maze, int width, int height) {




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
