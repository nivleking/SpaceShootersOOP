package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class AsteroidLarge extends Asteroid{
    public AsteroidLarge(float x, float y) {
        super(x, y);
        numPoints = 12;
        width = height = 70;
        speed = MathUtils.random(20,30);
        score = 20;
        rotationSpeed = MathUtils.random(-1, 1);

        radians = MathUtils.random(2 * 3.1415f);
        dX = MathUtils.cos(radians) * speed;
        dY = MathUtils.sin(radians) * speed;

        shapeX = new float[numPoints];
        shapeY = new float[numPoints];
        distances = new float[numPoints];

        int radius = width / 2;
        for (int i = 0; i < numPoints; i++) {
            distances[i] = MathUtils.random(radius/2, radius);
        }

        setShape();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0,1,1,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = shapeX.length - 1; i < shapeX.length; j = i++) {
            shapeRenderer.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }
        shapeRenderer.end();
    }
}
