package com.bravo.johny.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Player {

    private static final float BOUND_RADIUS = 0.4f;
    private static final float SIZE = 2* BOUND_RADIUS;
    private static final float MAX_X_SPEED = 0.25f;

    private float x, y;
    private Circle bound;

    public Player() {
        bound = new Circle(x, y, BOUND_RADIUS);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.circle(bound.x, bound.y, bound.radius, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBound();
    }

    public void update() {
        float xSpeed = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            xSpeed = MAX_X_SPEED;
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            xSpeed = -MAX_X_SPEED;

        x += xSpeed;
        updateBound();
    }

    private void updateBound() {
        bound.setPosition(x,y);
    }
}
