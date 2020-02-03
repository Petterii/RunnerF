package com.firstrunner.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Objects.Hills;
import com.firstrunner.game.Objects.MainBall;


import static com.firstrunner.game.Globals.TEXTURE_BACKGROUNDENDLESS;
import static com.firstrunner.game.Globals.TEXTURE_MAINMENU;
import static com.firstrunner.game.Globals.TEXTURE_TITLETEXT;

public class MainMenu implements Screen {

    private Firstrunner game;
    private Hills bg,bg1;
    private TextureRegion bgr;
    private OrthographicCamera cam;
    private Viewport viewport;
    private MainBall ball;
    private Texture title;

    public MainMenu(Firstrunner game) {
    this.game = game;
    this.speed = 1f;
    bg = new Hills((Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS),1,true);
    bg1 = new Hills((Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS),2,true);
    ball = new MainBall(this);
    bgOffset1 = -bg.getWidth();
    bgOffset2 = 0;
    title = game.getManager().get(TEXTURE_TITLETEXT);
    bgr = new TextureRegion(title,437,286);
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
        ball.update(delta);
        bg.update(delta,bgOffset1);
        bg1.update(delta,bgOffset2);

        if (ball.isOutside())
            game.setScreen(new GameScreen(game));
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        bg.draw(game.batch);
        bg1.draw(game.batch);
        ball.draw(game.batch);
         game.batch.draw(bgr,Firstrunner.FR_WIDTH/7,-Firstrunner.FR_HEIGHT/4,Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);

        game.batch.end();

        handleInput();

        backgroundScrollingupdate(delta);
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
           // game.setScreen(new GameScreen(game));
            if (!ball.isMoving())
                ball.enablemovement();
        }
    }

    private float speed;
    private final float bgwidth = 437f;
    private float bgOffset1;
    private float bgOffset2;

    public void backgroundScrollingupdate(float delta){
        float width = bg.getWidth();
        if (bgOffset1 <= -bg.getWidth())
            bgOffset1 = bgOffset2 + bgwidth-1f;
        else if (bgOffset2 <= -bg.getWidth())
            bgOffset2 = bgOffset1 + bgwidth-1f;
        bgOffset1 = bgOffset1-1f*speed;
        bgOffset2 = bgOffset2-1f*speed;
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

    }

    public Firstrunner getGame() {
        return game;
    }
}
