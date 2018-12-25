package com.bravo.johny.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.screens.menu.MenuScreen;

public class GameScreen implements Screen {

    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    private GameController gameController;
    private GameRenderer gameRenderer;
    private ObstacleAvoidGame game;
    private AssetManager assetManager;
    private SpriteBatch batch;

    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        // this is like create. it is used to initialize and load resources

        gameController = new GameController(game);
        gameRenderer = new GameRenderer(batch, assetManager, gameController);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);

        if(gameController.isGameOver())
            game.setScreen(new MenuScreen(game));
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        // this is called when we move from one screen to another
        dispose();  // dispose method is not called automatically
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
