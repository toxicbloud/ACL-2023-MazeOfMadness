package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Maze;
import com.game.tiles.GroundLava;
import com.game.tiles.GroundSpikes;
import com.game.tiles.GroundWater;
import com.game.tiles.Tile;
import com.game.tiles.TileType;

import java.security.SecureRandom;

/**
 * Trap spawner class.
 */
public final class TrapSpawner {

    /**
     * LAVA_SPAWN_PROBABILITY : Rate of spawn for the lava traps inside the maze.
     */
    private static final float LAVA_SPAWN_PROBABILITY = 0.10F;
    /**
     * WATER_SPAWN_PROBABILITY : Rate of spawn for the water traps inside the maze.
     */
    private static final float WATER_SPAWN_PROBABILITY = 0.20F;

    /**
     * SPIKES_SPAWN_PROBABILITY : Rate of spawn for the spikes traps inside the
     * maze.
     */
    private static final float SPIKES_SPAWN_PROBABILITY = 0.05F;

    /** Private constructor for the TrapSpawner class. */
    private TrapSpawner() {
    }

    /**
     * This Method spawn different traps inside the maze.
     *
     * @param maze Maze to populate.
     * @return Maze populated with traps.
     */
    public static Maze spawnTraps(Maze maze) {
        SecureRandom sr = new SecureRandom();
        Tile[] mazeTiles = maze.getTiles();
        boolean spawnedATrap = false; // This value allows us to not spawn a trap where there is already one.

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                // Lava spawn
                if (sr.nextFloat() < TrapSpawner.LAVA_SPAWN_PROBABILITY
                        && TrapSpawner.canSpawnTraps(maze, x, y)) {
                    mazeTiles[x + y * maze.getWidth()] = new GroundLava(new Vector3(x, y, 0));
                    spawnedATrap = true;
                }
                // Water spawn
                if (!spawnedATrap
                        && sr.nextFloat() < TrapSpawner.WATER_SPAWN_PROBABILITY
                        && TrapSpawner.canSpawnTraps(maze, x, y)) {
                    mazeTiles[x + y * maze.getWidth()] = new GroundWater(new Vector3(x, y, 0));
                }
                spawnedATrap = false;
                // Ground spikes
                if (sr.nextFloat() < TrapSpawner.SPIKES_SPAWN_PROBABILITY
                        && TrapSpawner.canSpawnTraps(maze, x, y)) {
                    mazeTiles[x + y * maze.getWidth()] = new GroundSpikes(new Vector3(x, y, 0));
                }
            }
        }
        return new Maze(maze.getWidth(), maze.getHeight(), maze.getDepth(), mazeTiles, maze.getSpawnPoint());
    }

    /**
     * This method checks if the trap can be spawned on the choosen tile.
     * It will check if the tile is not the end of the level and at the
     * player's spawnpoint.
     *
     * @param maze The maze used to check.
     * @param x    x coordinate of the tile to check.
     * @param y    y coordinate of the tile to check.
     * @return boolean indicating if the tile can accept a trap.
     */
    private static boolean canSpawnTraps(Maze maze, int x, int y) {
        Vector3 spawn = maze.getSpawnPoint();
        Tile target = maze.getTile(x, y, 0);

        // We ensure that :
        // -> The trap won't be set on the player's spawnpoint.
        // -> The trap won't be set on maze's endpoint.
        // -> That the tile targeted is a ground rock.

        return (int) spawn.x != x
                && (int) spawn.y != y
                && target.getType() != TileType.GROUND_END
                && target.getType() == TileType.GROUND_ROCK;
    }
}
