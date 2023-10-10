package com;

import com.badlogic.gdx.Gdx;
import com.engine.utils.Vector3;
import com.game.Level;
import com.game.PlayerData;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for file level loading.
 *
 * @author Antonin Rousseau, toxicbloud on GitHub
 */
@ExtendWith(GdxTestRunner.class)
public class LevelLoaderTest {

    /**
     * The expected health of the player.
     */
    private static final int EXPECTED_HEALTH = 100;

    /**
     * The expected maximum health of the player.
     */
    private static final int EXPECTED_MAX_HEALTH = 120;

    /**
     * The expected position of the player.
     */
    private static final Vector3 EXPECTED_POSITION = new Vector3(2.0f, 4.0f, 5.0f);

    /**
     * Test parsing a player data JSON object.
     */
    @Test
    void testPlayerDataParsing() {
        String jsonPlayerData = """
                        {
                            "health": 100,
                            "maxHealth": 120,
                            "position": {
                                "x": 2.0,
                                "y": 4.0,
                                "z": 5.0
                            },
                            "items":[
                                {
                                    "type": "sword",
                                }
                            ]
                        }
                """;
        JSONObject playerData = new JSONObject(jsonPlayerData);
        assertEquals(EXPECTED_HEALTH, playerData.getInt("health"));
        PlayerData player = new PlayerData(playerData);
        assertEquals(EXPECTED_HEALTH, player.getHealth(), "Player health should be 100");
        assertEquals(EXPECTED_MAX_HEALTH, player.getMaxHealth(), "Player max health should be 120");
        assertEquals(EXPECTED_POSITION, player.getPosition(), "Player position should be (2.0, 4.0, 5.0)");
    }

    /**
     * Test serializing a player data object to JSON.
     */
    @Test
    void testPlayerDataSerialization() {
        PlayerData player = new PlayerData(EXPECTED_HEALTH, EXPECTED_MAX_HEALTH, EXPECTED_POSITION);
        JSONObject jsonPlayer = player.toJSON();
        assertEquals(EXPECTED_HEALTH, jsonPlayer.getInt("health"));
        assertEquals(EXPECTED_MAX_HEALTH, jsonPlayer.getInt("maxHealth"));
        JSONObject jsonPosition = (JSONObject) jsonPlayer.get("position");
        assertEquals(EXPECTED_POSITION.getX(), jsonPosition.getFloat("x"));
        assertEquals(EXPECTED_POSITION.getY(), jsonPosition.getFloat("y"));
        assertEquals(EXPECTED_POSITION.getZ(), jsonPosition.getFloat("z"));
    }

    /**
     * Test parsing a level JSON object.
     */
    @Test
    void testLevelParsing() {
        String level = """
                {
                    "name": "Charlemagne first floor",
                    "description": "Charlemagne first floor is a strange place to be.",
                    "author": "Antonin Rousseau",
                    "version": "1.4.2",
                    "maze": {
                        "width": 2,
                        "height": 1,
                        "depth": 1,
                        "tiles": [
                            {
                                "type": "StairGrass",
                                "direction": 0
                            },
                            {
                                "type": "GroundRock"
                            }
                        ],
                        "monsters": [
                            {
                                "type": "zombie",
                                "position": {
                                    "x": 0,
                                    "y": 0,
                                    "z": 0
                                }
                            },
                            {
                                "type": "ghost",
                                "position": {
                                    "x": 0,
                                    "y": 0,
                                    "z": 0
                                }
                            }
                        ],
                        "items": [
                            {
                                "type": "sword",
                                "position": {
                                    "x": 0,
                                    "y": 0,
                                    "z": 0
                                }
                            }
                        ]
                    },
                    "player": {
                        "health": 100,
                        "maxHealth": 100,
                        "position": {
                            "x": 0,
                            "y": 0,
                            "z": 0
                        },
                        "items": [
                            {
                                "type": "sword"
                            }
                        ]
                    }
                }
                """;
        assertFalse(Gdx.files.internal("images/existepas.png").exists(), level);
        assertTrue(Gdx.files.internal("images/player.png").exists(), level);
        JSONObject jsonLevel = new JSONObject(level);
        assertEquals("Charlemagne first floor", jsonLevel.getString("name"));
        Level parsedLevel = new Level(jsonLevel);
        assertEquals("Charlemagne first floor", parsedLevel.getName());
        assertEquals(parsedLevel.getMaze().getHeight(), 1);
        assertEquals(parsedLevel.getMaze().getWidth(), 2);
        assertEquals(parsedLevel.getMaze().getDepth(), 1);
    }

}
