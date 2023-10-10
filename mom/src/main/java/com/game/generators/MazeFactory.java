package com.game.generators;

import com.game.Maze;
import com.game.generators.tree.Leaf;
import com.game.tiles.Tile;
import com.game.tiles.WallRock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Factory for mazes.
 */
public final class MazeFactory {

    /**
     * RNG : Attribut permettant de faire appel à la classe Random.
     */
    public static final Random RNG = new Random();
    /**
     * MIN_ROOM_SIZE : Attribut permettant de définir la taille minimale des salles des maps créées.
     */
    public static final int MIN_ROOM_SIZE = 5;
    /**
     * RNG_THRESHOLD : Constante pour faire plaisir au Checkstyle.
     */
    private static final float RNG_THRESHOLD = 0.25F;

    /**
     * Private default constructor.
     */
    private MazeFactory() { }

    /**
     * This function generates an initialized Maze Object that has been generated randomly with predetermined height
     * and width values.
     * @return Maze object initialized with a random 20 by 20 maze
     */
    public static Maze createMaze() {
        final int heightWidth = 20;
        return MazeFactory.createMaze(heightWidth, heightWidth, 2);
    }

    /**
     * This function generates an initialized Maze Object that has been generated randomly.
     * WARNING - This method is made to generate mazes that are 1 block high
     * @param height Height of the maze
     * @param width Width of the maze
     * @param depth Depth of the maze
     * @return Maze object initialized with a random maze
     */
    public static Maze createMaze(int height, int width, int depth) {
        // We call the room-splitting method on the array
        Tile[] maze = MazeFactory.generateRooms(width, height, depth);
        // Returning the maze.
        return new Maze(height, width, depth, maze);
    }

    /**
     * Recursive Method, calls itself to split the maze in rooms.
     * It uses a tree-like structure to generate the rooms.
     * @param width desired width of the maze.
     * @param height desired height of the maze.
     * @param depth desired depth of the maze.
     * @return Tableau de Tiles du labyrinthe.
     */
    private static Tile[] generateRooms(int width, int height, int depth) {
        ArrayList<Leaf> leafArray = new ArrayList<>();
        Leaf root = new Leaf(0, 0, width, height);
        leafArray.add(root);

        boolean didSplit = true;
        // We loop through the Leaf array, until we can split no more.
        while (didSplit) {
            didSplit = false;

            for (int i = 0; i < leafArray.size(); i++) {
                Leaf leaf = leafArray.get(i);
                if (leaf.getLeft() == null && leaf.getRight() == null) {

                    // If this Leaf is too big, or 75% chance...
                    if (leaf.getWidth() > MazeFactory.MIN_ROOM_SIZE
                        || leaf.getHeight() > MazeFactory.MIN_ROOM_SIZE
                        || MazeFactory.RNG.nextFloat() > MazeFactory.RNG_THRESHOLD) {

                        if (leaf.split()) { // split the Leaf!
                            // If we did split, push the child leafs to the Vector so we can loop
                            // into them next iteration.
                            leafArray.add(leaf.getLeft());
                            leafArray.add(leaf.getRight());
                            didSplit = true;
                        }
                    }
                }
            }
        }

        // We now have an array full of leaves. We can populate it with rooms and generate halls.
        for (Leaf l : leafArray) {
            l.createRooms();
        }

        Tile[] maze = new Tile[height * width * depth];
        for (int i = 0; i < height * width * depth; i++) {
            maze[i] = new WallRock();
        }
        for (Leaf l : leafArray) {
            l.exportToArray(maze, height, width, depth);
        }

        return maze;
    }
}
