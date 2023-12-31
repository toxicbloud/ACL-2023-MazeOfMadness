package com.network;

import com.game.Entity;
import com.game.Level;
import com.game.Player;
import com.game.Score;
import com.game.WorldItem;
import com.game.monsters.Monster;
import com.game.tiles.Tile;
import org.json.JSONObject;

/**
 * Network dialogs.
 * (server and client)
 */
public final class NetworkDialogs {
    /** Byte number shift multiplier. */
    public static final int BYTE_SHIFT_MULT = 256;
    /** 0xFF value (255). */
    public static final int BYTE_FF = 0xFF;
    /** HUNDRED. */
    public static final int HUNDRED = 100;

    /** END_PQT code. */
    public static final byte END_PQT = -1;

    /** REGISTER_RQT code. */
    public static final byte REGISTER_RQT = 11;
    /** REGISTER_RSP code. */
    public static final byte REGISTER_RSP = 12;

    /** MAZE_RQT code. */
    public static final byte MAZE_RQT = 21;
    /** MAZE_LGH code. */
    public static final byte MAZE_LGH = 22;
    /** MAZE_ADD code. */
    public static final byte MAZE_ADD = 23;
    /** MAZE_UPT code. */
    public static final byte MAZE_UPD = 24;
    /** MAZE_REM code. */
    public static final byte MAZE_REM = 25;

    /** PLR_UPD code. */
    public static final byte PLR_UPD = 26;
    /** PLR_ATK code. */
    public static final byte PLR_ATK = 27;
    /** PLR_ITR code. */
    public static final byte PLR_ITR = 28;

    /** ENTITY_TLE code. */
    public static final byte ENTITY_TLE = 31;
    /** ENTITY_MST code. */
    public static final byte ENTITY_MST = 32;
    /** ENTITY_ITM code. */
    public static final byte ENTITY_ITM = 33;
    /** ENTITY_PLR code. */
    public static final byte ENTITY_PLR = 34;

    /** GAME_STR code. */
    public static final byte GAME_STR = 42;
    /** GAME_RDY code. */
    public static final byte GAME_RDY = 43;
    /** GAME_SCR code. */
    public static final byte GAME_SCR = 44;
    /** GAME_NXT code. */
    public static final byte GAME_NXT = 45;
    /** GAME_OK code. */
    public static final byte GAME_OK = 46;
    /** GAME_END code. */
    public static final byte GAME_END = 47;

    /** Hidden constructor. */
    private NetworkDialogs() {}

    /**
     * Get the integer value of a byte array.
     * @param data Data array.
     * @param offset Offset.
     * @return Integer value.
     */
    public static int getIntValue(byte[] data, int offset) {
        return (data[offset] & BYTE_FF) * BYTE_SHIFT_MULT
                + (data[offset + 1] & BYTE_FF);
    }

    /**
     * Encode an integer value into a byte array.
     * @param value Integer value.
     * @param data Data array.
     * @param offset Offset.
     */
    public static void encodeIntValue(int value, byte[] data, int offset) {
        data[0 + offset] = (byte) (value / BYTE_SHIFT_MULT);
        data[1 + offset] = (byte) (value % BYTE_SHIFT_MULT);
    }

