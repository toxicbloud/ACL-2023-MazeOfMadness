package com.game;

import com.engine.Evolvable;
import com.game.monsters.Monster;
import com.game.tiles.Tile;

/**
 * Maze class.
 * This is the maze class.
 */
public class Maze implements Evolvable {
    /** The width of the maze. */
    private int width;
    /** The height of the maze. */
    private int height;
    /** The tiles of the maze. */
    private Tile[] tiles;
    /** The monsters of the maze. */
    private Monster[] monsters;
    /** The items of the maze. */
    private Item[] items;

    /**
     * Maze constructor.
     * This is the default constructor for the maze class.
     * It sets the width and height to 1.
     */
    public Maze() {
        this.width = 1;
        this.height = 1;
    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     * @param w The width of the maze.
     * @param h The height of the maze.
     */
    public Maze(int w, int h) {
        this.width = w;
        this.height = h;
        this.tiles = new Tile[w * h];
    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     * @param w The width of the maze.
     * @param h The height of the maze.
     * @param t The tiles of the maze.
     */
    public Maze(int w, int h, Tile[] t) {
        if (t.length != w * h) {
            throw new IllegalArgumentException("The number of tiles must be equal to the width times the height.");
        }
        this.width = w;
        this.height = h;
        this.tiles = t;
    }

    @Override
    public void update() {
        for (Monster m: this.monsters) {
            m.update();
        }
        for (Tile t: this.tiles) {
            t.update();
        }
        for (Item i: this.items) {
            i.update();
        }
    }

    @Override
    public void render() {
        for (Monster m: this.monsters) {
            m.render();
        }
        for (Tile t: this.tiles) {
            t.render();
        }
        for (Item i: this.items) {
            i.render();
        }
    }

    /**
     * Get the width of the maze.
     * @return The width of the maze.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the height of the maze.
     * @return The height of the maze.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Get the tiles of the maze.
     * @return The tiles of the maze.
     */
    public Tile[] getTiles() {
        return tiles;
    }

    /**
     * Get the tile at the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The tile at the given coordinates.
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("The coordinates must be within the maze.");
        }
        return tiles[x + y * width];
    }

    /**
     * Get the monsters of the maze.
     * @return The monsters of the maze.
     */
    public Monster[] getMonsters() {
        return monsters;
    }

    /**
     * Set the monsters of the maze.
     * @param monsters The monsters of the maze.
     */
    public void setMonsters(Monster[] monsters) {
        this.monsters = monsters;
    }

    /**
     * Get the items of the maze.
     * @return The items of the maze.
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * Set the items of the maze.
     * @param items The items of the maze.
     */
    public void setItems(Item[] items) {
        this.items = items;
    }
}
