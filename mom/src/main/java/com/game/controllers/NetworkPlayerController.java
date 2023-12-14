package com.game.controllers;

import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Level;
import com.game.Living;
import com.game.Player;
import com.network.NetworkDialogs;
import com.network.NetworkManagerTCP;
import org.json.JSONObject;

/**
 * Network player controller.
 * (used to control other players entities for network games)
 */
public class NetworkPlayerController extends PlayerController {

    /**
     * Constructor.
     * @param player Player.
     * @param id Player id.
     * @param network Network manager.
     */
    public NetworkPlayerController(Player player, int id, NetworkManagerTCP network) {
        super(player);

        network.when((data, infos) -> {
            return data.length >= 1 + 2
                && data[0] == NetworkDialogs.PLR_UPD
                && NetworkDialogs.getIntValue(data, 1) == id;
        }, (data, infos) -> {
            JSONObject json = new JSONObject(NetworkDialogs.getStringValue(data, 1 + 2 + 1));
            if (Level.verifyJSON(json, "position")) {
                Vector3 pos = getTarget().getPosition();
                Vector3 pos2 = Level.parsePosition(json.getJSONObject("position"));
                getTarget().moveBy(new Vector2(pos2.x - pos.x, pos2.y - pos.y));
            }
            if (Level.verifyJSON(json, "health") && getTarget() instanceof Living) {
                ((Living) getTarget()).setHealth(json.getInt("health"));
            }
            return false;
        });
    }

    @Override
    public void update() {

    }
}
