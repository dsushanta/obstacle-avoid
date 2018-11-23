package com.bravo.johny.utils.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraController {

    private static final Logger log = new Logger(DebugCameraController.class.getName(), Logger.DEBUG);

    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_UP_KEY = Input.Keys.W;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;
    private static final float DEFAULT_MOVE_SPEED = 20.0f;
    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.COMMA;
    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.PERIOD;
    private static final int DEFAULT_ZOOM_RESET_KEY = Input.Keys.BACKSPACE;
    private static final int DEFAULT_ZOOM_LOG_KEY = Input.Keys.ENTER;
    private static final float DEFAULT_ZOOM_SPEED = 2.0f;
    private static final float DEFAULT_MAX_ZOOM_IN = 0.20f;
    private static final float DEFAULT_MAX_ZOOM_OUT = 30f;

    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;

    public void setStartPosition(float x, float y) {
        startPosition.set(x,y);
        position.set(x,y);
    }
    
    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.zoom = zoom;
        camera.update();
    }
    
    public void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;
        
        float moveSpeed = DEFAULT_MOVE_SPEED * delta;
        float zoomSpeed = DEFAULT_ZOOM_SPEED * delta;
        
        if (Gdx.input.isKeyPressed(DEFAULT_LEFT_KEY))
            moveLeft(moveSpeed);
        else if (Gdx.input.isKeyPressed(DEFAULT_RIGHT_KEY))
            moveRight(moveSpeed);
        else if (Gdx.input.isKeyPressed(DEFAULT_UP_KEY))
            moveUP(moveSpeed);
        else if (Gdx.input.isKeyPressed(DEFAULT_DOWN_KEY))
            moveDown(moveSpeed);
        else if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_IN_KEY))
            zoomIn(zoomSpeed);
        else if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_OUT_KEY))
            zoomOut(zoomSpeed);
        else if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_RESET_KEY))
            zoomReset();
        else if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_LOG_KEY))
            logDebug();
    }

    private void logDebug() {
        log.debug("Position : "+position);
        log.debug("Zoom Level : "+zoom);
    }

    private void setZoom(float zoomValue) {
        zoom = MathUtils.clamp(zoomValue, DEFAULT_MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_OUT);
    }

    private void zoomReset() {
        position.set(startPosition);
        setZoom(1.0f);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom -zoomSpeed);
    }

    private void zoomIn(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x+xSpeed, position.y+ySpeed);
    }

    private void moveDown(float moveSpeed) {
        moveCamera(0, -moveSpeed);
    }

    private void moveUP(float moveSpeed) {
        moveCamera(0, moveSpeed);
    }

    private void moveRight(float moveSpeed) {
        moveCamera(moveSpeed, 0);
    }

    private void moveLeft(float moveSpeed) {
        moveCamera(-moveSpeed, 0);
    }
}
