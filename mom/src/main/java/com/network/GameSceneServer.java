package com.network;

import com.game.Maze;
import com.game.Player;
import com.game.controllers.NetworkLivingController;
import com.renderer.GameScene;

import java.util.List;

/**
 * Game scene for the server.
 */
public class GameSceneServer extends GameScene {
    /** Network server. */
    private NetworkServer server;

    /** Client players list. */
    private Player[] players;

    /**
     * Constructor.
     * @param maze Maze.
     * @param server Network server.
     */
    public GameSceneServer(Maze maze, NetworkServer server) {
        super(maze);
        this.server = server;

        List<NetworkInfos> clients = server.getClients();
        players = new Player[clients.size()];
        for (int i = 0; i < players.length; i++) {
            Player player = new Player(maze.getSpawnPoint());
            players[i] = player;
            player.registerController(new NetworkLivingController(player, clients.get(i).getId()));
            maze.addEntity(players[i]);
            byte[] playerData = NetworkDialogs.encodePlayerValue(player, 2);
            playerData[0] = NetworkDialogs.MAZE_ADD;
            playerData[1] = (byte) clients.get(i).getId();
            server.sendData(playerData);
        }
    }

    @Override
    public void update() {
        super.update();
        server.update();
    }
}
