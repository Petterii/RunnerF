package com.firstrunner.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;

import static com.firstrunner.game.Globals.*;


public class NewGameScreen implements Screen {

    private Firstrunner game;

    private Viewport viewport;
    private Label playButton;
    private Stage stage;

    private Texture background;

    public NewGameScreen(Firstrunner game){
        this.game = game;
        this.background = game.getManager().get(BACKGROUND_MENU);

        viewport = new FitViewport(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,this.game.batch);

        tableSetup();
    }

    private void tableSetup() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameTitle = new Label("First Runner", font);
        playButton = new Label("Click to play!", font);

        table.add(gameTitle).expandX().padTop(40f);
        table.row();
        table.add(playButton).expandX().padTop(10f);

        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear screen after each frame
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0,Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);
        stage.getBatch().end();

        stage.draw();

        handleInputs();
    }

    private void handleInputs() {
        if (Gdx.input.justTouched()){
            game.setScreen(new GameScreen((Firstrunner) game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
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
        stage.dispose();
        background.dispose();

    }
}
