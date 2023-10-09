package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Managers.GameKeys;
import com.mygdx.game.Managers.Saves;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.menuScreen;

public class ScoreScreen implements Screen {
    final MyGdxGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont scoreFont;
    private FreeTypeFontGenerator generator;
    OrthographicCamera camera;

    private long[] scores;
    private String[] names;

    public ScoreScreen(MyGdxGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/awekening.ttf"));
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        font = generator.generateFont(parameter);

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/EdgeOfTheGalaxyPoster-3zRAp.otf"));
        parameter.size = 35;
        scoreFont = generator.generateFont(parameter);
        init();
    }

    public void init() {
        Saves.load();
        this.scores = Saves.gameData.getScores();
        this.names = Saves.gameData.getNames();
    }

    @Override
    public void show() {
        batch.begin();
        String score;

        score = "Scores";

        //title
        font.draw(batch, score,
                ((Gdx.graphics.getWidth()) / 2.0f) - 70,
                Gdx.graphics.getHeight() * 0.9f);

        //score-score
        for (int i = 0; i < scores.length; i++) {
            score = String.format(
                    "%2d. %07d %s",
                    i + 1,
                    this.scores[i],
                    this.names[i]
            );
            scoreFont.draw(batch, score,
                    ((Gdx.graphics.getWidth()) / 2.0f) - 85,
                    Gdx.graphics.getHeight() * 0.74f - 40 * i
            );
        }
        batch.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (GameKeys.isPressed(GameKeys.enter) || GameKeys.isPressed(GameKeys.escape)) {
            this.game.setScreen(menuScreen);
        }

        GameKeys.update();
        update(Gdx.graphics.getDeltaTime());
        show();
    }

    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {

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
        batch.dispose();
        font.dispose();
        scoreFont.dispose();
    }
}
