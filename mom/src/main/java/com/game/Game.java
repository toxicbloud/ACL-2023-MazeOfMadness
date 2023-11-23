package com.game;

import com.badlogic.gdx.files.FileHandle;
import com.engine.Window;
import com.game.generators.MazeFactory;
import com.game.generators.MonsterSpawner;
import com.game.generators.PotionSpawner;
import com.game.generators.TrapSpawner;
import com.renderer.GameScene;

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

    /**
     * Create and load a new game.
     * ensure that the player and the score are new.
     */
    public void loadNew() {
        setMaze(TrapSpawner.spawnTraps(MazeFactory.createMaze()));
        setPlayer(new Player(maze.getSpawnPoint()));
        score = new Score();
        Window.getInstance().setScene(new GameScene());
        MonsterSpawner.spawnMonsters(maze);
        PotionSpawner.spawnPotion(maze);
    }

    /**
     * Create and load the next maze.
     * keep the same player.
     */
    public void loadNext() {
        var newMaze = TrapSpawner.spawnTraps(MazeFactory.createMaze());
        Game.getInstance().setMaze(newMaze);
        Game.getInstance().getPlayer().setPosition(newMaze.getSpawnPoint());
        Window.getInstance().setScene(new GameScene());
        MonsterSpawner.spawnMonsters(newMaze);
        PotionSpawner.spawnPotion(newMaze);
    }

    /**
     * Load a game from a file.
     *
     * @param file The file to load from.
     */
    public void loadFromFile(FileHandle file) {
        loadFromLevel(LevelLoader.load(file));
    }

    /**
     * Load a game from a file.
     *
     * @param path The path to the file to load from.
     */
    public void loadFromFile(String path) {
        loadFromFile(new FileHandle(path));
    }

    /**
     * Load a game from a level.
     *
     * @param level The level to load from.
     */
    public void loadFromLevel(Level level) {
        setMaze(level.getMaze());
        setPlayer(new Player(level.getPlayerData().getPosition()));
        score = new Score();
        Window.getInstance().setScene(new GameScene());
    }
}
