package com.game;

import com.game.monsters.MonsterType;
import org.json.JSONObject;

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
     * Score constructor.
     * @param obj The score object.
     */
    public Score(Score obj) {
        this.points = obj.points;
        this.support = new PropertyChangeSupport(this);
        this.kills = new HashMap<>(obj.kills);
    }

    /**
     * Score constructor.
     * @param json The JSON object.
     */
    public Score(JSONObject json) {
        this.points = json.getInt("points");
        this.support = new PropertyChangeSupport(this);
        this.kills = new HashMap<>();
        JSONObject killsJSON = json.getJSONObject("kills");
        for (String key : killsJSON.keySet()) {
            kills.put(MonsterType.valueOf(key), killsJSON.getInt(key));
        }
    }

    /**
     * Apply the score.
     * @param other The other score.
     */
    public void apply(Score other) {
        this.points = other.points;
        this.kills = new HashMap<>(other.kills);
        support.firePropertyChange("points", null, this.points);
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
        support.firePropertyChange("points", null, this.points);
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Score) {
            Score other = (Score) obj;
            return other.points == this.points;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return points;
    }

    /**
     * Convert the score to a JSON object.
     * @return The JSON object.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("points", points);
        JSONObject killsJSON = new JSONObject();
        for (Map.Entry<MonsterType, Integer> entry : kills.entrySet()) {
            killsJSON.put(entry.getKey().name(), entry.getValue());
        }
        json.put("kills", killsJSON);
        return json;
    }
}
