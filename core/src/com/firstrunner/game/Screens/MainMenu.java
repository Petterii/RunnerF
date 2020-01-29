package com.firstrunner.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Objects.Hills;

import static com.firstrunner.game.Globals.TEXTURE_MAINMENU;

public class MainMenu implements Screen {

    private Firstrunner game;
    private Texture bg;
    private TextureRegion bgr;
    private OrthographicCamera cam;
    private Viewport viewport;

    public MainMenu(Firstrunner game) {
    this.game = game;
    bg = game.getManager().get(TEXTURE_MAINMENU);
    bgr = new TextureRegion(bg,437,286);
    cam = new OrthographicCamera(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);
    viewport = new FitViewport(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT,cam);
    viewport.apply();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.batch.draw(bgr,0,0,Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);
        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
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
        bg.dispose();
    }
}
