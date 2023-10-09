package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Managers.Sounds;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player extends SpaceObjects{

    private boolean left;
    private boolean right;
    private boolean up;

    private final float[] flameX;
    private final float[] flameY;

    private ArrayList<PlayerBullets> playerBullets;

    private final float maxSpeed;
    private final float acceleration;
    private final float deceleration;
    private float accelerationTimer;

    private boolean hit;
    private boolean dead;

    private float hitTimer;
    private float hitTime;
    private Line2D.Float[] hitLines;
    private Point2D.Float[] hitLinesVector;

    private long score;
    private int lives;
    private long requiredScore;
    private Sounds sounds;

    public Player(ArrayList<PlayerBullets> playerBullets) {
        this.playerBullets = playerBullets;

        this.x = Gdx.graphics.getWidth() / 2.0f;
        this.y = Gdx.graphics.getHeight() / 2.0f;

        this.maxSpeed = 300f;
        this.acceleration = 150f;
        this.deceleration = 10f;

        this.shapeX = new float[4];
        this.shapeY = new float[4];
        this.flameX = new float[3];
        this.flameY = new float[3];

        this.radians = 3.1415f / 2;
        this.rotationSpeed = 3;

        this.hit = false;
        this.hitTimer = 0;
        this.hitTime = 2;

        this.score = 0;
        this.lives = 3;
        this.requiredScore = 5_000;

        sounds = new Sounds();
    }

    public void hit() {
        if (hit) return;

        hit = true;
        dX = dY = 0;
        left = right = up = false;

        hitLines = new Line2D.Float[4];
        for (int  i = 0, j = hitLines.length - 1; i < hitLines.length; j = i++) {
            hitLines[i] = new Line2D.Float(shapeX[i], shapeY[i],
                    shapeX[j], shapeY[j]);
        }

        hitLinesVector = new Point2D.Float[4];
        hitLinesVector[0] = new Point2D.Float(MathUtils.cos(radians + 1.5f), MathUtils.sin(radians + 1.5f));
        hitLinesVector[1] = new Point2D.Float(MathUtils.cos(radians - 1.5f), MathUtils.sin(radians - 1.5f));
        hitLinesVector[2] = new Point2D.Float(MathUtils.cos(radians - 2.8f), MathUtils.sin(radians - 2.8f));
        hitLinesVector[3] = new Point2D.Float(MathUtils.cos(radians + 2.8f), MathUtils.sin(radians + 2.8f));
    }

    public void setShape() {
        shapeX[0] = x + MathUtils.cos(radians) * 16;
        shapeY[0] = y + MathUtils.sin(radians) * 16;

        shapeX[1] = x + MathUtils.cos(radians - 6 * 3.1415f / 5) * 16;
        shapeY[1] = y + MathUtils.sin(radians - 6 * 3.1415f / 5) * 16;

        shapeX[2] = x + MathUtils.cos(radians + 3.1415f) * 10;
        shapeY[2] = y + MathUtils.sin(radians + 3.1415f) * 10;

        shapeX[3] = x + MathUtils.cos(radians + 6 * 3.1415f / 5) * 16;
        shapeY[3] = y + MathUtils.sin(radians + 6 * 3.1415f / 5) * 16;
    }

    public void setFlame() {
        flameX[0] = x + MathUtils.cos(radians - 5 * 3.1415f / 6) * 10;
        flameY[0] = y + MathUtils.sin(radians - 5 * 3.1415f / 6) * 10;

        flameX[1] = x + MathUtils.cos(radians - 3.1415f) * (6 + accelerationTimer * 50);
        flameY[1] = y + MathUtils.sin(radians - 3.1415f) * (6 + accelerationTimer * 50);

        flameX[2] = x + MathUtils.cos(radians + 5 * 3.1415f / 6) * 10;
        flameY[2] = y + MathUtils.sin(radians + 5 * 3.1415f / 6) * 10;
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        if (b && !up && !hit)
            sounds.getSound8().loop(0.12f);
        else if (!b)
            sounds.getSound8().stop();
        up = b;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setShape();
    }

    public boolean isDead() {
        return this.dead;
    }

    public boolean isHit() {
        return this.hit;
    }

    public long getScore() {
        return this.score;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(long score) { this.score = score;}

    public void shoot() {
        int MAX_BULLETS = 4;
        if (this.playerBullets.size() == MAX_BULLETS) return;
        if (!isDead()) {
            playerBullets.add(new PlayerBullets(x, y, radians));
            sounds.getSound6().play(0.17f);
        }
    }

    public void loseLife() {
        this.lives--;
    }

    public void addScore(long temp) {
        this.score += temp;
    }

    public void update(float delta) {
        if (hit) {
            hitTimer += delta;
            if (hitTimer > hitTime) {
                dead = true;
                hitTimer = 0;
            }
            for (int i = 0; i < hitLines.length; i++) {
                hitLines[i].setLine(
                        hitLines[i].x1 + hitLinesVector[i].x * 10 * delta,
                        hitLines[i].y1 + hitLinesVector[i].y * 10 * delta,
                        hitLines[i].x2 + hitLinesVector[i].x * 10 * delta,
                        hitLines[i].y2 + hitLinesVector[i].y * 10 * delta
                );
            }
            return;
        }

        if (score >= requiredScore) {
            lives++;
            requiredScore += 5_000;
            sounds.getSound1().play();
        }

        //bergerak
        if (left)
            radians += rotationSpeed * delta;
        else if (right)
            radians -= rotationSpeed * delta;

        //menambah percepatan
        if (up) {
            dX += MathUtils.cos(radians) * acceleration * delta;
            dY += MathUtils.sin(radians) * acceleration * delta;
            accelerationTimer += delta;

            if (accelerationTimer > 0.1f)
                accelerationTimer = 0;
        }
        else
            accelerationTimer = 0;

        //mengurangi percepatan
        float vec = (float) Math.sqrt(dX * dX + dY * dY);
        if(vec > 0) {
            dX -= (dX / vec) * deceleration * delta;
            dY -= (dY / vec) * deceleration * delta;
        }
        if (vec > maxSpeed) {
            dX = (dX / vec) * maxSpeed;
            dY = (dY / vec) * maxSpeed;
        }

        x += dX * delta;
        y += dY * delta;

        setShape();

        if (up)
            setFlame();

        wrap();
    }

    public void draw(ShapeRenderer shapeRenderer1, ShapeRenderer shapeRenderer2) {
        shapeRenderer1.setColor(1, 1, 1, 1);
        shapeRenderer2.setColor(0.8f, 0.1f, 0, 1);
        shapeRenderer1.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer2.begin(ShapeRenderer.ShapeType.Line);

        if (hit) {
            for (int i = 0; i < hitLines.length; i++) {
                shapeRenderer1.line(
                        hitLines[i].x1,
                        hitLines[i].y1,
                        hitLines[i].x2,
                        hitLines[i].y2
                );
                shapeRenderer2.line(
                        hitLines[i].x1,
                        hitLines[i].y1,
                        hitLines[i].x2,
                        hitLines[i].y2
                );
            }
            shapeRenderer1.end();
            shapeRenderer2.end();
            return;
        }

        for (int i = 0, j = shapeX.length - 1; i < shapeX.length; j = i++) {
            shapeRenderer1.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }

        for (int i = 0, j = flameX.length - 1; i < flameX.length; j = i++) {
            shapeRenderer2.line(flameX[i], flameY[i], flameX[j], flameY[j]);
        }
        shapeRenderer1.end();
        shapeRenderer2.end();
    }

    public void respawn() {
        this.x = Gdx.graphics.getWidth() / 2.0f;
        this.y = Gdx.graphics.getHeight() / 2.0f;
        setShape();

        if (up)
            setFlame();

        wrap();
        hit = dead = false;
    }
}
