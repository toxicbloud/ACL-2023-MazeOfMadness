package maze;

import java.util.Arrays;

public class Maze {

    private int[][] maze;
    private int height;
    private int width;

    /**
     * Default constructor. Uses default Data class values.
     */
    public Maze(){
        this.maze = new int[Data.MAZE_HEIGHT][Data.MAZE_WIDTH];
        this.height = Data.MAZE_HEIGHT;
        this.width = Data.MAZE_WIDTH;

        // Building Walls (Trump style)
        // -> Lines
        for(int i = 0; i < this.width; i++){
            this.maze[0][i] = 1;
            this.maze[this.height - 1][i] = 1;
        }
        for(int i = 0; i < this.height; i++){
            this.maze[i][0] = 1;
            this.maze[i][this.width - 1] = 1;
        }
    }


    public void printMaze(){
        System.out.println("# ---- MAZE ---- #");

        for(int j = 0; j < this.height; j++) {
            for(int i = 0; i < this.width; i++) {
                    if(this.maze[j][i] == 1) {
                    System.out.print("\u001B[31m" + this.maze[j][i] + "   ");
                } else {
                    System.out.print("\u001B[32m" + this.maze[j][i] + "   ");
                }
            }
            System.out.println();
        }
    }
}
