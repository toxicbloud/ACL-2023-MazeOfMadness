package com.game;

import com.engine.Sprite;
import com.engine.Texture;
import com.game.weapons.PlayerFist;

public class Player extends Living {
    public Player() {
        super(new Sprite(new Texture("images/player.png"), 16, 16));
        this.setWeapon(new PlayerFist());
    }

    public void update() {
        // TODO
    }
}
