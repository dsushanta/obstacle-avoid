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

    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;

    private DebugCameraConfig cameraConfig;

    public DebugCameraController() {
        cameraConfig = new DebugCameraConfig();
        log.info("CameraConfig -- "+cameraConfig);
    }

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
        
        float moveSpeed = cameraConfig.getMoveSpeed() * delta;
        float zoomSpeed = cameraConfig.getZoomSpeed() * delta;
        
        if (cameraConfig.isLeftPressed())
            moveLeft(moveSpeed);
        else if (cameraConfig.isRightPressed())
            moveRight(moveSpeed);
        else if (cameraConfig.isUpPressed())
            moveUP(moveSpeed);
        else if (cameraConfig.isDownPressed())
            moveDown(moveSpeed);
        else if (cameraConfig.isZoomInPressed())
            zoomIn(zoomSpeed);
        else if (cameraConfig.isZoomOutPressed())
            zoomOut(zoomSpeed);
        else if (cameraConfig.isResetPressed())
            zoomReset();
        else if (cameraConfig.isLogPressed())
            logDebug();
    }

    private void logDebug() {
        log.debug("Position : "+position);
        log.debug("Zoom Level : "+zoom);
    }

    private void setZoom(float zoomValue) {
        zoom = MathUtils.clamp(zoomValue, cameraConfig.getMaxZoomIn(), cameraConfig.getMaxZoomOut());
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
