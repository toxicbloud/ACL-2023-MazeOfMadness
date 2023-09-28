package maze;

import maze.tiles.EmptyTile;
import org.junit.jupiter.api.Test;

public class MazeTest {

    @Test
    void testMazeSize(){
        Maze maze = new Maze();
//        maze.printMaze();

        assert maze.getHeight() == Data.MAZE_HEIGHT : "[ASSERT ERROR] - Height is not correct. Expected " + Data.MAZE_HEIGHT + " and got " + maze.getHeight();
        assert maze.getWidth() == Data.MAZE_WIDTH : "[ASSERT ERROR] - Width is not correct. Expected " + Data.MAZE_WIDTH + " and got " + maze.getWidth();
    }


    void testGetNeightbours(){
        Maze maze = new Maze();
        maze.printMaze();

        EmptyTile et1 = new EmptyTile(0, 10);
        EmptyTile et2 = new EmptyTile(Data.MAZE_WIDTH, 0);
        EmptyTile et3 = new EmptyTile(10, 0);
        EmptyTile et4 = new EmptyTile(0, Data.MAZE_HEIGHT);



    }
}
