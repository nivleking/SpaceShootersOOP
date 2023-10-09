package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Entities.*;
import com.mygdx.game.Managers.GameInput;
import com.mygdx.game.Managers.GameKeys;
import com.mygdx.game.Managers.Saves;
import com.mygdx.game.Managers.Sounds;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

import static com.mygdx.game.MyGdxGame.gameOverScreen;
import static com.mygdx.game.MyGdxGame.scoreScreen;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    final MyGdxGame game;
    private Sounds sounds;

    private SpriteBatch spriteBatch;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;
    private BitmapFont pauseFont;

    //player's ship
    private ShapeRenderer shapeRenderer1;
    private ShapeRenderer shapeRenderer2;
    private Player player;
    private Player hudPlayer;

    //player's bullets
    private ArrayList<PlayerBullets> playerBullets;

    //asteroids
    private int level;
    private int totalAsteroids;
    private int totalAsteroidsLeft;
    private ArrayList<Asteroid> asteroids;

    //particles
    private ArrayList<Particle> particles;

    public GameScreen(MyGdxGame game) {
        this.spriteBatch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/EdgeOfTheGalaxyPoster-3zRAp.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter);

        this.game = game;
        this.camera = new OrthographicCamera();
        this.shapeRenderer1 = new ShapeRenderer();
        this.shapeRenderer2 = new ShapeRenderer();

        this.sounds = new Sounds();

        this.playerBullets = new ArrayList<>();
        this.player = new Player(playerBullets);
        this.asteroids = new ArrayList<>();
        this.hudPlayer = new Player(null);

        camera.setToOrtho(false, 800, 480);
        Gdx.input.setInputProcessor(new GameInput());

        particles = new ArrayList<>();

        level = 1;
        spawnAsteroids();
    }

    public void createParticles(float x, float y) {
        for (int i = 0; i < 6; i++) {
            particles.add(new Particle(x, y));
        }
    }

    public void spawnAsteroids() {
        asteroids.clear();
        int spawn = 4 + (level - 1);
        totalAsteroids = spawn * 7;
        totalAsteroidsLeft = totalAsteroids;

        for (int i = 0; i < spawn; i++) {
            float x = MathUtils.random(Gdx.graphics.getWidth());
            float y = MathUtils.random(Gdx.graphics.getHeight());

            float dX = x - player.getX();
            float dY = y - player.getY();
            float distance = (float) Math.sqrt(dX * dX + dY * dY);

            while (distance < 100) {
                x = MathUtils.random(Gdx.graphics.getWidth());
                y = MathUtils.random(Gdx.graphics.getHeight());
                dX = x - player.getX();
                dY = y - player.getY();
                distance = (float) Math.sqrt(dX * dX + dY * dY);
            }

            asteroids.add(new AsteroidLarge(x, y));
        }
    }

    public void splitAsteroids (Asteroid asteroid) {
        createParticles(asteroid.getX(), asteroid.getY());
        totalAsteroidsLeft--;
        if (asteroid instanceof AsteroidLarge) {
            for (int i = 0; i < 2; i++)
                asteroids.add(new AsteroidMedium(asteroid.getX(), asteroid.getY()));
        }

        if (asteroid instanceof AsteroidMedium) {
            for (int i = 0; i < 2; i++)
                asteroids.add(new AsteroidSmall(asteroid.getX(), asteroid.getY()));
        }
    }

    public void handleInput() {
        if (!player.isHit()) {
            player.setLeft(GameKeys.isDown(GameKeys.left));
            player.setRight(GameKeys.isDown(GameKeys.right));
            player.setUp(GameKeys.isDown(GameKeys.up));
        }
    }

    public void update (float delta) {
        handleInput();

        //next level
        if (asteroids.size() == 0) {
            this.level++;
            spawnAsteroids();
        }

        //player's ship
        player.update(delta);
        if (player.isDead()) {
            if (player.getLives() == 0) {
                sounds.stopSounds();
                MenuScreen.spaceMusic.stop();
                sounds.getSound9().play(0.1f);

                this.game.setScreen(gameOverScreen);
                scoreScreen.init();
                Saves.gameData.setTentativeScores(player.getScore());
                asteroids.clear();
                this.player.setScore(0);
                this.player.setLives(4);
                this.level = 1;
                spawnAsteroids();
                return;
            }
            player.respawn();
            player.loseLife();
            return;
        }

        //bullets
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).update(delta);
            if (playerBullets.get(i).remove()) {
                playerBullets.remove(i);
                i--;
            }
        }

        //asteroid
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(delta);
            if (asteroids.get(i).isRemove()) {
                asteroids.remove(i);
                i--;
            }
        }

        //particles
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update(delta);
            if (particles.get(i).isRemove()) {
                particles.remove(i);
                i--;
            }
        }

        //collisions
        checkCollisions();
    }

    public void checkCollisions() {
        //bullet dengan asteroid
        for (int i = 0; i< playerBullets.size(); i++) {
            PlayerBullets bullets = playerBullets.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid temp = asteroids.get(j);
                if (temp.contains(bullets.getX(), bullets.getY())) {
                    playerBullets.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(temp);
                    player.addScore(temp.getScore());
                    sounds.getSound0().play();
                    break;
                }
            }
        }

        //player dengan asteroid
        if (!player.isHit()) {
            for (int i = 0; i < asteroids.size(); i++) {
                Asteroid temp = asteroids.get(i);
                if (temp.intersects(player)) {
                    player.hit();
                    asteroids.remove(i);
                    i--;
                    splitAsteroids(temp);
                    sounds.getSound0().play();
                    break;
                }
            }
        }
    }

    @Override
    public void show() {
        //player
        player.draw(shapeRenderer1, shapeRenderer2);
        for (int i = 0; i < playerBullets.size(); i++)
            playerBullets.get(i).draw(shapeRenderer1);
        //asteroids
        for (int i =0; i < asteroids.size(); i++) {
            if (asteroids.get(i) instanceof AsteroidLarge) {
                ((AsteroidLarge)asteroids.get(i)).draw(shapeRenderer1);
            }
            if (asteroids.get(i) instanceof AsteroidMedium) {
                ((AsteroidMedium)asteroids.get(i)).draw(shapeRenderer1);
            }
            if (asteroids.get(i) instanceof AsteroidSmall) {
                ((AsteroidSmall)asteroids.get(i)).draw(shapeRenderer1);
            }
        }
        for (int i = 0; i < particles.size(); i++)
            particles.get(i).draw(shapeRenderer1);

        for (int i = 0; i < player.getLives(); i++) {
            hudPlayer.setPosition(50 + i * 20, 520);
            hudPlayer.draw(shapeRenderer1, shapeRenderer2);
        }

        spriteBatch.begin();
        font.draw(spriteBatch, Long.toString(player.getScore()), 45, 575);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        pauseFont = generator.generateFont(parameter);

        pauseFont.draw(spriteBatch, "Level " + level, 690, 580);
        spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!player.isHit())
            if (GameKeys.isPressed(GameKeys.space))
                player.shoot();

        GameKeys.update();
        update(Gdx.graphics.getDeltaTime());
        show();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        spriteBatch.dispose();
        font.dispose();
        pauseFont.dispose();
        shapeRenderer1.dispose();
        shapeRenderer2.dispose();

    }
}
