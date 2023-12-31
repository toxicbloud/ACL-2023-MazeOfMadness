package com.game.weapons;

import com.engine.SoundManager;
import com.engine.SoundManager.SoundList;
import com.engine.Sprite;
import com.engine.Texture;
import com.engine.utils.Time;
import com.engine.utils.Vector3;
import com.game.Game;
import com.game.Living;
import com.game.monsters.Monster;
import com.game.particles.BombThrown;
import com.game.particles.Fire;
import com.game.tiles.Tile;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Bomb class.
 */
public class Bomb extends Weapon {
    /**
     *
     */
    private static final int EXPLOSION_DEPTH = 5;
    /** Bomb damage amount. */
    private static final int DAMAGE = 200;
    /** Bomb cooldown. */
    private static final int ATTACK_COOLDOWN = 300;
    /** Bomb range. */
    private static final int RANGE = 1;
    /** Bomb explosion delay. */
    private static final long EXPLOSION_DELAY = 5000;
    /**
     * Bomb sprite.
     */
    private static final Sprite SPRITE = new Sprite(new Texture("images/bombitem.png"), SPRITE_SIZE, SPRITE_SIZE,
            0);

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
        super(new Vector3(), DAMAGE, ATTACK_COOLDOWN, RANGE, false, SPRITE);
    }

    /**
     * Bomb full constructor.
     *
     * @param position The position of the Bomb.
     */
    public Bomb(Vector3 position) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, false, SPRITE);
    }

    /**
     * Bomb full constructor.
     *
     * @param position        The position of the Bomb.
     * @param hasDoubleDamage If the weapon's damage have been doubled.
     */
    public Bomb(Vector3 position, boolean hasDoubleDamage) {
        super(position, DAMAGE, ATTACK_COOLDOWN, RANGE, hasDoubleDamage, SPRITE);
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
        if (!isThrown) {
            launch();
        }
        return true;
    }

    private void launch() {
        SoundManager.getInstance().play(SoundList.BOMB_WAITING);
        this.isThrown = true;
        this.launchTime = Time.getInstance().getCurrentTime();
        Game.getInstance().getMaze().addParticle(new BombThrown(this.getPosition()));
    }

    @Override
    public void update() {
        if (isThrown && Time.getInstance().getCurrentTime() - launchTime > EXPLOSION_DELAY) {
            Living owner = getOwner();
            int bombX = Math.round(this.getPosition().x);
            int bombY = Math.round(this.getPosition().y);
            int bombZ = Math.round(this.getPosition().z);

            Queue<Object[]> queue = new LinkedList<>();
            Set<Tile> visited = new HashSet<>();
            Tile start = Game.getInstance().getMaze().getTile(bombX, bombY, bombZ);
            queue.add(new Object[] {start, 0 });
            visited.add(start);

            while (!queue.isEmpty()) {
                Object[] currentPair = queue.poll();
                Tile current = (Tile) currentPair[0];
                int depth = (Integer) currentPair[1];
                if (current != null && depth <= EXPLOSION_DEPTH) {
                    Game.getInstance().getMaze().addParticle(new Fire(current.getPosition()));
                    for (Tile tile : current.getNeighbours()) {
                        if (!visited.contains(tile) && !tile.isSolid()) {
                            queue.add(new Object[] {tile, depth + 1 });
                            visited.add(tile);
                        }
                    }
                }
            }
            for (Monster monster : Game.getInstance().getMaze().getMonsters()) {
                Vector3 pos = monster.getPosition();
                Tile tile = Game.getInstance().getMaze().getTile(Math.round(pos.x),
                        Math.round(pos.y), Math.round(pos.z));
                if (visited.contains(tile)) {
                    monster.takeDamage(this.getDamage());
                }
            }
            Tile tile = Game.getInstance().getMaze().getTile(owner.getPosition());
            if (tile != null && visited.contains(tile)) {
                float distance = owner.getPosition().dst(this.getPosition());
                // The closer the player is to the bomb, the more damage he takes.
                owner.takeDamage((int) (this.getDamage() * (1 - distance / EXPLOSION_DEPTH)));
            }
            owner.setWeapon(null);
            SoundManager.getInstance().play(SoundList.BOMB_EXPLOSION);
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
