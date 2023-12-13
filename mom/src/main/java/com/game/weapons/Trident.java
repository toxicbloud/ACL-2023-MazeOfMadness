package com.game.weapons;

import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.ItemType;
import com.game.Living;
import com.game.Living.Direction;

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
     * The direction of the trident.
     */
    private Direction direction;
    /**
     * The trident sprite when it is not in player's hand.
     */
    private Sprite item;

    /**
     * Trident constructor.
     */
    public Trident() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE, ItemType.WEAPON_TRIDENT);
        this.item = new Sprite(new Texture("images/trident.png"), SPRITE_SIZE, SPRITE_SIZE, 0);
    }

    /**
     * Trident full constructor.
     *
     * @param position The position of the Trident.
     */
    public Trident(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, ItemType.WEAPON_TRIDENT);
        this.item = new Sprite(new Texture("images/trident.png"), SPRITE_SIZE, SPRITE_SIZE, 0);
    }

    /**
     * Trident full constructor.
     *
     * @param position        The position of the Trident.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Trident(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, ItemType.WEAPON_TRIDENT);
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
        this.direction = getOwner().getDirection();
        this.getOwner().setWeapon(null);
        this.isLaunched = false;
        Game.getInstance().getMaze()
                .addParticle(new com.game.particles.Trident(getPosition(),
                        this.getOwner().getDirection(),
                        this));
    }

    @Override
    public void render() {
        if (!isLaunched) {
            super.render();
        }
    }

    @Override
    public void update() {
    }

}
