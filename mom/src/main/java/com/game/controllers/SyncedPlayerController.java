package com.game.controllers;

import com.engine.utils.Time;
import com.engine.utils.Vector2;
import com.game.Entity;
import com.game.Living;
import com.game.Player;
import com.game.weapons.Weapon;
import com.network.NetworkDialogs;
import com.network.NetworkManagerTCP;

/**
 * Network player controller.
 * (used to control other players entities for network games)
 */
public class SyncedPlayerController extends PlayerController {

    /** Player id. */
    private int id;

    /** Network manager. */
    private NetworkManagerTCP network;

    /**
     * Constructor.
     * @param player Player.
     * @param id Player id.
     * @param network Network manager.
     */
    public SyncedPlayerController(Player player, int id, NetworkManagerTCP network) {
        super(player);
        this.id = id;
        this.network = network;
    }

    @Override
    public void update() {
        Entity target = getTarget();
        if (target == null) {
            return;
        }
        Player player = (Player) target;
        Weapon weapon = player.getWeapon();
        if (weapon == null) {
            return;
        }

        weapon.setPosition(target.getPosition());
        Vector2 normalized = getDirection().normalize();
        target.moveBy(
                new Vector2(normalized.x, normalized.y)
                        .mul(Time.getInstance().getDeltaTime() * ((Living) target).getSpeed()));

        if (isAttacking()) {
            byte[] data = new byte[]{NetworkDialogs.PLR_ATK, 0, 0};
            NetworkDialogs.encodeIntValue(id, data, 1);
            network.sendData(data);
        }

        if (isInteracting()) {
            byte[] data = new byte[]{NetworkDialogs.PLR_ITR, 0, 0};
            NetworkDialogs.encodeIntValue(id, data, 1);
            network.sendData(data);
        }
    }
}
