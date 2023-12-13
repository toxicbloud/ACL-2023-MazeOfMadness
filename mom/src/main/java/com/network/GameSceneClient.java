package com.network;

import com.game.Game;
import com.game.Maze;
import com.game.Player;
import com.game.controllers.NetworkPlayerController;
import com.game.controllers.PlayerController;
import com.renderer.GameScene;

/**
 * Game scene for the server.
 */
public class GameSceneClient extends GameScene {
    /** Network client. */
    private NetworkClient client;

    /**
     * Constructor.
     * @param maze Maze.
     * @param client Network client.
     */
    public GameSceneClient(Maze maze, NetworkClient client) {
        super(maze);
        this.client = client;

        client.when((data, infos) -> {
            return data[0] == NetworkDialogs.MAZE_ADD && data[1 + 2] == NetworkDialogs.ENTITY_PLR;
        }, (data, infos) -> {
            Player player = NetworkDialogs.getPlayerFromData(data, 2 + 1);
            System.out.println("Player base pos: " + player.getPosition());
            maze.addEntity(player);
            int clientID = NetworkDialogs.getIntValue(data, 1);
            System.out.println("Player " + clientID + " added to maze (perso = " + client.getId() + ")");
            if (clientID == client.getId()) {
                Game.getInstance().setPlayer(player);
                new PlayerController(player);
                System.out.println("Player " + clientID + " position set : " + player.getPosition());
            } else {
                new NetworkPlayerController(player, clientID, client);
            }
            return false;
        });

        client.sendData(new byte[]{NetworkDialogs.GAME_RDY}); // ready to game
    }

    @Override
    public void update() {
        super.update();
        client.update();

        Player player = Game.getInstance().getPlayer();
        if (player != null && player.hasBeenUpdated()) {
            byte[] data = NetworkDialogs.encodePlayerValue(player, 1 + 2);
            data[0] = NetworkDialogs.PLR_UPD;
            NetworkDialogs.encodeIntValue(client.getId(), data, 1);
            client.sendData(data);
        }
    }
}
