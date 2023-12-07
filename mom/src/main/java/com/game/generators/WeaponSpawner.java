package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Item;
import com.game.Maze;
import com.game.tiles.Tile;
import com.game.tiles.TileType;

import java.security.SecureRandom;
import java.util.ArrayList;

/** WeaponSpawner class. */
public final class WeaponSpawner {

    /** MIN_DST_PLAYER_WEAPON : Safe distance from player's spawn to not spawn weapons in. */
    private static final int MIN_DST_PLAYER_WEAPON = 8;

    /** WEAPON_SPAWN_PROBABILITY : Base spawn probability for a weapon. */
    private static final float WEAPON_SPAWN_PROBABILITY = 0.1F;

    /** SWORD_PROBABILITY : Base spawn probability for a sword. */
    private static final float SWORD_PROBABILTY = 0.1F;

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

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                if (!canSpawnWeapon(maze, x, y, sr)) {
                    continue;
                }
                WeaponSpawner.spawnWeapon(x, y, sr.nextFloat(), items);
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
        if (randomFloat <= WeaponSpawner.SWORD_PROBABILTY) {
            items.add(WeaponFactory.createSword(x, y));
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
                && target.getType() != TileType.GROUND_END
                && target.getType() == TileType.GROUND_ROCK
                && sr.nextFloat() <= WeaponSpawner.WEAPON_SPAWN_PROBABILITY;
    }
}
