package com.network;

import com.game.Entity;
import com.game.Item;
import com.game.Level;
import com.game.Player;
import com.game.exceptions.InvalidItemException;
import com.game.exceptions.InvalidMonsterException;
import com.game.exceptions.InvalidSchemaException;
import com.game.exceptions.InvalidTileException;
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
    public static final byte MAZE_UPT = 24;
    /** MAZE_REM code. */
    public static final byte MAZE_REM = 25;

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
     * Get the encoded Monster value from the data array.
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
     * Get the encoded Item value from the data array.
     * @param i Item.
     * @param offset Offset.
     * @return Encoded Item value.
     */
    public static byte[] encodeItemValue(Item i, int offset) {
        String json = i.toJSON().toString();
        byte[] data = new byte[json.length() + 1 + offset];
        data[offset] = ENTITY_ITM;
        encodeStringValue(json, data, 1 + offset);
        return data;
    }

    /**
     * Get the encoded Player value from the data array.
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
     * Get the encoded Tile value from the data array.
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
     * Get the encoded Monster from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Monster.
     */
    public static Monster getMonsterFromData(byte[] data, int offset) {
        try {
            Monster m = Level.parseMonster(new JSONObject(getStringValue(data, offset)));
            return m;
        } catch (InvalidSchemaException | InvalidMonsterException e) {
            System.out.println("Error parsing monster: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get the encoded Item from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Item.
     */
    public static Item getItemFromData(byte[] data, int offset) {
        try {
            Item i = Level.parseItem(new JSONObject(getStringValue(data, offset)));
            return i;
        } catch (InvalidSchemaException | InvalidItemException e) {
            System.out.println("Error parsing item: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get the encoded Player from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Player.
     */
    public static Player getPlayerFromData(byte[] data, int offset) {
        try {
            JSONObject obj = new JSONObject(getStringValue(data, offset));
            Level.verifyJSON(obj, "position");
            Level.verifyJSON(obj, "health");
            Player p = new Player(Level.parsePosition(obj.getJSONObject("position")));
            p.setHealth(obj.getInt("health"));
            return p;
        } catch (InvalidSchemaException e) {
            System.out.println("Error parsing player: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get the encoded Tile from the data array.
     * @param data Data array.
     * @param offset Offset.
     * @return Tile.
     */
    public static Tile getTileFromData(byte[] data, int offset) {
        try {
            Tile t = Level.parseTile(new JSONObject(getStringValue(data, offset)));
            return t;
        } catch (InvalidSchemaException | InvalidTileException e) {
            System.out.println("Error parsing tile: " + e.getMessage());
            return null;
        }
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
