package com.game.weapons;

import com.game.Item;
import com.game.Living;
import com.engine.Sprite;
import com.engine.Texture;

public abstract class Weapon extends Item {
    private int damage;
    private float range;

    protected Weapon(int damage, float range) {
        super(new Sprite(new Texture("images/weapon.png"), 16, 16));
        this.damage = damage;
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public boolean attack(Living living) {
        if (this.distance(living) > this.range) return false;
        living.takeDamage(this.damage);
        return true;
    }

    public void update() {

    }
}
