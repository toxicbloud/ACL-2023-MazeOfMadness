package com.game.weapons;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Living;

import java.util.List;

/**
 * Trident class.
 */
public class Trident extends Weapon {
    /** Trident damage amount. */
    private static final int DAMAGE = 30;
    /** Trident cooldown. */
    private static final int ATTACK_COOLDOWN = 300;
    /** Trident range. */
    private static final int RANGE = 1;
    /**
     * If the trident is launched.
     */
    private boolean isLaunched;

    /**
     * Trident constructor.
     */
    public Trident() {
        super(new Vector3(), DAMAGE, ATTACK_COOLDOWN, RANGE, false, new Sprite(new Texture(
                "images/tridentitem.png"), SPRITE_SIZE, SPRITE_SIZE, 0));
    }

    /**
     * Trident full constructor.
     *
     * @param position The position of the Trident.
     */
    public Trident(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, new Sprite(new Texture(
                "images/tridentitem.png"), SPRITE_SIZE, SPRITE_SIZE, 0));
    }

    /**
     * Trident full constructor.
     *
     * @param position        The position of the Trident.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Trident(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, new Sprite(new Texture(
                "images/tridentitem.png"), SPRITE_SIZE, SPRITE_SIZE, 0));
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Trident(this.getPosition(), true);
    }

    @Override
    public boolean attack(Living living) {
        launch();
        getOwner().setWeapon(null);
        return true;
    }

    @Override
    public boolean attack(List<Living> livingList) {
        launch();
        getOwner().setWeapon(null);
        return true;
    }

    private void launch() {
        this.isLaunched = true;
        this.getOwner().setWeapon(null);
        this.isLaunched = false;
        Game.getInstance().getMaze()
                .addParticle(new com.game.particles.Trident(getPosition(),
                        this.getOwner().getDirection()));
    }

    @Override
    public void update() {
    }

}
