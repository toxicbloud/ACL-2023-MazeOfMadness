package com.game.generators;

import com.engine.utils.Vector3;
import com.game.tiles.*;

import java.security.SecureRandom;

/**
 * Trap spawner class.
 */
public final class TrapSpawner {

    /** LAVA_SPAWN_PROBABILITY : Rate of spawn for the lava traps inside the maze.*/
    private static final float LAVA_SPAWN_PROBABILITY = 0.1F;
    /** WATER_SPAWN_PROBABILITY : Rate of spawn for the water traps inside the maze.*/
    private static final float WATER_SPAWN_PROBABILITY = 0.1F;
    /** SPIKES_SPAWN_PROBABILITY : Rate of spawn for the spikes traps inside the maze.*/
    private static final float SPIKES_SPAWN_PROBABILITY = 0.05F;
    /** MIN_DST_PLAYER_TRAP : Minimal distance from the player's spawnpoint where no traps will spawn. */
    private static final int MIN_DST_PLAYER_TRAP = 6;

    /** Private constructor for the TrapSpawner class. */
    private TrapSpawner() {}

    /**
     * This Method spawn different traps inside the maze.
     *
     * @param maze       Maze to populate.
     * @param x          x coordinate of the tile to populate.
     * @param y          y coordinate of the tile to populate.
     * @param tileIndex  Index of the tile to check.
     * @param spawnpoint Spawnpoint to not populate with traps.
     */
    public static void spawnTrap(Tile[] maze, int x, int y, int tileIndex, Vector3 spawnpoint) {
        SecureRandom sr = new SecureRandom();
        float randomValue = sr.nextFloat();

        if (!TrapSpawner.canSpawnTrap(maze, x, y, tileIndex, spawnpoint)) {
            return;
        }

        // Lava spawn
        if (randomValue <= TrapSpawner.LAVA_SPAWN_PROBABILITY) {
            maze[tileIndex] = new GroundLava(new Vector3(x, y, 0));
            return;
        }
        // Water spawn
        if (randomValue <= TrapSpawner.LAVA_SPAWN_PROBABILITY
                + TrapSpawner.WATER_SPAWN_PROBABILITY) {
            maze[tileIndex] = new GroundWater(new Vector3(x, y, 0));
            return;
        }
        // Ground spikes
        if (randomValue <= TrapSpawner.LAVA_SPAWN_PROBABILITY
                + TrapSpawner.WATER_SPAWN_PROBABILITY
                + TrapSpawner.SPIKES_SPAWN_PROBABILITY
        ) {
            maze[tileIndex] = new GroundSpikes(new Vector3(x, y, 0));
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
     * @param tileIndex  Index of the tile to check.
     * @param spawnpoint Spawnpoint to not populate with traps.
     * @return boolean indicating if the tile can accept a trap.
     */
    private static boolean canSpawnTrap(Tile[] maze, int x, int y, int tileIndex, Vector3 spawnpoint) {
        // We ensure that :
        // -> The trap won't be set near the player's spawnpoint.
        // -> That the tile targeted is a ground rock.
        return spawnpoint.dst(x, y, 1.0F) > MIN_DST_PLAYER_TRAP
               && maze[tileIndex].getType() == TileType.GROUND_ROCK;
    }
}
