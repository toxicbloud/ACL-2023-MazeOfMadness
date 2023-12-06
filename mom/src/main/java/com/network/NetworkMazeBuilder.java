package com.network;

import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Network maze builder.
 */
public class NetworkMazeBuilder {
    /** Maze data length. */
    private int dataLength;

    /** Maze data. */
    private List<Entity> data;

    /** Maze. */
    private Maze maze;

    /**
     * Default constructor.
     */
    public NetworkMazeBuilder() {
        this.data = new ArrayList<>();
    }

    /**
     * Set the maze data length.
     * @param l Maze data length.
     */
    public void setDataLength(int l) {
        this.dataLength = l;
    }

    /**
     * Add an entity to the maze.
     * @param entity Entity to add.
     */
    public void addEntity(Entity entity) {
        this.data.add(entity);
    }

    /**
     * Get the maze entities number.
     * @return Maze entities number.
     */
    public int getEntitiesNumber() {
        return this.data.size();
    }

    /**
     * Get the maze data length.
     * @return Maze data length.
     */
    public int getDataLength() {
        return dataLength;
    }

    /**
     * Build the maze.
     * @return Maze.
     */
    public Maze build() {
        if (this.maze != null) {
            return this.maze;
        }

        // NOTE : We don't use the maze dimensions because it is automatically
        //       calculated from the data list, but we need to get a smooth and
        //       quite accurate map loading animation (percents).
        this.maze = Maze.fromList(data);
        this.maze.setSpawnPoint(new Vector3());
        return this.maze;
    }
}
