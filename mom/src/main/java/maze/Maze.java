package maze;

import maze.tiles.EmptyTile;
import maze.tiles.Tile;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {

    private final ArrayList<ArrayList<Tile>> maze;
    private final int height;
    private final int width;

    /**
     * Default constructor. Uses default Data class values.
     */
    public Maze(){
        this.maze = new ArrayList<>();

        this.height = Data.MAZE_HEIGHT;
        this.width = Data.MAZE_WIDTH;

        for(int i = 0; i < this.height; i++) {
            this.maze.add(new ArrayList<>());

        }

        this.generateMaze();
    }



    private void generateMaze(){
        Random r = new Random();
        int start_x, start_y;
        Stack<Tile> stack = new Stack<>();

        start_x = r.nextInt(this.width-1); // Int between 0 and width - 1
        start_y = r.nextInt(this.height-1); // Int between 0 and width - 1

        System.out.println("[DEBUG] - Start point : (" + start_x + " : " + start_y + ")");

        this.maze[start_x][start_y] = new EmptyTile(start_x, start_y);
        this.maze[start_x][start_y].visit();
        stack.push(this.maze[start_x][start_y]);

        while(!stack.isEmpty()) {
            Tile currentCell = stack.pop();





        }
    }


    public void printMaze(){
        System.out.println("# -------- MAZE Generated -------- #");

        for(int j = 0; j < this.height; j++) {
            for(int i = 0; i < this.width; i++) {
                if(this.maze[j][i].isWall()) {
                    System.out.print(Data.RED_COLOR + this.maze[j][i] + "   ");
                } else {
                    System.out.print(Data.GREEN_COLOR + this.maze[j][i] + "   ");
                }
            }
            System.out.println();
        }
        System.out.println(Data.WHITE_COLOR + "# -------- END OF MAZE -------- #");
    }

    public int getHeight() {
        return this.maze.length;
    }
    public int getWidth() {
        return this.maze[0].length;
    }
}
