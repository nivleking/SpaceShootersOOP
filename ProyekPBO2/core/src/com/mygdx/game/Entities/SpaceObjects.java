package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;

public class SpaceObjects{
    protected float x;
    protected float y;

    protected float dX;
    protected float dY;

    protected float radians;
    protected float speed;
    protected float rotationSpeed;

    protected int width;
    protected int height;

    protected float[] shapeX;
    protected float[] shapeY;

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float[] getShapeX() {
        return this.shapeX;
    }

    public float[] getShapeY() {
        return this.shapeY;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean contains(float x, float y) {
        boolean temp = false;
        for (int i = 0, j = shapeX.length - 1; i < shapeX.length; j = i++) {
            if ((shapeY[i] > y) != (shapeY[j] > y) && (x < (shapeX[j] - shapeX[i]) * (y - shapeY[i])
            / (shapeY[j] - shapeY[i]) + shapeX[i])) {
                temp = !temp;
            }
        }
        return temp;
    }

    public boolean intersects(SpaceObjects spaceObjects) {
        float[] sX = spaceObjects.getShapeX();
        float[] sY = spaceObjects.getShapeY();

        for (int i = 0; i < sX.length; i++) {
            if (contains(sX[i], sY[i]))
                return true;
        }
        return false;
    }

    protected void wrap() {
        if (x < 0) x = Gdx.graphics.getWidth();
        if (x > Gdx.graphics.getWidth()) x = 0;
        if (y < 0) y = Gdx.graphics.getHeight();
        if (y > Gdx.graphics.getHeight()) y = 0;
    }
}
