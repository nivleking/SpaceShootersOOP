package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class AsteroidMedium extends Asteroid{
    public AsteroidMedium(float x, float y) {
        super(x, y);
        numPoints = 10;
        width = height = 50;
        speed = MathUtils.random(50,60);
        score = 500;
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
        shapeRenderer.setColor(1,0.6f,0,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = shapeX.length - 1; i < shapeX.length; j = i++) {
            shapeRenderer.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }
        shapeRenderer.end();
    }
}
