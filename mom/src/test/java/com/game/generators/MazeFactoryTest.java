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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
        // TODO : remove this test. Too long to process.
        // Will try to generate each possible maze from the random height and width proposed in the default createMaze method.
        final int MINIMUM_MAZE_SIZE = MazeFactory.MIN_SIZE;
        final int MAXIMUM_MAZE_SIZE = MazeFactory.MAX_SIZE;
        int counter = 0;
        final boolean debug = true;

        for (int i = MINIMUM_MAZE_SIZE; i <= MAXIMUM_MAZE_SIZE; i++) {
            for (int j = MINIMUM_MAZE_SIZE; j <= MAXIMUM_MAZE_SIZE; j++) {
                try {
                    MazeFactory.createMaze(i, j, 2);        // No need to change default depth for now...
                } catch (Exception e) {
                    if (debug) System.out.println(ANSI_RED + "[DEBUG] - Maze generation failed : " + i + " x " + j + ANSI_RESET);
                    counter += 1;
                }
                if(debug) System.out.println(ANSI_GREEN + "[DEBUG] - Maz generation successful : " + i + " x " + j + ANSI_RESET);
            }
        }
        assert counter == 0 : ANSI_RED + "[ERROR] - Some mazes failed to generate ! \n -> "
            + counter + " out of " + (MAXIMUM_MAZE_SIZE - MINIMUM_MAZE_SIZE) * (MAXIMUM_MAZE_SIZE - MINIMUM_MAZE_SIZE) + ANSI_RESET ;
    }
}
