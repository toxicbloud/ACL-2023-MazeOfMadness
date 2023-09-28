package maze;

import org.junit.jupiter.api.Test;

public class MazeTest {

    @Test
    void testPrintMaze(){
        Maze maze = new Maze();
        maze.printMaze();

        assert maze.getHeight() == Data.MAZE_HEIGHT : "[ASSERT ERROR] - Height is not correct. Expected " + Data.MAZE_HEIGHT + " and got " + maze.getHeight();
        assert maze.getWidth() == Data.MAZE_WIDTH : "[ASSERT ERROR] - Width is not correct. Expected " + Data.MAZE_WIDTH + " and got " + maze.getWidth();
    }
}
