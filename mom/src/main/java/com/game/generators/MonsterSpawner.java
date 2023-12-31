package com.game.generators;

import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.GhostController;
import com.game.controllers.ZombieController;
import com.game.monsters.Ghost;
import com.game.monsters.Monster;
import com.game.monsters.Zombie;

import java.util.ArrayList;
import java.util.List;

/**
 * MonsterSpawner class.
 * It spawns monster on the maze.
 */
public final class MonsterSpawner {
    /** Probability of spawning a zombie. */
    public static final double PROBA_ZOMBIE = 0.03;

    /** Probability of spawning a ghost. */
    public static final double PROBA_GHOST = 0.005;

    /** Minimum distance between player and monster. */
    public static final float MIN_DST_PLAYER_MONSTER = 6;

    /**
     * Private constructor for MonsterSpawuner class.
     */
    private MonsterSpawner() {}

    /**
     * spawnMonster method. Spawns mobs inside the maze.
     * @param maze Maze to populate.
     */
    public static void spawnMonsters(Maze maze) {
        List<Monster> monsters = new ArrayList<>();
        double randomChoice;

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                if (!canSpawnMonster(maze, x, y)) {
                    continue;
                }

                randomChoice = Math.random();

                if (randomChoice <= PROBA_ZOMBIE) {
                    Zombie m = new Zombie(new Vector3(x, y, 1));
                    new ZombieController(m);
                    monsters.add(m);
                    continue;
                }
                if (randomChoice <= PROBA_GHOST + PROBA_ZOMBIE) {
                    Ghost m = new Ghost(new Vector3(x, y, 1));
                    new GhostController(m);
                    monsters.add(m);
                }
            }
        }
        maze.setMonsters(monsters.toArray(new Monster[monsters.size()]));
    }

    private static boolean canSpawnMonster(Maze maze, int x, int y) {
        boolean blocSolid = maze.getTile(x, y, 1).isSolid();
        Player player = Game.getInstance().getPlayer();
        boolean farFromPlayer = player != null
            ? player.getPosition().dst(x, y, 1) > MIN_DST_PLAYER_MONSTER
            : true;
        return !blocSolid && farFromPlayer;
    }
}
