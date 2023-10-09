package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class Sounds {
    private Sound[] sounds;

    public Sounds() {
        sounds = new Sound[10];

        sounds[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/extralife.ogg"));
        sounds[6] = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.ogg"));
        sounds[8] = Gdx.audio.newSound(Gdx.files.internal("sounds/thruster.ogg"));
        sounds[9] = Gdx.audio.newSound(Gdx.files.internal("sounds/spongebob.ogg"));
    }

    public void stopSounds() {
        sounds[0].stop();
        sounds[1].stop();
        sounds[6].stop();
        sounds[8].stop();
        sounds[9].stop();
    }

    public Sound getSound0() {
        int temp =MathUtils.random(0, 1);
        if (temp == 0)
            sounds[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/ugh1.ogg"));
        else
            sounds[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/ugh2.ogg"));
        return sounds[0];
    }

    public Sound getSound1() {
        return sounds[1];
    }

    public Sound getSound6() {
        return sounds[6];
    }

    public Sound getSound8() {
        return sounds[8];
    }

    public Sound getSound9() {
        return sounds[9];
    }
}

