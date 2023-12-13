package com.game.generators;

import com.GdxTestRunner;
import com.game.Maze;
import com.game.generators.factories.MazeFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Maze maze1 = MazeFactory.createMaze(v, v, 2);
        assert maze1 != null : "[ERROR] - Maze returned is null !";
    }

    @Test()
    void testZeroDepthMazeGeneration() {
        // Will generate a maze 150 by 150
        final int v = 150;
        assertThrows(IllegalArgumentException.class, () -> {
            MazeFactory.createMaze(v, v, 0);
        });
    }
}
