package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.ScoreScreen;
import com.mygdx.game.Screens.MenuScreen;

public class MyGdxGame extends Game {
	public static MenuScreen menuScreen;
	public static ScoreScreen scoreScreen;
	public static GameScreen gameScreen;
	public static GameOverScreen gameOverScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		scoreScreen = new ScoreScreen(this);
		menuScreen = new MenuScreen(this);
		gameOverScreen = new GameOverScreen(this);
		this.setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
