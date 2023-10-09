package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends SpaceObjects{
    private float timer;
    private float time;
    private boolean remove;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;

        this.width = this.height = 2;

        this.speed = 50;
        radians = MathUtils.random(2*3.1415f);
        this.dX = MathUtils.cos(radians) * speed;
        this.dY = MathUtils.sin(radians) * speed;

        timer = 0;
        time = 1;
    }

    public void update(float delta) {
        this.x += this.dX * delta;
        this.y += this.dY * delta;

        timer += delta;
        if (timer > time)
            this.remove = true;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(x - this.width / 2.0f, y - this.width / 2.0f, width/2.0f);

        shapeRenderer.end();
    }

    public boolean isRemove() {
        return this.remove;
    }
}
