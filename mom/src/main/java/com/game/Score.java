package com.game;

import com.game.monsters.Monster;
import com.game.monsters.MonsterType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * Score class.
 * stores points of the player and monsters killed.
 */
public class Score {
    /**
     * Score constructor.
     */
    private int points;
    /**
     * The property change support used to notify the view of changes.
     */
    private PropertyChangeSupport support;
    /**
     * Store kills by monster type.
     */
    private Map<MonsterType, Integer> kills;

    /**
     * Score constructor.
     */
    public Score() {
        this.points = 0;
        this.support = new PropertyChangeSupport(this);
        this.kills = new HashMap<>();
    }

    /**
     * Handle kill.
     *
     * @param monster The monster which was killed by the player.
     */
    public void handleKill(Monster monster) {
        monster.accept(this);
    }

    /**
     * Add points.
     *
     * @param pts The points to add.
     */
    public void addPoints(int pts) {
        this.points += pts;
        support.firePropertyChange("points", null, this.points);
    }

    /**
     * Get the score points.
     *
     * @return The score points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set the score points.
     *
     * @param type The monster type.
     * @return How many monsters of this type were killed.
     */
    public int getKillCount(MonsterType type) {
        Integer count = kills.get(type);
        return count == null ? 0 : count;
    }

    /**
     * Add a kill.
     *
     * @param type The monster type.
     */
    public void addKill(MonsterType type) {
        if (!kills.containsKey(type)) {
            kills.put(type, 1);
        } else {
            kills.put(type, kills.get(type) + 1);
        }
    }

    /**
     * Add a property change listener.
     *
     * @param propertyName The property name.
     * @param listener     The listener.
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }
}
