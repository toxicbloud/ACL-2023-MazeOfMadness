package com.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.engine.utils.Time;
import com.engine.utils.Vector3;
import com.game.ItemType;
import com.game.Living;

import java.util.List;

/**
 * Bomb class.
 */
public class Bomb extends Weapon {
    /** Bomb damage amount. */
    private static final int DAMAGE = 30;
    /** Bomb cooldown. */
    private static final int ATTACK_COOLDOWN = 300;
    /** Bomb range. */
    private static final int RANGE = 1;
    /** Bomb explosion delay. */
    private static final long EXPLOSION_DELAY = 5000;
    /**
     * Sound played when the bomb is thrown and is waiting to explode.
     */
    private static final Sound WAITING_SOUND = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/tictac.mp3"));

    /**
     * Sound played when the bomb explodes.
     */
    private static final Sound EXPLOSION_SOUND = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/boom.mp3"));
    /**
     * If the Bomb is launched.
     */
    private boolean isThrown;
    /** The time when the bomb was thrown. */
    private long launchTime;

    /**
     * Bomb constructor.
     */
    public Bomb() {
        super(DAMAGE, ATTACK_COOLDOWN, RANGE, ItemType.WEAPON_BOMB);
    }

    /**
     * Bomb full constructor.
     *
     * @param position The position of the Bomb.
     */
    public Bomb(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, ItemType.WEAPON_BOMB);
    }

    /**
     * Bomb full constructor.
     *
     * @param position        The position of the Bomb.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Bomb(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, ItemType.WEAPON_BOMB);
    }

    @Override
    public Weapon createDoubleDamageWeapon() {
        return new Bomb(this.getPosition(), true);
    }

    @Override
    public boolean attack(Living living) {
        if (!isThrown) {
            launch();
        }
        return true;
    }

    @Override
    public boolean attack(List<Living> livingList) {
        // return super.attack(livingList);
        if (!isThrown) {
            launch();
        }
        return true;
    }

    private void launch() {
        System.out.println("LAUNCH");
        WAITING_SOUND.play();
        this.isThrown = true;
        this.setPickable(false);
        this.launchTime = Time.getInstance().getCurrentTime();
    }

    @Override
    public void render() {
        if (isThrown) {
            super.render();
        }
    }

    @Override
    public void update() {
        if (isThrown && Time.getInstance().getCurrentTime() - launchTime > EXPLOSION_DELAY) {

            System.out.println("BOOM");
            Living owner = getOwner();

            if (this.getPosition().dst(owner.getPosition()) < 1) {
                owner.takeDamage(this.getDamage());
            }
            owner.setWeapon(null);
            EXPLOSION_SOUND.play();
        }
    }

    /**
     * Set the position of the bomb only if it is not thrown.
     *
     * @param position The position to set.
     */
    @Override
    public void setPosition(Vector3 position) {
        if (isThrown) {
            return;
        }
        super.setPosition(position);
    }

}