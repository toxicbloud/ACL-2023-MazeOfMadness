package maze.tiles;

public class WallTile extends Tile {

    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
