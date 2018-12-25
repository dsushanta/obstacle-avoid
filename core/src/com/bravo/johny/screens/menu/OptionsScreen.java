package com.bravo.johny.screens.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
import com.bravo.johny.config.DifficultyLevel;

/**
 * Created by bittu on 24,December,2018
 */
public class OptionsScreen extends MenuScreenBase {

    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy, medium, hard;

    public OptionsScreen(ObstacleAvoidGame game) {
        super(game);
    }

    // ##### PRIVATE METHODS #####

    @Override
    protected Actor createUI() {
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label label = new Label("DIFFICULTY", uiskin);

        easy = checkBox(DifficultyLevel.EASY.name(), uiskin);
//        easy.setDebug(true);
        medium = checkBox(DifficultyLevel.MEDIUM.name(), uiskin);
        hard = checkBox(DifficultyLevel.HARD.name(), uiskin);

        checkBoxGroup = new ButtonGroup<CheckBox>(easy, medium, hard);

        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        checkBoxGroup.setChecked(difficultyLevel.name());

        TextButton backButton = new TextButton("BACK", uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);

        Table contentTable = new Table(uiskin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(RegionNames.PANEL);
        contentTable.add(label).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add(backButton);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private CheckBox checkBox(String text, Skin uiskin) {
        CheckBox checkBox = new CheckBox(text, uiskin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(8);

        return checkBox;
    }

    private void difficultyChanged() {
        CheckBox checked = checkBoxGroup.getChecked();

        if(checked == easy)
            GameManager.INSTANCE.updateDifficultyLevel(DifficultyLevel.EASY);
        else if(checked == medium)
            GameManager.INSTANCE.updateDifficultyLevel(DifficultyLevel.MEDIUM);
        else if(checked == hard)
            GameManager.INSTANCE.updateDifficultyLevel(DifficultyLevel.HARD);
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }

}
