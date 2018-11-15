package com.bravo.johny.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class viewPortUtils {

    private static final Logger log = new Logger(viewPortUtils.class.getName(), Logger.DEBUG);
    private static final int DEFAULT_CELL_SIZE = 1;

    private viewPortUtils() {}

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer) {
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize) {
        if(viewport == null)
            throw new IllegalArgumentException("Viewport is null");
        if(renderer == null)
            throw new IllegalArgumentException("Shaperenderer is null");
        if(cellSize < DEFAULT_CELL_SIZE)
            cellSize = DEFAULT_CELL_SIZE;

        Color oldColor = new Color(renderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth(); // World height/width was set when viewport was
        int worldHeight = (int) viewport.getWorldHeight(); // instantiated
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.setColor(Color.WHITE);
        for (int x=-doubleWorldWidth; x<doubleWorldWidth; x+=cellSize) {
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }

        for (int y=-doubleWorldHeight; y<doubleWorldHeight; y+=cellSize) {
            renderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }
        renderer.setColor(Color.YELLOW);
        renderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        renderer.setColor(Color.GREEN);
        renderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);

        renderer.setColor(Color.RED);
        renderer.line(0, worldHeight, worldWidth, worldHeight);
        renderer.line(worldWidth, 0, worldWidth, worldHeight);

        renderer.end();
        renderer.setColor(oldColor);
    }

    public static void debugPixelPerUnit(Viewport viewport) {
        if (viewport == null)
            throw new IllegalArgumentException("Viewport is null");

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("X PPU : "+xPPU+" | Y PPU : "+yPPU);
    }
}
