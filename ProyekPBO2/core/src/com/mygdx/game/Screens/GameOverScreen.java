package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Managers.GameKeys;
import com.mygdx.game.Managers.Saves;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.menuScreen;

public class GameOverScreen implements Screen {
    final MyGdxGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private char[] newName;
    private int currentChar;

    private FreeTypeFontGenerator generator;
    private BitmapFont gameOverFont;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public GameOverScreen(MyGdxGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.newName = new char[] {'A', 'A', 'A'};
        this.currentChar = 0;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/awekening.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;

        this.gameOverFont = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/EdgeOfTheGalaxyPoster-3zRAp.otf"));
        parameter.size = 35;
        this.font = generator.generateFont(parameter);
    }

    @Override
    public void show() {
        batch.begin();

        String title;

        title = "GAME OVER";

        gameOverFont.draw(batch, title,
                ((Gdx.graphics.getWidth()) / 2.0f) - 125,
                Gdx.graphics.getHeight() * 0.55f
                );

        title = "NEW SCORE: " + Saves.gameData.getTentativeScores();
        font.draw(batch, title,
                300,
                Gdx.graphics.getHeight() * 0.4f
        );

        for (int i = 0; i < newName.length; i++) {
            font.draw(batch, Character.toString(newName[i]),
                    370 + 25 * i,
                    200
            );
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1,0,0,1);
        shapeRenderer.line(370 + 25 * currentChar,
                160,
                384 + 25 * currentChar,
                160
                );
        shapeRenderer.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (GameKeys.isPressed(GameKeys.enter)) {
            Saves.gameData.addScore(Saves.gameData.getTentativeScores(), new String(newName));
            Saves.save();
            this.game.setScreen(menuScreen);
            MenuScreen.spaceMusic.play();
            MenuScreen.spaceMusic.setLooping(true);
            MenuScreen.spaceMusic.setVolume(0.03f);
        }

        if (GameKeys.isPressed(GameKeys.up)) {
            if (newName[currentChar] == ' ') {
                newName[currentChar] = 'Z';
            }
            else {
                newName[currentChar]--;
                if (newName[currentChar] < 'A')
                    newName[currentChar] = ' ';
            }
        }

        if (GameKeys.isPressed(GameKeys.down)) {
            if (newName[currentChar] == ' ') {
                newName[currentChar] = 'A';
            } else {
                newName[currentChar]++;
                if (newName[currentChar] > 'Z')
                    newName[currentChar] = ' ';
            }
        }

        if (GameKeys.isPressed(GameKeys.right)) {
            if (currentChar < newName.length - 1) {
                currentChar++;
            }
        }

        if (GameKeys.isPressed(GameKeys.left)) {
            if (currentChar > 0) {
                currentChar--;
            }
        }

        update(Gdx.graphics.getDeltaTime());
        GameKeys.update();
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
        game.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        gameOverFont.dispose();
        font.dispose();
    }
}
