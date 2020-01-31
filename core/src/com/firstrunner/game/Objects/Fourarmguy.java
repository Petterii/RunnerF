package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.firstrunner.game.Helpers.CreateWorldRandomized;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.GROUND_BIT;
import static com.firstrunner.game.Globals.ITEM_BIT;
import static com.firstrunner.game.Globals.PLAYER_BIT;
import static com.firstrunner.game.Globals.PPM;
import static com.firstrunner.game.Globals.TEXTURE_BOUNCINGBALL;
import static com.firstrunner.game.Globals.TEXTURE_DOUBLEENEMY;
import static com.firstrunner.game.Globals.WALL_DESTROYER;

public class Fourarmguy extends Items {

    private CreateWorldRandomized game;
    private Texture texture;
    private TextureRegion textureRegion;

    public Fourarmguy(CreateWorldRandomized game,float x,float y) {
        this.game = game;
        defineBody(x,y);

        texture = (Texture)game.getGameScreen().getManager().get(TEXTURE_DOUBLEENEMY);
        textureRegion = new TextureRegion(texture,0,0,80,128);

        //setOrigin(-32f/PPM,-32f/PPM);
        setBounds(0,0,20f/PPM,50f/PPM);
        setRegion(textureRegion);
        setPosition(body.getPosition().x-(10f/PPM),body.getPosition().y-(10f/PPM));
        balls = new Array<>();
        timerBetweenBallSpawns = 0;
    }

    private BodyDef myBodyDef;
    private FixtureDef fd;
    private Fixture fixture;
    private Body body;


    private float timerBetweenBallSpawns;

    @Override
    public Items collition() {
        return this;

    }

    public void update(float dt){
        timerBetweenBallSpawns += dt;
        if (timerBetweenBallSpawns > 2f)
            spawnBall();
        for (BouncingBall b:balls) {
            b.update(dt);
        }

        if (toDestroy){
            game.getWorld().destroyBody(body);
            isDestroyed = true;
        }
    }

    @Override
    public void enableDestroy() {
        this.toDestroy = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    private Array<BouncingBall> balls;
    private void spawnBall() {
        timerBetweenBallSpawns = 0f;
        game.addObject(new BouncingBall(game,body.getPosition().x,body.getPosition().y));
    }

    public Body getBody(){
        return body;
    }

    private void defineBody(float x,float y){
        int radius = 10;

        myBodyDef = new BodyDef();
        fd = new FixtureDef();

        myBodyDef.type = BodyDef.BodyType.StaticBody; //this will be a dynamic body
        myBodyDef.position.set( x, y); //a little to the left

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10f/PPM,10f/PPM);
        fd.shape = polygonShape;
        fd.filter.categoryBits = ITEM_BIT;
        fd.filter.maskBits = PLAYER_BIT | GROUND_BIT | WALL_DESTROYER;
        fd.isSensor = true;
        body = GameScreen.world.createBody(myBodyDef);

        fixture = body.createFixture(polygonShape,1);
        fixture.setFilterData(fd.filter);
        fixture.setUserData(this);

        polygonShape.dispose();


    }

    @Override
    public void draw(Batch batch) {

        super.draw(batch);
    }
}
