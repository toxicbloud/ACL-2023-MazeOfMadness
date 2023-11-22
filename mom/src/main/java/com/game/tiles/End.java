package com.game.tiles;

import com.engine.Sprite;
import com.engine.Window;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Player;
import com.ui.EndScene;

/**
 * End class.
 * Finish the game when the player reach this tile.
 */
public class End extends Ground {

    /**
     * End sprite shift from texture top.
     */
    private static final int END_SPRITE_SHIFT = SPRITE_SIZE * 3;

    /**
     * End constructor.
     *
     *
     */
    public End() {
        super(TileType.GROUND_END, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, END_SPRITE_SHIFT));
    }

    /**
     * End full constructor.
     *
     * @param position The position of the tile
     */
    public End(Vector3 position) {
        super(TileType.GROUND_END, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, END_SPRITE_SHIFT), position);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void onPlayerExit(Player p) {
    }

    @Override
    public void onPlayerEnter(Player p) {
        Game.getInstance().setMaze(null);
        Game.getInstance().setPlayer(null);
        Window.getInstance().setScene(new EndScene(true));
    }

}
