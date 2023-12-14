package com.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;

/**
 * SoundManager class.
 * It is the manager of all sounds in the game.
 */
public final class SoundManager {
    /** Set the player's damage sound volume. */
    private static final float PLAYER_DAMAGE_VOLUME = 0.3f;
    /** Set the zombie's attack sound volume. */
    private static final float ZOMBIE_ATTACK_VOLUME = 0.3f;
    /** Set the zombie's damage sound volume. */
    private static final float ZOMBIE_DAMAGE_VOLUME = 0.25f;
    /** Set the zombie's ambient sound volume. */
    private static final float ZOMBIE_AMBIENT_VOLUME = 0.07f;
    /** Set the ghost's attack sound volume. */
    private static final float GHOST_ATTACK_VOLUME = 0.25f;
    /** Set the ghost's damage sound volume. */
    private static final float GHOST_DAMAGE_VOLUME = 0.2f;
    /** Set the potion sound volume. */
    private static final float POTION_VOLUME = 0.25f;
    /** Set the win sound volume. */
    private static final float WIN_VOLUME = 0.25f;
    /** Set the fail sound volume. */
    private static final float FAIL_VOLUME = 0.4f;
    /** SoundManager instanced class. */
    private static SoundManager instance;
    /** Player attack sound. */
    private Sound playerAttackSound;
    /** Player damage sound. */
    private Sound playerDamageSound;
    /** Zombie attack sound. */
    private Sound zombieAttackSound;
    /** Zombie damage sound. */
    private Sound zombieDamageSound;
    /** Zombie ambient sound. */
    private Sound zombieAmbientSound;
    /** Ghost attack sound. */
    private Sound ghostAttackSound;
    /** Ghost damage sound. */
    private Sound ghostDamageSound;
    /** Potion sound. */
    private Sound potionSound;
    /** Sound when walking on the floor. */
    private Sound footStepGround;
    /** Sound when walking in lava. */
    private Sound footStepLava;
    /** Sound when walking in water. */
    private Sound footStepWater;
    /** The button click sound. */
    private Sound buttonClick;
    /** Fail sound. */
    private Sound failSound;
    /** Win sound. */
    private Sound winSound;

    /** Sound enum. */
    public enum SoundList {
        /** Player attack sound. */
        PLAYER_ATTACK,
        /** Player damage sound. */
        PLAYER_DAMAGE,
        /** Zombie attack sound. */
        ZOMBIE_ATTACK,
        /** Zombie damage sound. */
        ZOMBIE_DAMAGE,
        /** Zombie ambient sound. */
        ZOMBIE_AMBIENT,
        /** Ghost attack sound. */
        GHOST_ATTACK,
        /** Ghost damage sound. */
        GHOST_DAMAGE,
        /** Potion sound. */
        POTION,
        /** Sound when walking on the floor. */
        FOOTSTEP_GROUND,
        /** Sound when walking in lava. */
        FOOTSTEP_LAVA,
        /** Sound when walking in water. */
        FOOTSTEP_WATER,
        /** The button click sound. */
        BUTTON_CLICK,
        /** The win sound. */
        WIN,
        /** The fail sound. */
        FAIL
    }

    private SoundManager() {
    }

    /**
     * Get the soundmanager instance.
     *
     * @return The soundmanager instance.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
            instance.init();
        }
        return instance;
    }

    private void init() {
        playerAttackSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/playerAttack.mp3"));
        playerDamageSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/playerDamage.mp3"));
        zombieAttackSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/zombieAttack.mp3"));
        zombieDamageSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/zombieDamage.mp3"));
        zombieAmbientSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/zombieAmbient.mp3"));
        ghostAttackSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/ghostAttack.mp3"));
        ghostDamageSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/ghostDamage.mp3"));
        potionSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/potion.mp3"));
        footStepGround = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/footstepGround.mp3"));
        footStepLava = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/potion.mp3"));
        footStepWater = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/footstepWater.mp3"));
        buttonClick = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        failSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/fail.mp3"));
        winSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));
    }

    /**
     * Play the right sound.
     *
     * @param soundList Enumerating sounds.
     */
    public void play(SoundList soundList) {
        switch (soundList) {
            case PLAYER_ATTACK:
                playerAttackSound.play();
                break;
            case PLAYER_DAMAGE:
                long playerDamage = playerDamageSound.play();
                playerDamageSound.setVolume(playerDamage, PLAYER_DAMAGE_VOLUME);
                break;
            case ZOMBIE_ATTACK:
                long zombieAttack = zombieAttackSound.play();
                zombieAttackSound.setVolume(zombieAttack, ZOMBIE_ATTACK_VOLUME);
                break;
            case ZOMBIE_DAMAGE:
                long zombieDamage = zombieDamageSound.play();
                zombieDamageSound.setVolume(zombieDamage, ZOMBIE_DAMAGE_VOLUME);
                break;
            case ZOMBIE_AMBIENT:
                long zombieAmbient = zombieAmbientSound.loop();
                zombieAmbientSound.setVolume(zombieAmbient, ZOMBIE_AMBIENT_VOLUME);
                break;
            case GHOST_ATTACK:
                long ghostAttack = ghostAttackSound.play();
                ghostAttackSound.setVolume(ghostAttack, GHOST_ATTACK_VOLUME);
                break;
            case GHOST_DAMAGE:
                long ghostDamage = ghostDamageSound.play();
                ghostDamageSound.setVolume(ghostDamage, GHOST_DAMAGE_VOLUME);
                break;
            case POTION:
                long potion = potionSound.play();
                potionSound.setVolume(potion, POTION_VOLUME);
                break;
            case FOOTSTEP_GROUND:
                footStepGround.play();
                break;
            case FOOTSTEP_LAVA:
                footStepLava.play();
                break;
            case FOOTSTEP_WATER:
                footStepWater.play();
                break;
            case BUTTON_CLICK:
                buttonClick.play();
                break;
            case WIN:
                long win = winSound.play();
                winSound.setVolume(win, WIN_VOLUME);
                break;
            case FAIL:
                long fail = failSound.play();
                failSound.setVolume(fail, FAIL_VOLUME);
                break;
            default:
                break;
        }
    }

    /**
     * Stop the right sound.
     *
     * @param soundList Enumerating sounds.
     */
    public void stop(SoundList soundList) {
        switch (soundList) {
            case PLAYER_ATTACK:
                playerAttackSound.stop();
                break;
            case PLAYER_DAMAGE:
                playerDamageSound.stop();
                break;
            case ZOMBIE_ATTACK:
                zombieAttackSound.stop();
                break;
            case ZOMBIE_DAMAGE:
                zombieDamageSound.stop();
                break;
            case ZOMBIE_AMBIENT:
                zombieAmbientSound.stop();
                break;
            case FOOTSTEP_GROUND:
                footStepGround.stop();
                break;
            case FOOTSTEP_LAVA:
                footStepLava.stop();
                break;
            case FOOTSTEP_WATER:
                footStepWater.stop();
                break;
            default:
                break;
        }
    }
}
