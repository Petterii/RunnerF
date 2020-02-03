package com.firstrunner.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.Prefferences;

import static com.firstrunner.game.Globals.FONT_ITALIC;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewPort;

    private static Integer highScore;
    private static Integer score;
    static Label scoreLabel;
    static Label highscoreLabel;

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;

    public Hud(SpriteBatch sb){

        score = 0;

        viewPort = new FitViewport(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);



        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_ITALIC));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 11;
        fontParameter.borderWidth = 3;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        scoreLabel = new Label(String.format("Your: %d", getScore()), labelStyle);
        scoreLabel.setFontScale(0.8f);

        highscoreLabel = new Label(String.format("Highscore: %d", getHighScore()), labelStyle);
        highscoreLabel.setFontScale(0.8f);

        table.add(scoreLabel).padTop(20f);
        table.add(highscoreLabel).padTop(20f).padLeft(40f);

        stage.addActor(table);
        timer = 0.0f;
    }

    public static int getScore() {
        return score;
    }

    float timer;
    public void update(float dt) {
        timer += dt;
        if (timer > 1.0f){
            addScore(1);
            timer = 0.0f;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("Your: %d", score));
    }

    public static Integer getHighScore() {
        highScore = Prefferences.getPreffsHighScore();
        if (highScore == null)
            highScore = 0;
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
