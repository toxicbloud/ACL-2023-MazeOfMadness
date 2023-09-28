package maze.tiles;

public abstract class Tile {

    private final int x;
    private final int y;

    private boolean visited;

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit(){
        this.visited = true;
    }

    public abstract boolean isWall();
    public abstract boolean isEmpty();
}
