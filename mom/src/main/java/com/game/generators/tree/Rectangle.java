package com.game.generators.tree;

import com.engine.utils.Vector3;
import com.game.generators.spawners.TrapSpawner;
import com.game.tiles.GroundRock;
import com.game.tiles.Tile;
import com.game.tiles.VoidTile;

/**
 * Rectangle class. Used to define rooms.
 */
public class Rectangle {
    /** X coordinate. */
    private final int x;
    /** Y coordinate. */
    private final int y;
    /** width value. */
    private final int width;
    /** height value. */
    private final int height;
    /** top value. */
    private final int top;
    /** bottom value. */
    private final int bottom;
    /** left value. */
    private final int left;
    /** right value. */
    private final int right;
    /** If the rectangle represents a room. */
    private final boolean isRoom;

    /**
     * Constructor. Used to create a rectangle.
     *
     * @param x      x coord.
     * @param y      y coord.
     * @param width  width coord.
     * @param height height coord.
     * @param isRoom If the rectangle represents a room.
     */
    public Rectangle(int x, int y, int width, int height, boolean isRoom) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bottom = y + height;
        this.top = y;
        this.left = x;
        this.right = x + width;
        this.isRoom = isRoom;
    }

    /**
     * Returns top value.
     *
     * @return top value.
     */
    public int getTop() {
        return top;
    }

    /**
     * Returns bottom value.
     *
     * @return bottom value.
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * Returns left value.
     *
     * @return left value.
     */
    public int getLeft() {
        return left;
    }

    /**
     * Returns right value.
     *
     * @return right value.
     */
    public int getRight() {
        return right;
    }

    /**
     * Returns X value.
     *
     * @return X value.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns Y value.
     *
     * @return Y value.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns width value.
     *
     * @return width value.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns height value.
     *
     * @return height value.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Fills the maze array with ground Tiles where the room is.
     *
     * @param maze       Maze to fill.
     * @param mazeWidth  Width of the maze to fill.
     * @param mazeHeight Height of the maze to fill.
     * @param mazeDepth  Height of the maze to fill.
     * @param spawnpoint Spawnpoint coordinates to avoid filling it with traps.
     */
    public void populateMazeWithRect(Tile[] maze, int mazeWidth, int mazeHeight, int mazeDepth, Vector3 spawnpoint) {
        // Computing starting tile to fill.
        int startingTileIndex = this.x + this.y * mazeWidth;

        // We fill each Tile that is inside the current rectangle with a GroundRock.
        for (int i = startingTileIndex;
             i < startingTileIndex + (mazeWidth * this.height);
             i += mazeWidth) {

            for (int j = 0; j < this.width; j++) {
                // We fill the ground layer
                maze[i + j] = new GroundRock();

                // We populate the room with traps :
                if (this.isRoom) {
                    TrapSpawner.spawnTrap(maze, i % mazeWidth, this.y + j, i + j, spawnpoint);
                }

                // We unfill the above layers.
                for (int k = 1; k < mazeDepth; k++) {
                    maze[i + j + (mazeHeight * mazeWidth * k)] = new VoidTile();
                }
            }
        }
    }
}
