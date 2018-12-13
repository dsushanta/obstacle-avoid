package com.bravo.johny.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {

    private float x, y;
    private float width=1, height=1;
    protected Circle bound;

    public GameObjectBase(float boundsRadius) {
        bound = new Circle(x, y, boundsRadius);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.circle(bound.x, bound.y, bound.radius, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBound();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Circle getBound() {
        return bound;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBound();
    }

    public void setX(float x) {
        this.x = x;
        updateBound();
    }

    public void setY(float y) {
        this.y = y;
        updateBound();
    }

    protected void updateBound() {
        float halfWidth = width/2f;
        float halfHeight = height/2f;
        bound.setPosition(x+halfWidth, y+halfHeight);
    }

}
