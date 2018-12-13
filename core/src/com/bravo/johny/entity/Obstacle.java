package com.bravo.johny.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.bravo.johny.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable {

    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;

    public Obstacle() {
        super(GameConfig.OBSTACLE_BOUND_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.setColor(Color.ORANGE);
        renderer.circle(bound.x, bound.y, bound.radius, 30);
    }

    public void update() {
        setY(getY() - ySpeed);
    }

    public boolean isCollidingWithPlayer(Player player) {
        Circle playerBounds = player.getBound();
        boolean overlaps =  Intersector.overlaps(playerBounds, getBound());
        hit = overlaps;

        return overlaps;
    }

    public boolean isNotHit() {
        return !hit;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void reset() {
        hit = false;
//        ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    }

}
