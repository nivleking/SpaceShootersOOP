package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends SpaceObjects{
    protected long score;

    protected int numPoints;
    protected float[] distances;

    protected boolean remove;

    public Asteroid(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setShape() {
        float angle = 0;
        for (int i = 0; i < numPoints; i++){
            shapeX[i] = x + MathUtils.cos(angle + radians) * distances[i];
            shapeY[i] = y + MathUtils.sin(angle + radians) * distances[i];
            angle += 2 * 3.1415f / numPoints;
        }
    }

    public void update(float delta) {
        x += dX * delta;
        y += dY * delta;

        radians += rotationSpeed * delta;
        setShape();
        wrap();
    }

    public void draw(ShapeRenderer shapeRenderer) {

    }

    public boolean isRemove() {
        return this.remove;
    }

    public long getScore() {
        return this.score;
    }
}
