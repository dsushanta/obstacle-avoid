package com.bravo.johny.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.assets.RegionNames;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.screens.game.GameScreen;
import com.bravo.johny.utils.GdxUtils;

/**
 * Created by bittu on 23,December,2018
 */
public class MenuScreen extends ScreenAdapter {

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    public MenuScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        initUI();
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initUI() {
        Table table = new Table();
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // -- PLAY Button
        ImageButton playButton = createButton(uiAtlas, RegionNames.PLAY, RegionNames.PLAY_PRESSED);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        // -- HIGH SCORE Button
        ImageButton highScoreButton = createButton(uiAtlas, RegionNames.HIGH_SCORE, RegionNames.HIGH_SCORE_PRESSED);
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        // -- OPTIONS Button
        ImageButton optionsButton = createButton(uiAtlas, RegionNames.OPTIONS, RegionNames.OPTIONS_PRESSED);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });

        // -- QUIT Button
        ImageButton quitButton = createButton(uiAtlas, RegionNames.PLAY, RegionNames.PLAY_PRESSED);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                quit();
            }
        });

        Table buttonTable = new Table();
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(new TextureRegionDrawable(panelRegion));
        buttonTable.add(playButton).row();
        buttonTable.add(highScoreButton).row();
        buttonTable.add(optionsButton).row();
        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

    }

    private void showHighScore() {
        game.setScreen(new HighScoreScreen(game));
    }

    private void showOptions() {
        game.setScreen(new OptionsScreen(game));
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private ImageButton createButton(TextureAtlas atlas, String upRegionName, String downRegionName) {
        TextureRegion upRegion = atlas.findRegion(upRegionName);
        TextureRegion downRegion = atlas.findRegion(downRegionName);

        return new ImageButton(
                new TextureRegionDrawable(upRegion),
                new TextureRegionDrawable(downRegion)
        );
    }
}
