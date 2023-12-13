package com.game.generators.spawners;

import com.engine.utils.Vector3;
import com.game.Maze;
import com.game.WorldItem;
import com.game.generators.probabilities.ItemProbas;
import com.game.generators.probabilities.PotionProbas;
import com.game.tiles.Tile;
import com.game.tiles.TileType;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * PotionSpawner class.
 */
public final class PotionSpawner {

    /** MIN_DST_PLAYER_WEAPON : Safe distance from player's spawn to not spawn potions in. */
    private static final int MIN_DST_PLAYER_POTION = 8;

    /**
     * Private constructor for the PotionSpawner class.
     */
    private PotionSpawner() {}

    /**
     * This method spawns potions in the maze.
     * @param maze Maze to populate.
     */
    public static void spawnPotion(Maze maze) {
        ArrayList<WorldItem> potions = new ArrayList<>();
        SecureRandom sr = new SecureRandom();
        float randomValue;

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                if (!canSpawnPotion(maze, x, y, sr)) {
                    continue;
                }

                randomValue = sr.nextFloat() * (PotionProbas.DUMMY.computeTotal());
                PotionSpawner.spawnPotion(x, y, randomValue, potions);

            }
        }
        maze.addItems(potions.toArray(new WorldItem[potions.size()]));
    }

    /**
     * Method that spawns a potion inside the maze when called.
     *
     * @param x           x coordinate of the tile to check.
     * @param y           y coordinate of the tile to check.
     * @param randomFloat Random number generator.
     * @param items       Items array.
     */
    private static void spawnPotion(int x, int y, float randomFloat, ArrayList<WorldItem> items) {
        float counter = 0.F;
        for (ItemProbas wp : PotionProbas.DUMMY.getAllItemsProbas()) {
            counter += wp.getValue();
            if (counter >= randomFloat) {
                items.add(wp.getNewItem(x, y));
                break;
            }
        }
    }

    /**
     * This method checks if the potion can be spawned on the choosen tile.
     * It will check if the tile is not the end of the level and at the
     * player's spawnpoint.
     *
     * @param maze The maze used to check.
     * @param x    x coordinate of the tile to check.
     * @param y    y coordinate of the tile to check.
     * @param sr   Random number generator.
     * @return boolean indicating if the tile can accept a potion item.
     */
    private static boolean canSpawnPotion(Maze maze, int x, int y, SecureRandom sr) {
        Vector3 spawn = maze.getSpawnPoint();
        Tile target = maze.getTile(x, y, 0);

        // We ensure that :
        // -> The trap won't be set on the player's spawnpoint.
        // -> The trap won't be set on maze's endpoint.
        // -> That the tile targeted is a ground rock.

        return (int) spawn.x != x
                && (int) spawn.y != y
                && target.getType() != TileType.GROUND_END
                && target.getType() == TileType.GROUND_ROCK
                && maze.getSpawnPoint().dst(x, y, 1) > MIN_DST_PLAYER_POTION
                && sr.nextFloat() <= PotionProbas.DUMMY.getBaseSpawnProba();
    }
}
