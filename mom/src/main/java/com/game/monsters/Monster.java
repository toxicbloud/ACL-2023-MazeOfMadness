package com.game.monsters;

import com.engine.Sprite;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Living;
import com.game.Maze;
import com.game.Score;
import org.json.JSONObject;

/**
 * Monster class.
 * This is the monster class.
 */
public abstract class Monster extends Living {
    /**
     * Number of frames in the animation.
     */
    private static final int FRAMES_NUMBER = 40;
    /**
     * Ammount of points the player gets when killing a monster.
     */
    private static final int DEFAULT_POINTS = 20;

    /** The monster type. */
    private MonsterType type;

    /**
     * Monster constructor.
     *
     * @param sprite    The monster sprite to use.
     * @param t         The monster type.
     * @param health    The health of the monster.
     * @param maxHealth The max health of the monster.
     */
    protected Monster(Sprite sprite, MonsterType t, int health, int maxHealth) {
        super(sprite, new Vector3(0, 0, 0), new Vector3(1, 1, 1), health, maxHealth);
        this.type = t;
        this.setRandomAnimationShift();
    }

    /**
     * Monster constructor.
     * This is the constructor for the monster class.
     *
     * @param sprite    The monster sprite to use.
     * @param t         The type of the monster.
     * @param position  The position of the monster.
     * @param health    The health of the monster.
     * @param maxHealth The max health of the monster.
     */
    protected Monster(Sprite sprite, MonsterType t, Vector3 position, int health, int maxHealth) {
        super(sprite, position, new Vector3(1, 1, 1), health, maxHealth);
        this.type = t;
        this.setRandomAnimationShift();
    }

    /**
     * Monster constructor.
     * This is the constructor for the monster class.
     *
     * @param sprite    The monster sprite to use.
     * @param t         The type of the monster.
     * @param position  The position of the monster.
     * @param size      The size of the monster.
     * @param health    The health of the monster.
     * @param maxHealth The max health of the monster.
     */
    protected Monster(Sprite sprite, MonsterType t, Vector3 position, Vector3 size, int health, int maxHealth) {
        super(sprite, position, size, health, maxHealth);
        this.type = t;
        this.setRandomAnimationShift();
    }

    private void setRandomAnimationShift() {
        this.getSprite().setFrameCounter((int) (Math.random() * FRAMES_NUMBER));
    }

    @Override
    public void update() {
        super.update();
    }

    /**
     * Detect whether the player is in an enemy's field of vision.
     *
     * @param player The player.
     * @return Whether the player is in an enemy's field of vision.
     */
    public boolean findPlayer(Living player) {
        return isInFOV(this, player);
    }

    /**
     * Get the monster type.
     *
     * @return The monster type.
     */
    public MonsterType getType() {
        return type;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("type", this.type.toString());
        json.put("position", this.getPosition().toJSON());
        json.put("health", this.getHealth());

        return json;
    }

    /**
     * Get the points the player gets when killing the monster.
     *
     * @return The points the player gets when killing the monster.
     */
    public int getPoints() {
        return DEFAULT_POINTS;
    }

    /**
     * Affect the score.
     *
     * @param score The score object to accept.
     */
    public void affectScore(Score score) {
        score.addPoints(DEFAULT_POINTS);
    }

    @Override
    protected void remove() {
        Maze maze = Game.getInstance().getMaze();
        if (maze != null) {
            maze.removeMonster(this);
        }
    }
}
