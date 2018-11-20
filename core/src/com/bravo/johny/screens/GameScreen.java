package com.bravo.johny.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.entity.Player;
import com.bravo.johny.utils.GdxUtils;
import com.bravo.johny.utils.debug.DebugCameraController;
import com.bravo.johny.utils.viewPortUtils;

public class GameScreen implements Screen {

    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private Player player;
    private DebugCameraController debugCameraController;

    @Override
    public void show () {
        // this is like create. it is used to initialize and load resources
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        player = new Player();

//        float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
//        float startPlayerY = 1;

        float startPlayerX = 12;
        float startPlayerY = 12;

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        player.setPosition(startPlayerX, startPlayerY);
    }

    private void renderDebug() {

        renderer.setColor(Color.CYAN);
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        drawDebug();

        renderer.end();
        viewPortUtils.drawGrid(viewport, renderer);
    }

    @Override
    public void render (float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
        update(delta);
        GdxUtils.clearScreen();
        renderDebug();
    }

    private void update(float delta) {
        updatePlayer();
    }

    private void updatePlayer() {
        log.debug("Player X : "+player.getX()+" | "+"Player Y : "+player.getY());
        player.update();
    }

    private void drawDebug() {
        player.drawDebug(renderer);
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewPortUtils.debugPixelPerUnit(viewport);
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
}
