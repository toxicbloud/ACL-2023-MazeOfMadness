package com.game.controllers;

import com.engine.SoundManager;
import com.engine.SoundManager.SoundList;
import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Game;
import com.game.Player;
import com.game.monsters.Ghost;

/**
 * GhostController class.
 * This is the ghost controller class.
 */
public class GhostController extends Controller {
    /** Minimum distance to the player to stop chasing. */
    private static final float MIN_PLAYER_DISTANCE = 0.5f;
    /** The last time when the ghost attacks. */
    private long lastAttackTime;

    /**
     * GhostController constructor.
     * @param ghost The ghost to control.
     */
    public GhostController(Ghost ghost) {
        super(ghost);
    }

    @Override
    public void update() {
        Player player = Game.getInstance().getPlayer();
        Entity target = getTarget();
        Ghost ghost = (Ghost) target;

        if (player != null) {
            float distance = player.distance(target);

            if (distance > MIN_PLAYER_DISTANCE) {
                Vector3 delta = new Vector3(
                    player.getPosition().x - target.getPosition().x,
                    player.getPosition().y - target.getPosition().y,
                    0.0f
                );
                target.moveBy(
                    new Vector2(
                        delta.x,
                        delta.y
                    )
                    .normalize()
                    .mul(Time.getInstance().getDeltaTime() * Ghost.GHOST_SPEED
                ));
            } else if (Time.getInstance().getCurrentTime() - lastAttackTime > ghost.getWeapon().getCooldown()) {
                ghost.getWeapon().setPosition(ghost.getPosition());
                ghost.attack(player);
                if (player.distance(ghost) < (ghost.getWeapon().getRange())) {
                    SoundManager.getInstance().play(SoundList.ZOMBIE_ATTACK);
                }
            }
        }
    }
}
