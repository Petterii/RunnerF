package com.firstrunner.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.CreateWorldFromTiled;
import com.firstrunner.game.Helpers.WorldContactListener;
import com.firstrunner.game.Objects.Items;
import com.firstrunner.game.Objects.Player;

import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import static com.firstrunner.game.Globals.*;

public class GameScreen implements Screen {

    private Firstrunner game;

    private OrthographicCamera gamecam;

    private World world;
    private Box2DDebugRenderer b2dr;

    private TmxMapLoader mapLoad;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Viewport viewB2Dport; // viewB2Dport for 2dworld
    private OrthographicCamera graphicCam;
    private Player player;
    private static ArrayList<Items> items;
    private AssetManager manager;

    public AssetManager getManager() {
        return manager;
    }

    public void addItem(Items item){
        items.add(item);
    }

    public GameScreen(Firstrunner game) {
        this.game = game;
        this.manager = game.getManager();
        items = new ArrayList<>();
        gamecam = new OrthographicCamera();
        viewB2Dport = new FitViewport(Firstrunner.FR_WIDTH/PPM,Firstrunner.FR_HEIGHT/PPM,gamecam);
        viewB2Dport.apply();

        mapLoad = new TmxMapLoader();
        map = mapLoad.load("runner.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,(float)1/PPM);

        gamecam.position.set(viewB2Dport.getWorldWidth()/2,viewB2Dport.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-9f),true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(this);

        // for tilerenderer
        graphicCam = new OrthographicCamera();

       new CreateWorldFromTiled(this);

       world.setContactListener(new WorldContactListener());

    }

    private void update(float delta){
        world.step(1/60f,6,2);

            player.update(delta);

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        renderer.render();

        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);

        handleinput(delta);
    }

    private void handleinput(float delta) {
        if (Gdx.input.isTouched()){
            player.addForce(delta);
        }

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

    }
}
