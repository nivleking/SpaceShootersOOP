package com.mygdx.game.Managers;

import java.io.Serializable;

public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int MAX_SCORE = 10;
    private long[] scores;
    private String[] names;

    private long tentativeScores;

    public GameData() {
        this.scores = new long[MAX_SCORE];
        this.names = new String[MAX_SCORE];

        for (int i = 0; i < MAX_SCORE; i++) {
            scores[i] = 0;
            names[i] = "---";
        }
    }

    public long[] getScores() {
        return this.scores;
    }

    public String[] getNames() {
        return this.names;
    }

    public long getTentativeScores() {
        return this.tentativeScores;
    }

    public void setTentativeScores(long tentativeScores) {
        this.tentativeScores = tentativeScores;
    }

    public void addScore(long newScore, String name) {
        if (this.scores[9] != 0) {
            this.scores[9] =0;
        }

        for (int i = 0; i < MAX_SCORE;i++) {
            if (this.scores[i] == 0) {
                this.scores[i] = newScore;
                this.names[i] = name;
                break;
            }
        }
    }
}
