package com.firstrunner.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewPort;

    private static Integer highScore;
    private static Integer score;
    static Label scoreLabel;

    public Hud(SpriteBatch sb){

        score = 0;

        viewPort = new FitViewport(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(0.8f);


        table.add(scoreLabel).padTop(20f);

        stage.addActor(table);

    }

    public static int getScore() {
        return score;
    }

    public void update(float dt) {

    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static Integer getHighScore() {
        return highScore;
    }

    public static void setHighScore(Integer highScore) {
        Hud.highScore = highScore;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
