package com.bravo.johny.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bravo.johny.assets.AssetDescriptors;
import com.bravo.johny.assets.RegionNames;
import com.bravo.johny.config.GameConfig;
import com.bravo.johny.entity.Background;
import com.bravo.johny.entity.Obstacle;
import com.bravo.johny.entity.Player;
import com.bravo.johny.utils.GdxUtils;
import com.bravo.johny.utils.debug.DebugCameraController;
import com.bravo.johny.utils.viewPortUtils;

public class GameRenderer implements Disposable {

    private static final Logger log = new Logger(GameRenderer.class.getName(), Logger.DEBUG);

    private OrthographicCamera camera, hudCamera;
    private Viewport viewport, hudViewport;
    private BitmapFont font;
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private ShapeRenderer renderer;
    private DebugCameraController debugCameraController;
    private final GameController gameController;
    private TextureRegion playerRegion, obstacleRegion, backgroudRegion;

    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController controller) {
        this.assetManager = assetManager;
        gameController = controller;
        this.batch = batch;
        init();
    }

    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        font = assetManager.get(AssetDescriptors.FONT);
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        playerRegion = gameplayAtlas.findRegion(RegionNames.PLAYER);
        obstacleRegion = gameplayAtlas.findRegion(RegionNames.OBSTACLE);
        backgroudRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
    }

    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        if(Gdx.input.isTouched() && !gameController.isGameOver()) {
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            Player player = gameController.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - player.getWidth());
            player.setX(worldTouch.x);
        }

        GdxUtils.clearScreen();

        renderDebug();

        renderGamePlay();

        // render ui-hud
        renderUI();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        viewPortUtils.debugPixelPerUnit(viewport);
    }

    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Background background = gameController.getBackground();
        batch.draw(backgroudRegion,
                background.getX(),
                background.getY(),
                background.getWidth(),
                background.getHeight());

        Player player = gameController.getPlayer();
        batch.draw(playerRegion,
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight());

        for(Obstacle obstacle : gameController.getObstacles()) {
            batch.draw(obstacleRegion,
                    obstacle.getX(),
                    obstacle.getY(),
                    obstacle.getWidth(),
                    obstacle.getHeight());
        }

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setColor(Color.CYAN);
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        drawDebug();

        renderer.end();
//        viewPortUtils.drawGrid(viewport, renderer);
    }

    private void renderUI() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES : "+gameController.getLives();
        glyphLayout.setText(font, livesText);
        font.draw(batch, livesText,
                20,
                GameConfig.HUD_HEIGHT - glyphLayout.height
        );

        String scoreText = "SCORE : "+gameController.getDisplayScore();
        glyphLayout.setText(font, scoreText);
        font.draw(batch, scoreText,
                GameConfig.HUD_WIDTH - glyphLayout.width - 20,
                GameConfig.HUD_HEIGHT - glyphLayout.height
        );

        batch.end();
    }

    private void drawDebug() {
        gameController.getPlayer().drawDebug(renderer);

        for(Obstacle obstacle : gameController.getObstacles()) {
            obstacle.drawDebug(renderer);
        }
    }
}
