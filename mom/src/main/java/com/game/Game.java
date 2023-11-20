package com.game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Game class.
 * This is the game class.
 */
public final class Game {
    /** The game instance. */
    private static Game instance;
    /** The game score. */
    private int points;
    /** The game player entity. */
    private Player player;
    /** The current game maze. */
    private Maze maze;
    /**
     * The property change support used to notify the view of changes.
     */
    private PropertyChangeSupport support;

    /**
     * Game constructor.
     * Initialize the game.
     */
    public Game() {
        this.points = 0;
        support = new PropertyChangeSupport(this);
    }

    /**
     * Game constructor.
     * Initialize the game.
     *
     * @param p The game player.
     * @param m The game maze.
     */
    public Game(Player p, Maze m) {
        this();
        this.player = p;
        this.maze = m;
    }

    /**
     * Game constructor.
     * Initialize the game.
     *
     * @param m The game maze.
     */
    public Game(Maze m) {
        this.points = 0;
        this.maze = m;
    }

    /**
     * Get the game instance.
     *
     * @return The game instance.
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Get the game points.
     *
     * @return The game points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Add points to the game score.
     *
     * @param pts The points to add.
     */
    public void addPoints(int pts) {
        support.firePropertyChange("points", this.points, this.points + pts);
        this.points += pts;
    }

    /**
     * Remove points from the game score.
     *
     * @param pts The points to remove.
     */
    public void removePoints(int pts) {
        support.firePropertyChange("points", this.points, this.points - pts);
        this.points -= pts;
    }

    /**
     * Reset the game score.
     */
    public void resetScore() {
        this.points = 0;
    }

    /**
     * Get the game maze.
     *
     * @return The game maze.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Get the game player.
     *
     * @return The game player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the game player.
     *
     * @param player The game player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Set the game maze.
     *
     * @param maze The game maze.
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
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
