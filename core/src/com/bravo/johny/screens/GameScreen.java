package com.bravo.johny.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.utils.GdxUtils;
import com.bravo.johny.utils.viewPortUtils;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    @Override
    public void show () {
        // this is like create. it is used to initialize and load resources
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
    }

    @Override
    public void render (float delta) {
        GdxUtils.clearScreen();

        drawDebug();
    }

    private void drawDebug() {
        viewPortUtils.drawGrid(viewport, renderer);
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
