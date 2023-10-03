package com.game.generators;

import com.game.Maze;
import com.game.generators.tree.Leaf;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;

import java.util.ArrayList;
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
        ArrayList<Leaf> leaf_array = new ArrayList<>();
        Leaf root = new Leaf(0,0, width, height);
        leaf_array.add(root);

        boolean did_split = true;
        // We loop through the Leaf array, until we can split no more.
        while(did_split) {
            did_split = false;

            for(int i = 0; i < leaf_array.size(); i++) {
                Leaf leaf = leaf_array.get(i);
                if (leaf.getLeft() == null && leaf.getRight() == null) {

                    // If this Leaf is too big, or 75% chance...
                    if (leaf.getWidth() > MazeFactory.min_room_size ||
                        leaf.getHeight() > MazeFactory.min_room_size ||
                        MazeFactory.RNG.nextFloat() > 0.25) {

                        if (leaf.split()) { // split the Leaf!
                            // If we did split, push the child leafs to the Vector os we can loop into them next iteration.
                            leaf_array.add(leaf.getLeft());
                            leaf_array.add(leaf.getRight());
                            did_split = true;
                        }
                    }
                }
            }
        }

        // We now have an array full of leaves. We can populate it with rooms.
        for(Leaf l : leaf_array) {
            l.createRooms();
        }
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
