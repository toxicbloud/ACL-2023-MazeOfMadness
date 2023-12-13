package com.game;

import com.engine.Evolvable;
import com.engine.utils.Vector3;
import com.game.controllers.Controller;
import com.game.monsters.Monster;
import com.game.particles.Particle;
import com.game.tiles.Tile;
import com.game.tiles.TileType;
import com.game.tiles.VoidTile;
import com.renderer.GameScene;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final int height;
    /** The depth of the maze. */
    private int depth;
    /** The tiles of the maze. */
    private Tile[] tiles;
    /** The monsters of the maze. */
    private Monster[] monsters;
    /** The items of the maze. */
    private WorldItem[] items;
    /** The particles of the maze. */
    private Particle[] particles;
    /** Spawnpoint for the player. */
    private Vector3 spawnPoint;
    /** Temporary entities (only drew for the current frame). */
    private List<Entity> temporaryEntities = new ArrayList<>();
    /** Other entities (other players for example). */
    private List<Entity> otherEntities = new ArrayList<>();

    /**
     * Maze constructor.
     * This is the default constructor for the maze class.
     * It sets the width and height to 1.
     */
    public Maze() {
        this.width = 1;
        this.height = 1;
        this.particles = new Particle[0];

    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     *
     * @param w The width of the maze (x axis).
     * @param h The height of the maze (y axis).
     * @param d The depth of the maze (z axis).
     */
    public Maze(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
        this.tiles = new Tile[w * h * d];
        this.particles = new Particle[0];

    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     *
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
        this.items = new WorldItem[0];
        this.setTilesDefaultPositions();
        this.particles = new Particle[0];
    }

    /**
     * Maze constructor.
     *
     * @param w     the width of the maze (x axis).
     * @param h     the height of the maze (y axis).
     * @param d     the depth of the maze (z axis).
     * @param t     the tiles of the maze.
     * @param spawn the spawnpoint of the player.
     */
    public Maze(int w, int h, int d, Tile[] t, Vector3 spawn) {
        this(w, h, d, t);
        this.spawnPoint = spawn;
        this.particles = new Particle[0];
    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     *
     * @param w The width of the maze (x axis).
     * @param h The height of the maze (y axis).
     * @param d The depth of the maze (z axis).
     * @param t The tiles of the maze.
     * @param m The monsters of the maze.
     * @param i The items of the maze.
     */
    public Maze(int w, int h, int d, Tile[] t, Monster[] m, WorldItem[] i) {
        if (t.length != w * h * d) {
            throw new IllegalArgumentException("The number of tiles must be equal to width * height * depth.");
        }
        this.width = w;
        this.height = h;
        this.depth = d;
        this.tiles = t;
        this.monsters = m;
        this.items = i;
        this.particles = new Particle[0];
        this.setTilesDefaultPositions();
    }

    /**
     * Maze constructor.
     * This is the constructor for the maze class.
     *
     * @param w                 The width of the maze (x axis).
     * @param h                 The height of the maze (y axis).
     * @param d                 The depth of the maze (z axis).
     * @param t                 The tiles of the maze.
     * @param m                 The monsters of the maze.
     * @param i                 The items of the maze.
     * @param setTilesPositions Whether to set the tiles positions or not.
     */
    public Maze(int w, int h, int d, Tile[] t, Monster[] m, WorldItem[] i, boolean setTilesPositions) {
        if (t.length != w * h * d) {
            throw new IllegalArgumentException("The number of tiles must be equal to width * height * depth.");
        }
        this.width = w;
        this.height = h;
        this.depth = d;
        this.monsters = m;
        this.items = i;
        this.particles = new Particle[0];

        if (setTilesPositions) {
            this.tiles = t;
            this.setTilesDefaultPositions();
        } else {
            this.tiles = new Tile[t.length];
            for (Tile tile : t) {
                Vector3 pos = tile.getPosition();
                int index = getTileIndex((int) pos.x, (int) pos.y, (int) pos.z);
                this.tiles[index] = tile;
            }
            for (int j = 0; j < this.tiles.length; j++) {
                if (this.tiles[j] == null) {
                    this.tiles[j] = new VoidTile(new Vector3(
                            j % this.width,
                            j / this.width,
                            j / (this.width * this.height)));
                }
            }
        }
    }

    /**
     * Returns the tile index in the tiles array.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @param width The width of the maze.
     * @param height The height of the maze.
     * @return The tile index in the tiles array.
     */
    public static int getTileIndex(int x, int y, int z, int width, int height) {
        return x + y * width + z * width * height;
    }

    /**
     * Create a maze from a list of entities.
     * @param list The list of entities.
     * @return The maze.
     */
    public static Maze fromList(List<Entity> list) {
        int minX = 0;
        int minY = 0;
        int minZ = 0;
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;

        List<Tile> tiles = new ArrayList<Tile>();
        List<Monster> monsters = new ArrayList<Monster>();
        List<WorldItem> items = new ArrayList<WorldItem>();

        for (Entity e : list) {
            if (e instanceof Tile) {
                tiles.add((Tile) e);
            } else if (e instanceof Monster) {
                monsters.add((Monster) e);
            } else if (e instanceof WorldItem) {
                items.add((WorldItem) e);
            }

            Vector3 pos = e.getPosition();
            minX = Math.min(minX, (int) pos.x);
            minY = Math.min(minY, (int) pos.y);
            minZ = Math.min(minZ, (int) pos.z);
            maxX = Math.max(maxX, (int) pos.x);
            maxY = Math.max(maxY, (int) pos.y);
            maxZ = Math.max(maxZ, (int) pos.z);
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        int depth = maxZ - minZ + 1;

        Tile[] tilesArray = new Tile[width * height * depth];
        Vector3 shift = new Vector3(minX, minY, minZ);
        for (Tile t : tiles) {
            t.setPosition(t.getPosition().sub(shift));
            Vector3 pos = t.getPosition();
            int index = getTileIndex((int) pos.x, (int) pos.y, (int) pos.z, width, height);
            tilesArray[index] = t;
        }
        for (int i = 0; i < tilesArray.length; i++) {
            if (tilesArray[i] == null) {
                tilesArray[i] = new VoidTile(new Vector3(
                        i % width,
                        i / width,
                        i / (width * height)));
            }
        }

        Monster[] monstersArray = new Monster[monsters.size()];
        monsters.toArray(monstersArray);
        WorldItem[] itemsArray = new WorldItem[items.size()];
        items.toArray(itemsArray);

        Maze maze = new Maze(width, height, depth);
        maze.setTiles(tilesArray);
        maze.setItems(itemsArray);
        maze.setMonsters(monstersArray);
        maze.setTilesDefaultPositions();
        return maze;
    }

    private void setTilesDefaultPositions() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                for (int z = 0; z < this.depth; z++) {
                    Tile t = this.getTile(x, y, z);
                    if (t != null) {
                        t.setPosition(new Vector3(x, y, z));
                    }
                }
            }
        }
    }

    /**
     * Add an entity to the maze.
     * @param e The entity to add.
     */
    public void addEntity(Entity e) {
        this.otherEntities.add(e);
    }

    @Override
    public void update() {
        for (Monster m : this.monsters) {
            m.update();
        }
        for (Tile t : this.tiles) {
            if (t != null) {
                t.update();
            }
        }
        for (WorldItem i : this.items) {
            i.update();
        }
        for (Particle p : this.particles) {
            p.update();
        }
        if (Game.getInstance().getPlayer() != null) {
            Game.getInstance().getPlayer().update();
        }
    }

    @Override
    public void render() {
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(this.monsters));
        entities.addAll(Arrays.asList(this.tiles));
        entities.addAll(Arrays.asList(this.items));
        entities.addAll(Arrays.asList(this.particles));
        entities.addAll(otherEntities);
        entities.addAll(temporaryEntities);
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
        for (Entity e : entities) {
            e.render();
        }
        for (Entity e : entities) {
            Controller c = e.getController();
            if (c != null) {
                c.render();
            }
            if (e != null) {
                e.render();
            }
        }

        temporaryEntities.clear();
    }

    /**
     * Add a temporary entity to draw on this frame only.
     *
     * @param e The entity to add.
     */
    public void addTemporaryEntity(Entity e) {
        temporaryEntities.add(e);
    }

    /**
     * Get the width of the maze.
     *
     * @return The width of the maze.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the height of the maze.
     *
     * @return The height of the maze.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Get the depth of the maze.
     *
     * @return The depth of the maze.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Get the tiles of the maze.
     *
     * @return The tiles of the maze.
     */
    public Tile[] getTiles() {
        return Arrays.copyOf(this.tiles, this.tiles.length);
    }

    /**
     * Set the tiles of the maze.
     *
     * @param tiles The tiles of the maze.
     */
    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    /**
     * Get the tile at the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The tile at the given coordinates.
     */
    public Tile getTile(int x, int y, int z) {
        if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth) {
            return new VoidTile(new Vector3(x, y, z));
        }
        return tiles[getTileIndex(x, y, z)];
    }

    /**
     * Get the tile at the given position.
     *
     * @param pos The position.
     * @return The tile at the given coordinates.
     */
    public Tile getTile(Vector3 pos) {
        return getTile(
                (int) Math.floor(pos.x),
                (int) Math.floor(pos.y),
                (int) Math.round(pos.z));
    }

    /**
     * Returns the tile index in the tiles array.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The tile index in the tiles array.
     */
    public int getTileIndex(int x, int y, int z) {
        return getTileIndex(x, y, z, width, height);
    }

    /**
     * Get the monsters of the maze.
     *
     * @return The monsters of the maze.
     */
    public Monster[] getMonsters() {
        return monsters;
    }

    /**
     * remove a monster from the maze.
     *
     * @param m the monster to remove.
     */
    public void removeMonster(Monster m) {
        List<Monster> ms = new ArrayList<>(Arrays.asList(this.monsters));
        ms.remove(m);
        this.monsters = ms.toArray(new Monster[0]);
    }

    /**
     * Set the monsters of the maze.
     *
     * @param monsters The monsters of the maze.
     */
    public void setMonsters(Monster[] monsters) {
        this.monsters = monsters;
    }

    /**
     * Get the items of the maze.
     *
     * @return The items of the maze.
     */
    public WorldItem[] getItems() {
        return items;
    }

    /**
     * Set the items of the maze.
     *
     * @param items The items of the maze.
     */
    public void setItems(WorldItem[] items) {
        this.items = items;
    }

    /**
     * Get the particles of the maze.
     *
     * @return The particles of the maze.
     */
    public Particle[] getParticles() {
        return particles;
    }

    /**
     * ToString method. Displays the maze into grid-like String.
     *
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

    /**
     * Classic setter for the spawnpoint.
     *
     * @param v spawnpoint to set.
     */
    public void setSpawnPoint(Vector3 v) {
        this.spawnPoint = v;
    }

    /**
     * Classic getter for the spawnpoint.
     *
     * @return spawnpoint in the maze.
     */
    public Vector3 getSpawnPoint() {
        return this.spawnPoint;
    }

    /**
     * Removes an item from the maze.
     *
     * @param i item to remove from the maze.
     */
    public void removeItem(WorldItem i) {
        List<WorldItem> itemList = new ArrayList<>(Arrays.asList(this.items));
        itemList.remove(i);
        this.items = itemList.toArray(new WorldItem[0]);
    }

    /**
     * Add a particle to the maze.
     *
     * @param p particle to add to the maze.
     */
    public void addParticle(Particle p) {
        List<Particle> particlesList = new ArrayList<>(Arrays.asList(this.particles));
        particlesList.add(p);
        this.particles = particlesList.toArray(new Particle[0]);
    }

    /**
     * Removes a particle from the maze.
     *
     * @param p particle to remove from the maze.
     */
    public void removeParticle(Particle p) {
        List<Particle> particlesList = new ArrayList<>(Arrays.asList(this.particles));
        particlesList.remove(p);
        this.particles = particlesList.toArray(new Particle[0]);
    }
}
