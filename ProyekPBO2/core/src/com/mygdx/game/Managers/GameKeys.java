package com.mygdx.game.Managers;

public class GameKeys {
    private static boolean[] keys = new boolean[8];
    private static boolean[] prevKeys = new boolean[8];

    private static final int NUM_KEYS = 8;
    public static final int up = 0;
    public static final int left = 1;
    public static final int down = 2;
    public static final int right = 3;
    public static final int enter = 4;
    public static final int escape = 5;
    public static final int space = 6;
    public static final int shift = 7;

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++)
            prevKeys[i] = keys[i];
    }

    public static void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public static boolean isDown(int k) {
        return keys[k];
    }

    public static boolean isPressed(int k) {
        return !keys[k] && prevKeys[k];
    }
}

