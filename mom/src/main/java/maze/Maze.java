package maze;

import maze.tiles.EmptyTile;
import maze.tiles.Tile;
import maze.tiles.WallTile;

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
            for(int j = 0; j < this.width; j++) {
                this.maze.get(i).add(new WallTile(i,j));
            }
        }

        this.generateMaze();
    }



    private void generateMaze(){
        Random r = new Random();
        int start_x, start_y;
        Stack<Tile> stack = new Stack<>();

        // We choose a random cell
        start_x = r.nextInt(this.width-1); // Int between 0 and width - 1
        start_y = r.nextInt(this.height-1); // Int between 0 and width - 1
        System.out.println("[DEBUG] - Start point : (" + start_x + " : " + start_y + ")");

        // We mark it as an empty cell and as visited.
        this.maze.get(start_x).set(start_y, new EmptyTile(start_x, start_y));
        this.maze.get(start_x).get(start_y).visit();

        // We push this cell on the stack
        stack.push(this.maze.get(start_x).get(start_y) );

        while(!stack.isEmpty()) {
            Tile currentCell = stack.pop();
            ArrayList<Tile> neightbours = this.getUnvisitedCellNeightbours(currentCell);
            if (!neightbours.isEmpty()){
                int a = r.nextInt(neightbours.size() - 1);
                System.out.println("[DEBUG] - Found a Random neightbour : " + a + " with " + neightbours.size() + " potential neighbours.");
            }
        }
    }


    private ArrayList<Tile> getUnvisitedCellNeightbours(Tile currentCell) {
        ArrayList<Tile> adjacent_cells = new ArrayList<>();
        Tile neightbour;
        if(currentCell.getX() > 0) {
            neightbour = this.maze.get(currentCell.getX() - 1).get(currentCell.getY());
            if(!neightbour.isVisited()){
                adjacent_cells.add(neightbour);
            }
        }
        if(currentCell.getX() < this.width) {
            neightbour = this.maze.get(currentCell.getX() + 1).get(currentCell.getY());
            if(!neightbour.isVisited()){
                adjacent_cells.add(neightbour);
            }
        }
        if(currentCell.getY() > 0) {
            neightbour = this.maze.get(currentCell.getX()).get(currentCell.getY() - 1);
            if(!neightbour.isVisited()){
                adjacent_cells.add(neightbour);
            }
        }
        if(currentCell.getY() < this.height) {
            neightbour = this.maze.get(currentCell.getX()).get(currentCell.getY() + 1);
            if(!neightbour.isVisited()){
                adjacent_cells.add(neightbour);
            }
        }

        return adjacent_cells;
    }


    public void printMaze(){
        System.out.println("# -------- MAZE Generated -------- #");

        for(int j = 0; j < this.height; j++) {
            for(int i = 0; i < this.width; i++) {
                if(this.maze.get(j).get(i).isWall()) {
                    System.out.print(Data.RED_COLOR + "#   ");
                } else {
                    System.out.print(Data.GREEN_COLOR + "    ");
                }
            }
            System.out.println();
        }
        System.out.println(Data.WHITE_COLOR + "# -------- END OF MAZE -------- #");
    }

    public int getHeight() {
        return this.maze.size();
    }
    public int getWidth() {
        return this.maze.get(0).size();
    }
}
