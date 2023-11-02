package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Maze;
import com.game.generators.tree.Leaf;
import com.game.generators.tree.Rectangle;
import com.game.tiles.Tile;
import com.game.tiles.WallRock;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Factory for mazes.
 */
public final class MazeFactory {
    /**
     * MIN_ROOM_SIZE : Attribut permettant de définir la taille minimale des salles des maps créées.
     */
    public static final int BASE_ROOM_SIZE = 5;

    /**
     * MIN_SIZE : Constante pour la taille minimale d'un labyrinthe divisée par 5.
     */
    public static final int MIN_SIZE = 5;

    /**
     * MAX_SIZE : Constante pour la taille maximale d'un labyrinthe divisée par 5.
     */
    public static final int MAX_SIZE = 9;

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
     * and width values. These are randomly generated inside the function.
     * @return Maze object initialized with a random maze.
     */
    public static Maze createMaze() {
        final int width = MazeFactory.randomInt(MIN_SIZE, MAX_SIZE) * 5;
        final int height = MazeFactory.randomInt(MIN_SIZE, MAX_SIZE) * 5;
        System.out.println("[INFO] - height * width => " + height + " x " + width);
        return MazeFactory.createMaze(width, height, 2);
    }

    /**
     * This function generates an initialized Maze Object that has been generated randomly.
     * WARNING - This method is made to generate mazes that are 1 block high
     * @param height Height of the maze
     * @param width Width of the maze
     * @param depth Depth of the maze
     * @return Maze object initialized with a random maze
     */
    public static Maze createMaze(int width, int height, int depth) {
        // We call the room-splitting method on the array
        Vector3 spawnpoint = new Vector3(0, 0, 0);
        Tile[] maze = MazeFactory.generateRooms(width, height, depth, spawnpoint);
        // Returning the maze.
        Maze m = new Maze(height, width, depth, maze);
        m.setSpawnPoint(spawnpoint);
        return m;
    }

    /**
     * Recursive Method, calls itself to split the maze in rooms.
     * It uses a tree-like structure to generate the rooms.
     * @param width desired width of the maze.
     * @param height desired height of the maze.
     * @param depth desired depth of the maze.
     * @param spawnpoint variable to store the spawnpoint for the maze.
     * @return Tableau de Tiles du labyrinthe.
     */
    private static Tile[] generateRooms(int width, int height, int depth, Vector3 spawnpoint) {
        ArrayList<Leaf> leafArray = new ArrayList<>();
        SecureRandom sr = new SecureRandom();
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
                    if (leaf.getWidth() > MazeFactory.BASE_ROOM_SIZE
                        && leaf.getHeight() > MazeFactory.BASE_ROOM_SIZE
                        || sr.nextFloat() > MazeFactory.RNG_THRESHOLD) {

                        if (leaf.split()) { // split the Leaf!
                            // If we did split, push the child leafs to the ArrayList, so we can loop
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
        boolean spawnpointSet = false;
        for (Leaf l : leafArray) {
            l.createRooms();

            // If the spawnpoint is not set, we find one to set.
            if (!spawnpointSet) {
                // Careful ! It returns a random room in the maze. not the first one that is encountered.
                Rectangle room = l.getRoom();
                spawnpoint.x = room.getY();
                spawnpoint.y = room.getX();
                spawnpoint.z = 1;
                System.out.println("[INFO] - Spawnpoint found ! (x,y) = (" + spawnpoint.x + "," + spawnpoint.y + ")");
                spawnpointSet = true;
            }
        }

        // We first fill the maze with walls.
        Tile[] maze = new Tile[height * width * depth];
        for (int i = 0; i < height * width * depth; i++) {
            maze[i] = new WallRock();
        }
        // Then, we carve the rooms inside the given maze.
        for (Leaf l : leafArray) {
            l.exportToArray(maze, width, height, depth);
        }

        return maze;
    }

    /**
     * Superset of the RNG method that allows to provide a random int between 2 numbers.
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
