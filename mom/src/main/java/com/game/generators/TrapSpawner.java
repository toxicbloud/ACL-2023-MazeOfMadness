package com.game.generators;

import com.engine.utils.Vector3;
import com.game.tiles.*;

import java.security.SecureRandom;

/**
 * Trap spawner class.
 */
public final class TrapSpawner {

    /** LAVA_SPAWN_PROBABILITY : Rate of spawn for the lava traps inside the maze.*/
    private static final float LAVA_SPAWN_PROBABILITY = 0.15F;
    /** WATER_SPAWN_PROBABILITY : Rate of spawn for the water traps inside the maze.*/
    private static final float WATER_SPAWN_PROBABILITY = 0.15F;
    /** SPIKES_SPAWN_PROBABILITY : Rate of spawn for the spikes traps inside the maze.*/
    private static final float SPIKES_SPAWN_PROBABILITY = 0.10F;

    /** Private constructor for the TrapSpawner class. */
    private TrapSpawner() {}

    /**
     * This Method spawn different traps inside the maze.
     *
     * @param maze       Maze to populate.
     * @param x          x coordinate of the tile to populate.
     * @param y          y coordinate of the tile to populate.
     * @param mazeWidth  Width of the maze.
     * @param spawnpoint Spawnpoint to not populate with traps.
     */
    public static void spawnTrap(Tile[] maze, int x, int y, int mazeWidth, Vector3 spawnpoint) {
        SecureRandom sr = new SecureRandom();
        boolean spawnedATrap = false; // This value allows us to not spawn a trap where there is already one.

        // Lava spawn
        if (sr.nextFloat() < TrapSpawner.LAVA_SPAWN_PROBABILITY
                && TrapSpawner.canSpawnTrap(maze, x, y, mazeWidth, spawnpoint)) {
            maze[x + y * mazeWidth] = new GroundLava(new Vector3(x, y, 0));
            spawnedATrap = true;
        }
        // Water spawn
        if (!spawnedATrap
                && sr.nextFloat() < TrapSpawner.WATER_SPAWN_PROBABILITY
                && TrapSpawner.canSpawnTrap(maze, x, y, mazeWidth, spawnpoint)) {
            maze[x + y * mazeWidth] = new GroundWater(new Vector3(x, y, 0));
            spawnedATrap = true;
        }
        // Ground spikes
        if (!spawnedATrap
                && sr.nextFloat() < TrapSpawner.SPIKES_SPAWN_PROBABILITY
                && TrapSpawner.canSpawnTrap(maze, x, y, mazeWidth, spawnpoint)) {
            maze[x + y * mazeWidth] = new GroundSpikes(new Vector3(x, y, 0));
        }
    }

    /**
     * This method checks if the trap can be spawned on the choosen tile.
     * It will check if the tile is not the end of the level and at the
     * player's spawnpoint.
     *
     * @param maze       The maze used to check.
     * @param x          x coordinate of the tile to check.
     * @param y          y coordinate of the tile to check.
     * @param mazeWidth  Width of the maze.
     * @param spawnpoint Spawnpoint to not populate with traps.
     * @return boolean indicating if the tile can accept a trap.
     */
    private static boolean canSpawnTrap(Tile[] maze, int x, int y, int mazeWidth, Vector3 spawnpoint) {
        Tile target = maze[x + y * mazeWidth];

        // We ensure that :
        // -> The trap won't be set on the player's spawnpoint.
        // -> That the tile targeted is a ground rock.
        return (int) spawnpoint.x != x
               && (int) spawnpoint.y != y
               && target.getType() == TileType.GROUND_ROCK;
    }
}
