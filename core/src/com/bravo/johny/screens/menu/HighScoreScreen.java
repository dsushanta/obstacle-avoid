package com.bravo.johny.screens.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.assets.RegionNames;
import com.bravo.johny.comon.GameManager;

/**
 * Created by bittu on 24,December,2018
 */
public class HighScoreScreen extends MenuScreenBase {

    public HighScoreScreen(ObstacleAvoidGame game) {
        super(game);
    }

    // ######## PRIVATE METHODS ########

    @Override
    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label highScoreText = new Label("HIGH SCORE",uiskin);
        Label highScoreValue = new Label(GameManager.INSTANCE.getHighScore(), uiskin);

        TextButton backButton = new TextButton("BACK", uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        Table contentTable = new Table(uiskin);
        contentTable.defaults().pad(20);
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(highScoreText).row();
        contentTable.add(highScoreValue).row();
        contentTable.add(backButton).row();
        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;

    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
