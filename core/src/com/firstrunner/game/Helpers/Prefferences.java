package com.firstrunner.game.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Prefferences {

    public static void savePreffs(int highScore){
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putInteger("score", highScore);
        prefs.flush();
    }

    public static int getPreffsHighScore(){
        int highScores = 0;

        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        highScores = prefs.getInteger("score",0);

        return highScores;
    }

}
