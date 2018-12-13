package com.bravo.johny.screens;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    private GameController gameController;
    private GameRenderer gameRenderer;

    @Override
    public void show() {
        // this is like create. it is used to initialize and load resources

        gameController = new GameController();
        gameRenderer = new GameRenderer(gameController);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);
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
