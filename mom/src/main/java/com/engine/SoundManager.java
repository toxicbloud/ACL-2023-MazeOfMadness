package com.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;

/**
 * SoundManager class.
 * It is the manager of all sounds in the game.
 */
public final class SoundManager {
    /** SoundManager instanced class. */
    private static SoundManager instance;
    /** Attack sound. */
    private Sound attackSound;
    /** Potion sound. */
    private Sound potionSound;

    /** Sound enum. */
    public enum SoundList {
        /** Attack sound. */
        ATTACK,
        /** Sound of drinking a potion. */
        POTION
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
        attackSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        potionSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/potion.mp3"));
    }

    /**
     * Play the right sound.
     *
     * @param soundList Enumerating sounds.
     */
    public void play(SoundList soundList) {
        switch (soundList) {
            case ATTACK:
                attackSound.play();
                break;
            case POTION:
                potionSound.play();
                break;
            default:
                break;
        }
    }
}
