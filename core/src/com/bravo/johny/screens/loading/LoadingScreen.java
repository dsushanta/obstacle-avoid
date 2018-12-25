package com.bravo.johny.screens.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.ObstacleAvoidGame;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.screens.game.GameScreen;
import com.bravo.johny.screens.menu.MenuScreen;
import com.bravo.johny.utils.GdxUtils;

/**
 * Created by bittu on 22,December,2018
 */
public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH/2f;
    private static final float PROGRESS_BAR_HEIGHT = 60;
    private static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    public LoadingScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = this.game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.HIT_SOUND);
        // halts execution until all assets get loaded
//        assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        update(delta);
        GdxUtils.clearScreen();
        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        draw();
        shapeRenderer.end();
        if(changeScreen)
            game.setScreen(new MenuScreen(game));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        // Screens don't dispose automatically. Need to call dispose explicitly
        dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void update(float delta) {

        // it returns a number between 0 and 1 indicating how much of asset loading is done
        progress = assetManager.getProgress();

        // If update() returns true, means everything is loaded. this update method needs to be invoked to
        // continue loading remaining resources.(Remember, we are not using finishloading() method)
        if(assetManager.update())
            waitTime -= delta;
        if(waitTime <=0)
            changeScreen = true;
        // waitTime variable is introduced to give a small delay after all the resources are loaded

    }

    private void draw() {
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        shapeRenderer.rect(progressBarX,
                progressBarY,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT
        );
    }

    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
