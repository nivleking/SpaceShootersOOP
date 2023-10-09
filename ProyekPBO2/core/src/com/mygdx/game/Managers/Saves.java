package com.mygdx.game.Managers;

import java.io.*;

public class Saves {
    public static GameData gameData;

    public static void initialize() {
        gameData = new GameData();
        save();
    }

    public static void save() {
        try {
            ObjectOutputStream ooS = new ObjectOutputStream(new FileOutputStream(
                    "scores.bin"
            ));

            ooS.writeObject(gameData);
            ooS.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try{
            if (!saveFileExists()) {
                initialize();
                return;
            }

            ObjectInputStream oiS = new ObjectInputStream(new FileInputStream(
                    "scores.bin"
            ));
            gameData = (GameData) oiS.readObject();
            oiS.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean saveFileExists() {
        File file = new File("scores.bin");
        return file.exists();
    }
}
