package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Entities.Asteroid;
import com.mygdx.game.Entities.AsteroidLarge;
import com.mygdx.game.Managers.GameKeys;
import com.mygdx.game.Managers.Saves;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

import static com.mygdx.game.MyGdxGame.gameScreen;
import static com.mygdx.game.MyGdxGame.scoreScreen;

public class MenuScreen implements Screen {
    final MyGdxGame game;
    private SpriteBatch batch;
    private FreeTypeFontGenerator generator;
    private BitmapFont titleFont;
    private BitmapFont menuOptionFont;
    OrthographicCamera camera;

    private ArrayList<Asteroid> asteroids;
    private ShapeRenderer shapeRenderer;

    private final String title = "Space Asteroids";

    private int currentOption;
    private String[] menuOptions;

    public static Music spaceMusic;

    public MenuScreen(MyGdxGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/awekening.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(1,1,1,1);

        parameter.size = 40;
        menuOptionFont = generator.generateFont(parameter);
        menuOptions = new String[] {
                "PLAY",
                "SCORES",
                "EXIT"
        };

        spaceMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mega.mp3"));
        spaceMusic.setLooping(true);
        spaceMusic.setVolume(0.03f);
        spaceMusic.play();

        this.asteroids = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            asteroids.add(new AsteroidLarge(
                    MathUtils.random(Gdx.graphics.getWidth()),
                    MathUtils.random(Gdx.graphics.getHeight())
            ));
        }

        Saves.load();
    }

    @Override
    public void show() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(shapeRenderer);
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //title
        titleFont.draw(batch, title,
                255,
                Gdx.graphics.getHeight() * 0.60f);


        //menus
        int temp = 0;
        for (int i = 0; i < menuOptions.length; i++) {
            if (currentOption == i) menuOptionFont.setColor(1,0,0,1);
            else menuOptionFont.setColor(1, 1, 1, 1);
            if (temp == 2) temp = 0;
            menuOptionFont.draw(batch, menuOptions[i],
                    383 - 18 * temp,
                    Gdx.graphics.getHeight() * 0.4f - 50 * i
            );
            temp++;
        }
        batch.end();

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (GameKeys.isPressed(GameKeys.up)) {
            if (currentOption > 0) currentOption--;
        }
        if (GameKeys.isPressed(GameKeys.down)) {
            if (currentOption < menuOptions.length - 1) {
                currentOption++;
            }
        }
        if (GameKeys.isPressed(GameKeys.enter)) {
            select();
        }

        GameKeys.update();
        update(Gdx.graphics.getDeltaTime());
        show();
    }

    public void update(float delta) {
        handleInput();
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(delta);
        }
    }

    public void handleInput() {

    }

    private void select() {
        if (currentOption == 0) {
            game.setScreen(gameScreen);
        }
        else if (currentOption == 1)
            game.setScreen(scoreScreen);
        else if (currentOption == 2)
            System.exit(0);
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
        batch.dispose();
        shapeRenderer.dispose();
        titleFont.dispose();
        menuOptionFont.dispose();
        spaceMusic.dispose();
    }
}
