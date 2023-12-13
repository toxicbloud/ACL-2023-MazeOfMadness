package com.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Living;
import com.game.particles.Arrow;

import java.util.List;

/**
 * Bow class.
 */
public class Bow extends Weapon {
    /** Bow damage amount. */
    private static final int DAMAGE = 30;
    /** Bow cooldown. */
    private static final int ATTACK_COOLDOWN = 500;
    /**
     * Bow shoot sound.
     */
    private static final Sound SHOOT_SOUND = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/whoosh.mp3"));

    /**
     * Bow constructor.
     */
    public Bow() {
        super(new Vector3(), DAMAGE, ATTACK_COOLDOWN, Integer.MAX_VALUE, false, new Sprite(new Texture(
                "images/bowitem.png"), SPRITE_SIZE, SPRITE_SIZE, 0));
    }

    /**
     * Bow full constructor.
     *
     * @param position The position of the Bow.
     */
    public Bow(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, Integer.MAX_VALUE, false, new Sprite(new Texture(
                "images/bowitem.png"), SPRITE_SIZE, SPRITE_SIZE, 0));
    }

    /**
     * Bow full constructor.
     *
     * @param position        The position of the Bow.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Bow(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, Integer.MAX_VALUE, hasDoubleDamage, new Sprite(new Texture(
                "images/bowitem.png"), SPRITE_SIZE, SPRITE_SIZE, 0));
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Bow(this.getPosition(), true);
    }

    @Override
    public boolean attack(Living living) {
        if (!handleCooldown()) {
            return false;
        }
        shoot();
        return true;
    }

    @Override
    public boolean attack(List<Living> livingList) {
        if (!handleCooldown()) {
            return false;
        }
        shoot();
        return true;
    }

    private void shoot() {
        SHOOT_SOUND.play();
        Game.getInstance().getMaze()
                .addParticle(new Arrow(getOwner().getDirection(), getPosition(), getDamage()));
    }

    @Override
    public void update() {
    }

}
