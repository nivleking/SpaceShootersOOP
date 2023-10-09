package com.mygdx.game.Managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInput extends InputAdapter {
    public boolean keyDown(int k) {
        if (k == Input.Keys.W || k == Input.Keys.UP)
            GameKeys.setKey(GameKeys.up, true);
        if (k == Input.Keys.A || k == Input.Keys.LEFT)
            GameKeys.setKey(GameKeys.left, true);
        if (k == Input.Keys.S || k == Input.Keys.DOWN)
            GameKeys.setKey(GameKeys.down, true);
        if (k == Input.Keys.D || k == Input.Keys.RIGHT)
            GameKeys.setKey(GameKeys.right, true);
        if (k == Input.Keys.ENTER)
            GameKeys.setKey(GameKeys.enter, true);
        if (k == Input.Keys.ESCAPE)
            GameKeys.setKey(GameKeys.escape, true);
        if (k == Input.Keys.SPACE)
            GameKeys.setKey(GameKeys.space, true);
        if (k == Input.Keys.SHIFT_LEFT || k == Input.Keys.SHIFT_RIGHT)
            GameKeys.setKey(GameKeys.shift, true);
        return true;
    }

    public boolean keyUp(int k) {
        if (k == Input.Keys.W || k == Input.Keys.UP)
            GameKeys.setKey(GameKeys.up, false);
        if (k == Input.Keys.A || k == Input.Keys.LEFT)
            GameKeys.setKey(GameKeys.left, false);
        if (k == Input.Keys.S || k == Input.Keys.DOWN)
            GameKeys.setKey(GameKeys.down, false);
        if (k == Input.Keys.D || k == Input.Keys.RIGHT)
            GameKeys.setKey(GameKeys.right, false);
        if (k == Input.Keys.ENTER)
            GameKeys.setKey(GameKeys.enter, false);
        if (k == Input.Keys.ESCAPE)
            GameKeys.setKey(GameKeys.escape, false);
        if (k == Input.Keys.SPACE)
            GameKeys.setKey(GameKeys.space, false);
        if (k == Input.Keys.SHIFT_LEFT || k == Input.Keys.SHIFT_RIGHT)
            GameKeys.setKey(GameKeys.shift, false);
        return true;
    }
}
