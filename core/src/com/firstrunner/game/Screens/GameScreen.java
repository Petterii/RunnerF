package com.firstrunner.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.Background;
import com.firstrunner.game.Helpers.CreateWorldFromTiled;
import com.firstrunner.game.Helpers.CreateWorldRandomized;
import com.firstrunner.game.Helpers.WallDestroyer;
import com.firstrunner.game.Helpers.WorldContactListener;
import com.firstrunner.game.Objects.Items;
import com.firstrunner.game.Objects.Player;

import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import static com.firstrunner.game.Globals.*;

public class GameScreen implements Screen {

    private Firstrunner game;

    private OrthographicCamera gamecam;

    public static World world;
    private Box2DDebugRenderer b2dr;

    private TmxMapLoader mapLoad;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Viewport viewB2Dport; // viewB2Dport for 2dworld
    private Viewport viewBGFXport; // background

    private OrthographicCamera graphicCam;
    private Player player;
    private static ArrayList<Items> items;
    private AssetManager manager;
    private WallDestroyer wallDestroyer;
    private boolean gameover;

    private SpriteBatch sb;
    private  Music music;

    public AssetManager getManager() {
        return manager;
    }

    public void addItem(Items item){
        items.add(item);
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    public GameScreen(Firstrunner game) {
        this.game = game;
        gameStarted = false;
        this.manager = game.getManager();
        playerSpeed = 1;
        speed = -110f;
        items = new ArrayList<>();
        gamecam = new OrthographicCamera();
        viewB2Dport = new FitViewport(Firstrunner.FR_WIDTH/PPM,Firstrunner.FR_HEIGHT/PPM,gamecam);
        viewB2Dport.apply();
        mapLoad = new TmxMapLoader();
        map = mapLoad.load("runner.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,(float)1/PPM);

        gamecam.position.set(viewB2Dport.getWorldWidth()/2,viewB2Dport.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10f),true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(this);
        clicktogo = (Texture) getManager().get(TEXTURE_CLICKTOGO);

        sb = new SpriteBatch();

        graphicCam = new OrthographicCamera();
        viewBGFXport = new FitViewport(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT,graphicCam);
        viewBGFXport.apply();
        background1 = (Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS);
        background2 = (Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS);

        // new CreateWorldFromTiled(this);

       world.setContactListener(new WorldContactListener());

        randomWorld = new CreateWorldRandomized(this);

        wallDestroyer = new WallDestroyer(this);
        gamecam.position.x = player.getMainBody().getPosition().x;

        music = game.getManager().get(MUSIC_LOOP);
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();

        bgOffset1 = -400.0f;
        bgOffset2 = 400.0f;
    }


    private  CreateWorldRandomized randomWorld;

    public void backgroundScrollingupdate(float delta){
        bgOffset1 = bgOffset1+(delta*speed*playerSpeed);
        bgOffset2 = bgOffset2+(delta*speed*playerSpeed);
        if (bgOffset1 <= -1200)
            bgOffset1 = bgOffset2 + 799;
        else if (bgOffset2 <= -1200)
            bgOffset2 = bgOffset1 + 799;

    }

    private void update(float delta){
        world.step(1.0f/60.0f,6,2);
        if (!player.isStarted() || (player.isStarted() && gameStarted))
            player.update(delta);
        randomWorld.update(delta);
        wallDestroyer.update(delta);
        if (gameStarted)
            backgroundScrollingupdate(delta);

        for (Items item : items) {
            if (!item.isDestroyed())
                item.update(delta);
        }
            gamecam.position.x = player.getMainBody().getPosition().x;
            gamecam.update();
            renderer.setView(gamecam);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }


    @Override
    public void show() {


    }

    private Texture background1;
    private Texture background2;
    private Texture clicktogo;
    private float bgOffset1;
    private float bgOffset2;

    private float backgroundX;
    private float speed;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

      //  renderer.render();

        //b2dr.render(world,gamecam.combined);
        sb.setProjectionMatrix(graphicCam.combined);
        sb.begin();
        sb.draw(background1,bgOffset1,-Firstrunner.FR_HEIGHT/2);
        sb.draw(background2,bgOffset2,-Firstrunner.FR_HEIGHT/2);
        if (!gameStarted) {
            sb.draw(clicktogo, 100f, 100f);
          }
        sb.end();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        randomWorld.draw(game.batch);
        player.draw(game.batch);

        game.batch.end();


        handleinput(delta);

        if (gameover){
            game.setScreen(new GameScreen(game));
        }
    }

    public static float playerSpeed;
    private static boolean gameStarted;

    private void handleinput(float delta) {
        if (Gdx.input.isTouched()){
            if (gameStarted)
                player.addForce(delta);
            else
                gameStarted = true;
        }

    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    @Override
    public void resize(int width, int height) {
        viewB2Dport.update(width,height,false);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        music.dispose();
    }

    public float getPlayerX() {
        return player.getX();
    }
}
