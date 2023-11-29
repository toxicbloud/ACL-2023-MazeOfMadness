package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.engine.Scene;
import com.engine.Window;
import com.engine.events.Event;
import com.game.Score;
import com.game.monsters.MonsterType;

/**
 * Scene shown when the game ends.
 */
public class EndScene extends Scene {

    /**
     *
     */
    private static final float FIFTY_CONSTANT = 50.0f;
    /**
     * 100.0f.
     */
    private static final float ONE_HUNDRED_FLOAT = 100.0f;
    /**
     * Minimum world height used in the viewport.
     */
    private static final int MIN_WORLD_HEIGHT = 800;
    /**
     * Minimum world width used in the viewport.
     */
    private static final int MIN_WORLD_WIDTH = 800;
    /**
     * Zombie image draw near the zombie killed counter.
     */
    private Image zombie;
    /**
     * Ghost image draw near the ghost killed counter.
     */
    private Image ghost;
    /**
     * Stage used to draw the scene.
     */
    private Stage stage;
    /**
     * UI skin style.
     */
    private Skin skin;
    /**
     * True if the player won, false otherwise.
     */
    private boolean win;
    /**
     * Button click sound.
     */
    private Sound punch;
    /**
     * Fail sound.
     */
    private Sound failSound;
    /**
     * Win sound.
     */
    private Sound winSound;
    /**
     * Score obtained by the player in the last GameScene.
     */
    private Score score;

    /**
     * EndScene constructor.
     *
     * @param score The score obtained by the player
     * @param win   true if the player won, false otherwise
     */
    public EndScene(Score score, boolean win) {
        super();
        this.win = win;
        this.score = score;
    }

    /**
     * EndScene default constructor
     * when the player wins.
     */
    public EndScene() {
        super();
        this.win = false;
    }

    @Override
    public void create() {
        stage = new Stage(new ExtendViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT));
        skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        zombie = new Image(new Sprite(new Texture("images/menus/zombieCounter.png")));
        ghost = new Image(new Sprite(new Texture("images/menus/ghostCounter.png")));

        punch = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
        failSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/fail.mp3"));
        winSound = (Sound) Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        if (win) {
            Label label = new Label("YOU WIN", skin, "title");
            root.add(label).center().padBottom(FIFTY_CONSTANT);
            root.row();
            winSound.play();
        } else {
            // lose in red
            Label label2 = new Label("YOU LOSE", skin, "title");
            label2.setColor(1.0f, 0.0f, 0.0f, 1.0f);
            root.add(label2).center().padBottom(FIFTY_CONSTANT);
            root.row();
            failSound.play();
        }
        root.row();
        // score label
        Label scoreLabel = new Label("Score: " + score.getPoints(), skin, "subtitle");
        root.add(scoreLabel).center().padBottom(FIFTY_CONSTANT).row();
        Table zombieTable = new Table();
        zombieTable.add(zombie).size(ONE_HUNDRED_FLOAT, ONE_HUNDRED_FLOAT);
        zombieTable.add(new Label("x" + score.getKillCount(MonsterType.MONSTER_ZOMBIE), skin,
                "subtitle"));
        // space between the two
        zombieTable.add().width(ONE_HUNDRED_FLOAT);
        zombieTable.add(ghost).size(ONE_HUNDRED_FLOAT, ONE_HUNDRED_FLOAT);
        zombieTable.add(new Label("x" + score.getKillCount(MonsterType.MONSTER_GHOST), skin, "subtitle"));
        root.add(zombieTable).center().padBottom(FIFTY_CONSTANT);
        root.row();

        TextButton button = new TextButton("Back to menu", skin);
        root.add(button).center().padBottom(FIFTY_CONSTANT);
        button.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                punch.play();
                Window.getInstance().setScene(new MenuScene());
            }
        });

    }

    @Override
    public void update() {
        stage.act();
    }

    @Override
    public void render() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.getViewport().apply(true);
        stage.draw();
    }

    @Override
    public void onEvent(Event ev) {

    }

}
