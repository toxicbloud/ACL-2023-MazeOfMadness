package com.game;

import com.engine.utils.Vector3;
import org.json.JSONObject;

/**
 * Class use to serialize player data.
 */
public class PlayerData {
    /**
     * The player's health.
     */
    private int health;
    /**
     * The player's maximum health.
     */
    private int maxHealth;
    /**
     * The player's spawn position.
     */
    private Vector3 position;

    /**
     * Construct a new PlayerData object from a JSON object.
     *
     * @param json The JSON object to construct the PlayerData object from.
     */
    public PlayerData(JSONObject json) {
        this.health = json.getInt("health");
        this.maxHealth = json.getInt("maxHealth");
        JSONObject pos = (JSONObject) json.get("position");
        this.position = new Vector3(pos.getFloat("x"), pos.getFloat("y"), pos.getFloat("z"));
    }

    /**
     * Construct a new PlayerData object from values,
     * used when serializing the PlayerData object to JSON.
     *
     * @param health    The player's health.
     * @param maxHealth The player's maximum health.
     * @param position  The player's spawn position.
     */
    public PlayerData(int health, int maxHealth, Vector3 position) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.position = position;
    }

    /**
     * Get the player's health.
     *
     * @return The player's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the player's maximum health.
     *
     * @return The player's maximum health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the player's spawn position.
     *
     * @return The player's spawn position.
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Set the player's health.
     *
     * @param health The player's health.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Set the player's maximum health.
     *
     * @param maxHealth The player's maximum health.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Set the player's spawn position.
     *
     * @param position The player's spawn position.
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Convert the PlayerData object to a JSON object.
     *
     * @return The JSON object representing the PlayerData object.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("health", this.health);
        json.put("maxHealth", this.maxHealth);
        JSONObject pos = new JSONObject();
        pos.put("x", this.position.getX());
        pos.put("y", this.position.getY());
        pos.put("z", this.position.getZ());
        json.put("position", pos);
        return json;
    }

}
