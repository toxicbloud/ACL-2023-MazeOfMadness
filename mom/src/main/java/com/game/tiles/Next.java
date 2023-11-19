package com.game.tiles;

import com.engine.Sprite;
import com.engine.Window;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Player;
import com.game.generators.MazeFactory;
import com.game.generators.MonsterSpawner;
import com.game.generators.TrapSpawner;
import com.renderer.GameScene;

/**
 * Next class.
 * A tile that will be used to go to the next maze.
 */
public class Next extends Ground {
    /**
     * Next sprite shift from texture top.
     */
    private static final int NEXT_SPRITE_SHIFT = SPRITE_SIZE * 2;

    /**
     * Next constructor.
     */
    public Next() {
        super(TileType.GROUND_NEXT, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, NEXT_SPRITE_SHIFT));
    }

    /**
     * Next full constructor.
     *
     * @param position The position of the tile
     */
    public Next(Vector3 position) {
        super(TileType.GROUND_NEXT, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, NEXT_SPRITE_SHIFT), position);
    }

    @Override
    public void onPlayerExit(Player p) {
    }

    @Override
    public void onPlayerEnter(Player p) {
        var maze = TrapSpawner.spawnTraps(MazeFactory.createMaze());
        Game.getInstance().setMaze(maze);
        Game.getInstance().getPlayer().setPosition(maze.getSpawnPoint());
        Window.getInstance().setScene(new GameScene());
        MonsterSpawner.spawnMonsters(maze);
    }
}
