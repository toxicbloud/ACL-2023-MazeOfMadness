package com.game.controllers;

import com.game.Living;

/**
 * Network living controller.
 * (to see other livings in map)
 */
public class NetworkLivingController extends Controller {
    /** Network living id. */
    private int id;

    /**
     * Constructor.
     * @param player Living.
     * @param id Living id.
     */
    public NetworkLivingController(Living player, int id) {
        super(player);
        this.id = id;
    }

    @Override
    public void update() {

    }
}
