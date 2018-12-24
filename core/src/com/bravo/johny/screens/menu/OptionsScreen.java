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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.assets.RegionNames;
import com.bravo.johny.comon.GameManager;
import com.bravo.johny.config.DifficultyLevel;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.utils.GdxUtils;

/**
 * Created by bittu on 24,December,2018
 */
public class OptionsScreen extends ScreenAdapter {

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Image checkMark;

    public OptionsScreen(ObstacleAvoidGame game) {
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

    // ##### PRIVATE METHODS #####

    private void createUI() {
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        Image background = new Image(backgroundRegion);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label label = new Label("DIFFICULTY", labelStyle);
        label.setPosition(GameConfig.HUD_WIDTH/2f, GameConfig.HUD_HEIGHT/2f + 180, Align.center);
        TextureRegion checkMarkRegion = uiAtlas.findRegion(RegionNames.CHECK_MARK);
        checkMark = new Image(checkMarkRegion);
        final ImageButton easy = createButton(uiAtlas, RegionNames.EASY);
        easy.setPosition(GameConfig.HUD_WIDTH/2f, GameConfig.HUD_HEIGHT/2f + 90, Align.center);
        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkMark.setY(easy.getY()+25);
                GameManager.INSTANCE.updateDifficultyLevel(DifficultyLevel.EASY);
            }
        });
        final ImageButton medium = createButton(uiAtlas, RegionNames.MEDIUM);
        medium.setPosition(GameConfig.HUD_WIDTH/2f, GameConfig.HUD_HEIGHT/2f, Align.center);
        medium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkMark.setY(medium.getY()+25);
                GameManager.INSTANCE.updateDifficultyLevel(DifficultyLevel.MEDIUM);
            }
        });
        final ImageButton hard = createButton(uiAtlas, RegionNames.HARD);
        hard.setPosition(GameConfig.HUD_WIDTH/2f, GameConfig.HUD_HEIGHT/2f - 90, Align.center);
        hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkMark.setY(hard.getY()+25);
                GameManager.INSTANCE.updateDifficultyLevel(DifficultyLevel.HARD);
            }
        });

        checkMark.setPosition(medium.getX()+50, medium.getY()+40, Align.center);
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        if(difficultyLevel.isEasy())
            checkMark.setY(easy.getY()+25);
        else if(difficultyLevel.isHard())
            checkMark.setY(hard.getY()+25);

        ImageButton back = new ImageButton(
                new TextureRegionDrawable(uiAtlas.findRegion(RegionNames.BACK)),
                new TextureRegionDrawable(uiAtlas.findRegion(RegionNames.BACK_PRESSED))
        );
        back.setPosition(GameConfig.HUD_WIDTH/2f, GameConfig.HUD_HEIGHT/2f - 180, Align.center);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        stage.addActor(background);
        stage.addActor(label);
        stage.addActor(easy);
        stage.addActor(medium);
        stage.addActor(hard);
        stage.addActor(checkMark);
        stage.addActor(back);
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }

    private ImageButton createButton(TextureAtlas atlas, String regionName) {
        TextureRegion region = atlas.findRegion(regionName);

        return new ImageButton(
                new TextureRegionDrawable(region)
        );
    }
}
