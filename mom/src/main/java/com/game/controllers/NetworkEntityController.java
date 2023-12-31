package com.game.controllers;

import com.engine.utils.Vector2;
import com.engine.utils.Vector3;
import com.game.Entity;
import com.game.Level;
import com.game.Living;
import com.network.NetworkDialogs;
import com.network.NetworkManagerTCP;
import org.json.JSONObject;

/**
 * Network entity controller.
 * (to see other entity in map)
 */
public class NetworkEntityController extends Controller {
    /**
     * Constructor.
     * @param entity entity.
     * @param network Network manager.
     */
    public NetworkEntityController(Entity entity, NetworkManagerTCP network) {
        super(entity);
        System.out.println("NetworkEntityController: " + entity.getId());

        network.when((data, infos) -> {
            return data.length >= 1 + 2
                && (data[0] == NetworkDialogs.MAZE_UPD || data[0] == NetworkDialogs.MAZE_REM)
                && NetworkDialogs.getIntValue(data, 1) == entity.getId();
        }, (data, infos) -> {
            if (data[0] == NetworkDialogs.MAZE_REM) {
                System.out.println("NetworkEntityController: " + entity.getId() + " removed");
                getTarget().destroy();
                return true;
            }

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
