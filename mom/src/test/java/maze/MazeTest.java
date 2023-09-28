package maze;

import com.game.Maze;
import com.game.generators.MazeFactory;
import org.junit.jupiter.api.Test;

public class MazeTest {

    @Test
    void testDefaultMazeGeneration(){

        MazeFactory mf = new MazeFactory();     // Will generate mazes 20 by 20
        Maze maze1 = mf.createMaze();

        assert maze1 != null : "[ERROR] - Maze returned is null !";
    }

    @Test
    void testCustomMazeGeneration(){

        MazeFactory mf = new MazeFactory();     // Will generate mazes 150 by 150
        Maze maze1 = mf.createMaze(150,150);

        assert maze1 != null : "[ERROR] - Maze returned is null !";
    }
}
