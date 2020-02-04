package com.firstrunner.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.Background;
import com.firstrunner.game.Helpers.CreateWorldFromTiled;
import com.firstrunner.game.Helpers.CreateWorldRandomized;
import com.firstrunner.game.Helpers.Prefferences;
import com.firstrunner.game.Helpers.WallDestroyer;
import com.firstrunner.game.Helpers.WorldContactListener;
import com.firstrunner.game.Objects.BouncingBall;
import com.firstrunner.game.Objects.FloatingText;
import com.firstrunner.game.Objects.Fourarmguy;
import com.firstrunner.game.Objects.Hills;
import com.firstrunner.game.Objects.Items;
import com.firstrunner.game.Objects.Player;

import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import box2dLight.PointLight;
import box2dLight.RayHandler;

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

    private Hud hud;

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
        fts = new Array<>();
        this.game = game;
        this.hud = new Hud(game.batch);
        gameStarted = false;
        this.manager = game.getManager();
        playerSpeed = 1;
        speed = .1f;
        items = new ArrayList<>();
        playerdead = false;
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
        background1 = (Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS);
        background2 = (Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS);
        graphicCam = new OrthographicCamera();
        viewBGFXport = new FitViewport(Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT,graphicCam);
        viewBGFXport.apply();
        bg = new Hills((Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS),1,false);
        bg1 = new Hills((Texture)game.getManager().get(TEXTURE_BACKGROUNDENDLESS),2,false);

        // backgroundC = new Background(this);
        // new CreateWorldFromTiled(this);

       world.setContactListener(new WorldContactListener());

        randomWorld = new CreateWorldRandomized(this);

        wallDestroyer = new WallDestroyer(this);
        gamecam.position.x = player.getMainBody().getPosition().x;

        music = game.getManager().get(MUSIC_LOOP);
        music.setLooping(true);
        music.setVolume(0.09f);
        music.play();

        float width = bg.getWidth();
        bgOffset1 = -bg.getWidth();
        bgOffset2 = 0;
        initLights();
        initializeFonts();

        //particleeffect();
    }
    ParticleEffect pe;
    public void particleeffect(float x,float y) {
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particles/speed_particle.pe"),Gdx.files.internal(""));
        pe.scaleEffect((1.0f/PPM)*0.3f);
        pe.getEmitters().first().setPosition(x,y);
        pe.start();
    }

    FloatingText ft;

    private Array<FloatingText> fts;

    public void addFT(int points, float x,float y){
        ft = new FloatingText(this, points,x,y);
        fts.add(ft);
    }

    private Fourarmguy enemy;
    private RayHandler rayHandler;

    PointLight pl;
    private void initLights() {
        rayHandler = new RayHandler(world);
        pl = new PointLight(rayHandler, 128, new Color(0.2f,1,0,1f), 5,-5,2);
        //PointLight pl2 = new PointLight(rayHandler, 128, new Color(1,0,1,1f), 10,5,2);

        rayHandler.setShadows(false);
        rayHandler.setAmbientLight(0.5f);
        short mb = -1;
        pl.setContactFilter(mb,mb,(short)(GROUND_BIT | PLAYER_BIT));
        pl.setStaticLight(false);
        pl.setSoft(true);

    }

    private Hills bg,bg1;
    private final float bgwidth = 437f;
    private Background backgroundC;

    private  CreateWorldRandomized randomWorld;

    public void backgroundScrollingupdate(float delta){
    float width = bg.getWidth();
        if (bgOffset1 <= -bg.getWidth()-(Firstrunner.FR_WIDTH/2))
            bgOffset1 = bgOffset2 + bgwidth-1f;
        else if (bgOffset2 <= -bg.getWidth()-(Firstrunner.FR_WIDTH/2))
            bgOffset2 = bgOffset1 + bgwidth-1f;
        bgOffset1 = bgOffset1-1f*speed;
        bgOffset2 = bgOffset2-1f*speed;
    }

    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    private void initializeFonts(){
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_ITALIC));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 11;
        fontParameter.borderWidth = 3;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
    }

    private void update(float delta){
        world.step(1.0f/60.0f,6,2);
        if (pe != null){
            pe.setPosition(player.getMainBody().getPosition().x,player.getMainBody().getPosition().y);
            pe.update(delta);
        }

        if (!player.isStarted() || (player.isStarted() && gameStarted) ) {
            player.update(delta);
        }
        if (!playerdead)
            randomWorld.update(delta);
        wallDestroyer.update(delta);
        if (gameStarted && !playerdead)
            backgroundScrollingupdate(delta);

        for (Items item : items) {
            if (!item.isDestroyed())
                item.update(delta);
        }

        for (FloatingText ft : fts) {
            ft.udpate(delta, gamecam.position.x,gamecam.position.y);
        }

        for (int i = 0; i < fts.size; i++) {
            if (fts.get(i).isDestroyed()) {
                fts.removeIndex(i);
            }
        }


        if (!playerdead)
            gamecam.position.x = player.getMainBody().getPosition().x;
        gamecam.update();
        bg.update(delta,bgOffset1);
        bg1.update(delta,bgOffset2);
        graphicCam.update();
        if (gameStarted && !playerdead)
            hud.update(delta);

        //renderer.setView(gamecam);
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
      //  backgroundC.update(delta);
      //  renderer.render();


        sb.setProjectionMatrix(graphicCam.combined);
        sb.begin();
        bg.draw(sb);
        bg1.draw(sb);
        sb.end();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        // backgroundC.draw(game.batch);
        randomWorld.draw(game.batch);

        if (pe != null)
            pe.draw(game.batch);
        player.draw(game.batch);
        game.batch.end();
        hud.stage.draw();

        sb.begin();

        if (!gameStarted) {
            sb.draw(clicktogo, -200f, -150f);
        }
        if (playerdead) {
            Hud.setHighScore(Prefferences.getPreffsHighScore());
            if (Prefferences.getPreffsHighScore() < Hud.getScore()) {
                Prefferences.savePreffs(Hud.getScore());
                Hud.setHighScore(Hud.getScore());
            }
            font.draw(sb,"HighScore: "+ Hud.getHighScore(),10f,20f);
            font.draw(sb,"Score: "+ Hud.getScore(),10f,-20f);
            sb.draw((Texture) manager.get(TEXTURE_RETRYBUTTON), -Firstrunner.FR_WIDTH / 3f, -30f);
        }

        for (FloatingText ft1 : fts) {
            ft1.draw(sb);
        }
        sb.end();


        handleinput(delta);

        if (gameover){
            playerdead = true;
            //game.setScreen(new GameScreen(game));
        }
        pl.setPosition(player.getMainBody().getPosition().x-2.4f,2f);
        rayHandler.setCombinedMatrix(gamecam.combined,player.getMainBody().getPosition().x-0.5f,0,player.getMainBody().getPosition().x-0.5f,-1f);
        rayHandler.updateAndRender();

        if (DEBUGGER_BOX2D_ENABLED)
            b2dr.render(world,gamecam.combined);
    }

    private boolean playerdead;
    public static float playerSpeed;
    private static boolean gameStarted;

    private void handleinput(float delta) {
        if (Gdx.input.isTouched()){
            float positionPressedX = Gdx.input.getX();
            float positionPressedY = Gdx.input.getY();

            Gdx.app.log("pressing","X:"+positionPressedX + "  Y:"+positionPressedY);
            if (positionPressedX < Gdx.graphics.getWidth()/2 && playerdead)
                 game.setScreen(new GameScreen(game));

            else if (gameStarted && !playerdead)
                player.addForce(delta);
            else if(!playerdead)
                gameStarted = true;
        }

    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    @Override
    public void resize(int width, int height) {
        viewBGFXport.update(width,height,false);
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
        rayHandler.dispose();
    }

    public float getPlayerX() {
        return player.getX();
    }
}
