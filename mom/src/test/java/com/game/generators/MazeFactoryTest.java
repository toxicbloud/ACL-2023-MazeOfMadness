package com.game.generators;

import com.GdxTestRunner;
import com.game.Maze;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Maze test class.
 */
@ExtendWith(GdxTestRunner.class)
public class MazeFactoryTest {

    @Test
    void testDefaultMazeGeneration() {
        // Will generate a maze sample randomly.
        Maze maze1 = MazeFactory.createMaze();
        assert maze1 != null : "[ERROR] - Maze returned is null !";

    }

    @Test
    void testCustomMazeGeneration() {
        // Will generate a maze 150 by 150
        final int v = 150;
        Maze maze1 = MazeFactory.createMaze(v, v, 1);
        assert maze1 != null : "[ERROR] - Maze returned is null !";
    }

    @Test
    void testAllRandomMazeGenerations() {
        // Will try to generate each possible maze for the random height and width possible.
        final int MINIMUM_MAZE_SIZE = MazeFactory.MIN_SIZE;
        final int MAXIMUM_MAZE_SIZE = MazeFactory.MAX_SIZE;
        int counter = 0;

        for (int i = MINIMUM_MAZE_SIZE; i <= MAXIMUM_MAZE_SIZE; i++) {
            for (int j = MINIMUM_MAZE_SIZE; j <= MAXIMUM_MAZE_SIZE; j++) {
                System.out.println("[DEBUG] - Trying maze dimensions : " + i + " x " + j);
                try {
                    MazeFactory.createMaze(i, j, 2);        // No need to change default depth for now...
                } catch (Exception e) {
                    System.out.println("[DEBUG] - Maze generation failed : " + i + " x " + j);
                    counter += 1;
                }
            }
        }
        assert counter == 0 : "[ERROR] - Some mazes failed to generate ! \n -> " + counter + " out of " + MINIMUM_MAZE_SIZE * MAXIMUM_MAZE_SIZE ;
    }
}
