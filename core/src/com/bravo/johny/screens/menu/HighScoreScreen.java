package com.bravo.johny.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.assets.RegionNames;
import com.bravo.johny.comon.GameManager;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.utils.GdxUtils;

/**
 * Created by bittu on 24,December,2018
 */
public class HighScoreScreen extends ScreenAdapter {

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    public HighScoreScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createUI();
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

    // ######## PRIVATE METHODS ########

    private void createUI() {
        Table table = new Table();

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);
        TextureRegion backRegion = uiAtlas.findRegion(RegionNames.BACK);
        TextureRegion backPressedRegion = uiAtlas.findRegion(RegionNames.BACK_PRESSED);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label highScoreText = new Label("HIGH SCORE",labelStyle);
        Label highScoreLabel = new Label(GameManager.INSTANCE.getHighScore(), labelStyle);

        ImageButton backButton = new ImageButton(
                new TextureRegionDrawable(backRegion),
                new TextureRegionDrawable(backPressedRegion)
        );
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        Table contentTable = new Table();
        contentTable.defaults().pad(20);
        contentTable.setBackground(new TextureRegionDrawable(panelRegion));

        contentTable.add(highScoreText).row();
        contentTable.add(highScoreLabel).row();
        contentTable.add(backButton).row();
        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
