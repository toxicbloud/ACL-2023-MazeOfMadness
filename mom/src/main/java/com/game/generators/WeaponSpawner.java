package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Item;
import com.game.Maze;
import com.game.generators.probabilities.ItemProbas;
import com.game.generators.probabilities.WeaponProbas;
import com.game.tiles.Tile;
import com.game.tiles.TileType;

import java.security.SecureRandom;
import java.util.ArrayList;

/** WeaponSpawner class. */
public final class WeaponSpawner {

    /** MIN_DST_PLAYER_WEAPON : Safe distance from player's spawn to not spawn weapons in. */
    private static final int MIN_DST_PLAYER_WEAPON = 8;

    /**
     * Private constructor for the weapon spawner.
     */
    private WeaponSpawner() {}

    /**
     * Method that spawns weapons inside the maze.
     *
     * @param maze Maze to populate with weapons.
     */
    public static void spawnWeapons(Maze maze) {
        SecureRandom sr = new SecureRandom();
        ArrayList<Item> items = new ArrayList<>();
        float randomValue;

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                if (!canSpawnWeapon(maze, x, y, sr)) {
                    continue;
                }
                randomValue = sr.nextFloat() * (WeaponProbas.DUMMY.computeTotal());
                WeaponSpawner.spawnWeapon(x, y, randomValue, items);
            }
        }

        maze.addItems(items.toArray(new Item[items.size()]));
    }

    /**
     * Method that spawns a weapon inside the maze when called.
     *
     * @param x           x coordinate of the tile to check.
     * @param y           y coordinate of the tile to check.
     * @param randomFloat Random number generator.
     * @param items       Items array.
     */
    private static void spawnWeapon(int x, int y, float randomFloat, ArrayList<Item> items) {
        float counter = 0.F;
        for (ItemProbas wp : WeaponProbas.DUMMY.getItemsProbasArray()) {
            counter += wp.getValue();
            if (counter >= randomFloat) {
                items.add(wp.getNewItem(x, y));
                break;
            }
        }
    }

    /**
     * This method checks if the Weapon can be spawned on the choosen tile.
     * It will check if the tile is not the end of the level and at the
     * player's spawnpoint.
     *
     * @param maze The maze used to check.
     * @param x    x coordinate of the tile to check.
     * @param y    y coordinate of the tile to check.
     * @param sr   Random number generator.
     * @return boolean indicating if the tile can accept a potion item.
     */
    private static boolean canSpawnWeapon(Maze maze, int x, int y, SecureRandom sr) {
        Vector3 spawn = maze.getSpawnPoint();
        Tile target = maze.getTile(x, y, 0);

        // We ensure that :
        // -> The Weapon won't be set on the player's spawnpoint.
        // -> The Weapon won't be set on maze's endpoint.
        // -> That the tile targeted is a ground rock.

        return (int) spawn.x != x
                && (int) spawn.y != y
                && sr.nextFloat() <= WeaponProbas.DUMMY.getBaseSpawnProba()
                && maze.getSpawnPoint().dst(x, y, 1) > MIN_DST_PLAYER_WEAPON
                && target.getType() != TileType.GROUND_END
                && target.getType() == TileType.GROUND_ROCK;
    }
}
