package com.game;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import com.game.weapons.Weapon;

public abstract class Living extends Entity {
    private int health;
    private int speed;
    private Weapon weapon;

    protected Living(Sprite sprite) {
        super(sprite);
    }

    protected Living(Sprite sprite, Vector3 position, Vector3 size) {
        super(sprite, position, size);
    }

    public boolean attack(Living living) {
        if (this.weapon == null) return false;
        return this.weapon.attack(living);
    }

    public boolean takeDamage(int damage) {
        this.health -= damage;
        return this.health <= 0;
    }

    public void regen(int health) {
        this.health += health;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
