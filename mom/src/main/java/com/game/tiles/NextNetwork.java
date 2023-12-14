package com.game.tiles;

import com.engine.Scene;
import com.engine.Sprite;
import com.engine.Window;
import com.engine.utils.Vector3;
import com.game.Player;
import com.network.GameSceneServer;
import com.network.NetworkDialogs;
import com.network.NetworkManagerTCP;

/**
 * NextNetwork class.
 * A tile that will be used to go to the next maze on network games.
 */
public class NextNetwork extends Ground {
    /**
     * Next sprite shift from texture top.
     */
    private static final int NEXT_SPRITE_SHIFT = SPRITE_SIZE * 2;

    /** Network manager. */
    private NetworkManagerTCP networkManager;

    /**
     * NextNetwork constructor.
     */
    public NextNetwork() {
        super(TileType.GROUND_NEXT, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, NEXT_SPRITE_SHIFT));
    }

    /**
     * NextNetwork full constructor.
     *
     * @param position The position of the tile
     * @param networkManager The network manager
     */
    public NextNetwork(Vector3 position, NetworkManagerTCP networkManager) {
        super(TileType.GROUND_NEXT, new Sprite(TILE_TEXTURE, SPRITE_SIZE, SPRITE_SIZE, NEXT_SPRITE_SHIFT), position);
        this.networkManager = networkManager;
    }

    @Override
    public void onPlayerExit(Player p) {
    }

    @Override
    public void onPlayerEnter(Player p) {
        networkManager.sendData(new byte[]{NetworkDialogs.GAME_NXT});
        Scene scene = Window.getInstance().getScene();
        if (scene instanceof GameSceneServer) {
            GameSceneServer gameSceneServer = (GameSceneServer) scene;
            gameSceneServer.onNextCalled();
        }
    }
}
