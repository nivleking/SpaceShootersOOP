package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class PlayerBullets extends SpaceObjects{
    private final float lifeTime;
    private float lifeTimer;

    private boolean remove;

    public PlayerBullets(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;

        float speed = 350f;
        dX = MathUtils.cos(radians) * speed;
        dY = MathUtils.sin(radians) * speed;

        width = height = 2;

        lifeTimer = 0;
        lifeTime = 1;
    }

    public void update(float delta) {
        x += dX * delta;
        y += dY * delta;

        wrap();

        lifeTimer += delta;
        if (lifeTimer > lifeTime)
            remove = true;

    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0.5f, 1, 0,1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(x - width / 2.0f, y - height / 2.0f, width / 2.0f);
        shapeRenderer.end();
    }

    public boolean remove() {
        return remove;
    }
}