    /**
     * Get the string value of a byte array.
     * @param data Data array.
     * @param offset Offset.
     * @return String value.
     */
    public static String getStringValue(byte[] data, int offset) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - offset; i++) {
            sb.append((char) data[offset + i]);
        }
        return sb.toString();
    }

    /**
     * Encode a string value into a byte array.
     * @param value String value.
     * @param data Data array.
     * @param offset Offset.
     */
    public static void encodeStringValue(String value, byte[] data, int offset) {
        for (int i = 0; i < value.length(); i++) {
            data[offset + i] = (byte) value.charAt(i);
        }
    }

    /**
     * Encode an Entity value into a byte array.
     * @param e Entity.
     * @param offset Offset.
     * @return Encoded Entity value.
     */
    public static byte[] encodeEntityValue(Entity e, int offset) {
        String json = e.toJSON().toString();
        byte[] data = new byte[json.length() + 1 + offset];
        if (e instanceof Tile) {
            data[offset] = ENTITY_TLE;
        } else if (e instanceof Monster) {
            data[offset] = ENTITY_MST;
        } else if (e instanceof WorldItem) {
            data[offset] = ENTITY_ITM;
        } else if (e instanceof Player) {
            data[offset] = ENTITY_PLR;
        } else {
            return null;
        }
        encodeStringValue(json, data, 1 + offset);
        return data;
    }

    /**
     * Encode a Monster value into a byte array.
     * @param m Monster.
     * @param offset Offset.
     * @return Encoded Monster value.
     */
    public static byte[] encodeMonsterValue(Monster m, int offset) {
        String json = m.toJSON().toString();
        byte[] data = new byte[json.length() + 1 + offset];
        data[offset] = ENTITY_MST;
        encodeStringValue(json, data, 1 + offset);
        return data;
    }

    /**
     * Encode an Item value into a byte array.
     * @param i Item.
     * @param offset Offset.
     * @return Encoded Item value.
     */
    public static byte[] encodeItemValue(WorldItem i, int offset) {
        String json = i.toJSON().toString();
        byte[] data = new byte[json.length() + 1 + offset];
        data[offset] = ENTITY_ITM;
        encodeStringValue(json, data, 1 + offset);
        return data;
    }

    /**
     * Encode a Player value into a byte array.
     * @param p Player.
     * @param offset Offset.
     * @return Encoded Player value.
     */
    public static byte[] encodePlayerValue(Player p, int offset) {
        String json = p.toJSON().toString();
        byte[] data = new byte[json.length() + 1 + offset];
        data[offset] = ENTITY_PLR;
        encodeStringValue(json, data, 1 + offset);
        return data;
    }

    /**
     * Encode a Tile value into a byte array.
     * @param t Tile.
     * @param offset Offset.
     * @return Encoded Tile value.
     */
    public static byte[] encodeTileValue(Tile t, int offset) {
        String json = t.toJSON().toString();
        byte[] data = new byte[json.length() + 1 + offset];
        data[offset] = ENTITY_TLE;
        encodeStringValue(json, data, 1 + offset);
        return data;
    }

    /**
     * Encode a Score value into a byte array.
     * @param score Score.
     * @param offset Offset.
     * @return Encoded Score value.
     */
    public static byte[] encodeScoreValue(Score score, int offset) {
        String json = score.toJSON().toString();
        byte[] data = new byte[json.length() + offset];
        encodeStringValue(json, data, offset);
        return data;
    }

    /**
     * Get the encoded Score value from the data array.
     * @param data
     * @param offset
     * @return Encoded Score value.
     */
    public static Score getScoreFromData(byte[] data, int offset) {
        return new Score(new JSONObject(getStringValue(data, offset)));
    }

    /**
     * Get the encoded Monster from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Monster.
     */
    public static Monster getMonsterFromData(byte[] data, int offset) {
        return Level.parseMonster(new JSONObject(getStringValue(data, offset)));
    }

    /**
     * Get the encoded Item from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Item.
     */
    public static WorldItem getItemFromData(byte[] data, int offset) {
        return Level.parseItem(new JSONObject(getStringValue(data, offset)));
    }

    /**
     * Get the encoded Player from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Player.
     */
    public static Player getPlayerFromData(byte[] data, int offset) {
        JSONObject obj = new JSONObject(getStringValue(data, offset + 1));
        boolean isValid = Level.verifyJSON(obj, "position")
            && Level.verifyJSON(obj, "health");
        if (!isValid) {
            return null;
        }

        Player p = new Player(Level.parsePosition(obj.getJSONObject("position")));
        p.setHealth(obj.getInt("health"));
        return p;
    }

    /**
     * Get the encoded Tile from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Tile.
     */
    public static Tile getTileFromData(byte[] data, int offset) {
        return Level.parseTile(new JSONObject(getStringValue(data, offset)));
    }

    /**
     * Get the encoded Entity from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Entity.
     */
    public static Entity getEntityFromData(byte[] data, int offset) {
        int type = data[offset];
        switch (type) {
            case ENTITY_TLE:
                return getTileFromData(data, offset + 1);
            case ENTITY_MST:
                return getMonsterFromData(data, offset + 1);
            case ENTITY_ITM:
                return getItemFromData(data, offset + 1);
            default:
                return null;
        }
    }
}
