package maze.tiles;

public class EmptyTile extends Tile {

    public EmptyTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
