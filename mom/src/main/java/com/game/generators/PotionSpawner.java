package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Item;
import com.game.Maze;
import com.game.potions.HealthPotion;
import com.game.potions.SpeedPotion;
import com.game.potions.StrengthPotion;
import com.game.tiles.Tile;
import com.game.tiles.TileType;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * PotionSpawner class.
 */
public final class PotionSpawner {
    /** POTION_SPAWN_PROBABILITY : Rate of spawn for the potions. */
    private static final float POTION_SPAWN_PROBABILITY = 0.01F;
    /** POTION_SPAWN_PROBABILITY : Rate of spawn for the potions. */
    private static final float HEALTH_POTION_THRESHOLD = 0.5F;
    /** POTION_SPAWN_PROBABILITY : Rate of spawn for the potions. */
    private static final float SPEED_POTION_THRESHOLD = 0.75F;
    /** Maximum number of health potions that can be found in the maze. */
    private static final int MAX_HEALTH_POTIONS = 3;
    /** Maximum number of speed potions that can be found in the maze. */
    private static final int MAX_SPEED_POTIONS = 1;
    /** Maximum number of strength potions that can be found in the maze. */
    private static final int MAX_STRENGTH_POTIONS = 1;

    /**
     * Private constructor for the PotionSpawner class.
     */
    private PotionSpawner() {}

    /**
     * This method spawns potions in the maze.
     * @param maze Maze to populate.
     */
    public static void spawnPotion(Maze maze) {
        ArrayList<Item> potions = new ArrayList<>();
        int healthPotionCounter = 0;
        int speedPotionCounter = 0;
        int strengthPotionCounter = 0;
        float randomFloat;
        SecureRandom sr = new SecureRandom();

        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                if (PotionSpawner.canSpawnPotion(maze, i, j, sr)) {
                    randomFloat = sr.nextFloat();
                    if (randomFloat < PotionSpawner.HEALTH_POTION_THRESHOLD
                            && healthPotionCounter < PotionSpawner.MAX_HEALTH_POTIONS) {
                        healthPotionCounter += 1;
                        potions.add(new HealthPotion(new Vector3(i, j, 1)));
                    } else if (randomFloat < PotionSpawner.SPEED_POTION_THRESHOLD
                            && speedPotionCounter < PotionSpawner.MAX_SPEED_POTIONS) {
                        speedPotionCounter += 1;
                        potions.add(new SpeedPotion(new Vector3(i, j, 1)));
                    } else if (strengthPotionCounter < PotionSpawner.MAX_STRENGTH_POTIONS) {
                        strengthPotionCounter += 1;
                        potions.add(new StrengthPotion(new Vector3(i, j, 1)));
                    }
                }
            }
        }
        maze.setItems(potions.toArray(new Item[potions.size()]));
    }

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
                && sr.nextFloat() <= PotionSpawner.POTION_SPAWN_PROBABILITY;
    }
}
