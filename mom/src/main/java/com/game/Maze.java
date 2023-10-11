package com.game;

import com.engine.Evolvable;
import com.engine.utils.Vector3;
import com.game.monsters.Monster;
import com.game.tiles.Tile;
import com.game.tiles.TileType;
import com.renderer.GameScene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Maze class.
 * This is the maze class.
 */
public class Maze implements Evolvable {
    /** The width of the maze. */
    private final int width;
    /** The height of the maze. */
    private int height;
    /** The depth of the maze. */
    private int depth;
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
     * @param w The width of the maze (x axis).
     * @param h The height of the maze (y axis).
     * @param d The depth of the maze (z axis).
     */
    public Maze(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
        this.tiles = new Tile[w * h * d];
    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     * @param w The width of the maze (x axis).
     * @param h The height of the maze (y axis).
     * @param d The depth of the maze (z axis).
     * @param t The tiles of the maze.
     */
    public Maze(int w, int h, int d, Tile[] t) {
        if (t.length != w * h * d) {
            throw new IllegalArgumentException("The number of tiles must be equal to width * height * depth.");
        }
        this.width = w;
        this.height = h;
        this.depth = d;
        this.tiles = t;
        this.monsters = new Monster[0];
        this.items = new Item[0];
        this.setTilesDefaultPositions();
    }

    private void setTilesDefaultPositions() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                for (int z = 0; z < this.depth; z++) {
                    this.getTile(x, y, z).setPosition(new Vector3(x, y, z));
                }
            }
        }
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
        List<Entity> entities = new ArrayList<>();
        entities.addAll(List.of(this.monsters));
        entities.addAll(List.of(this.tiles));
        entities.addAll(List.of(this.items));
        if (Game.getInstance().getPlayer() != null) {
            entities.add(0, Game.getInstance().getPlayer());
        }

        entities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return GameScene.getObjectDrawingOrder(o1.getPosition())
                    - GameScene.getObjectDrawingOrder(o2.getPosition());
            }
        });

        for (Entity e: entities) {
            e.render();
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
     * Get the depth of the maze.
     * @return The depth of the maze.
     */
    public int getDepth() {
        return this.depth;
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
     * @param z The z coordinate.
     * @return The tile at the given coordinates.
     */
    public Tile getTile(int x, int y, int z) {
        if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth) {
            throw new IllegalArgumentException("The coordinates must be within the maze.");
        }
        return tiles[x + y * width + z * (width * height)];
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

    /**
     * ToString method. Displays the maze into grid-like String.
     * @return Maze turned into a String.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < this.tiles.length; i++) {
            if (i % this.width == 0) {
                s.append('\n');
            }
            if (this.tiles[i].getType() == TileType.WALL_ROCK) {
                s.append("#  ");
            }
            if (this.tiles[i].getType() == TileType.GROUND_ROCK) {
                s.append("<  ");
            }
            if (this.tiles[i].getType() == TileType.VOID) {
                s.append("   ");
            }
            if (this.tiles[i].getType() == TileType.GROUND_WATER) {
                s.append("w  ");
            }
        }

        return s.toString();
    }
}
