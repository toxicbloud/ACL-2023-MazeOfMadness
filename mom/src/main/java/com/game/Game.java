package com.game;

/**
 * Game class.
 * This is the game class.
 */
public final class Game {
    /** The game instance. */
    private static Game instance;
    /** The game score. */
    private Score score;
    /** The game player entity. */
    private Player player;
    /** The current game maze. */
    private Maze maze;

    /**
     * Game constructor.
     * Initialize the game.
     */
    public Game() {
        this.score = new Score();
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
        this();
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
     * Get the game score.
     *
     * @return The game score.
     */
    public Score getScore() {
        return score;
    }
}
