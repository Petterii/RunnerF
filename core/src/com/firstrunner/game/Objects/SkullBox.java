package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.firstrunner.game.Helpers.CustomBody;
import com.firstrunner.game.Screens.GameScreen;

import java.util.Random;

import static com.firstrunner.game.Globals.*;

public class SkullBox extends Items {

    private int index;
    private CustomBody mainBody;
    private float posX,posY;
    private static int counter;
 //   private GameScreen game;
   // private World world;
    private Texture texture;
    private TextureRegion textureRegion;
    private GameScreen screen;
    private int points;


    public Body getBody(){
        return body;
    }

    public SkullBox(GameScreen screen, float x,float y) {
        // this.game = game;
        //this.world = world;
        this.screen = screen;
        posX = x;
        posY = y;
        points = 20;
        stateTimer = 0;
        toDestroy = false;
        defineBody();

        texture = (Texture)screen.getManager().get(TEXTURE_CRATE);
        textureRegion = new TextureRegion(texture,0,0,96,96);

        setOrigin(10f/PPM,10f/PPM);
        setBounds(0,0,20f/PPM,20f/PPM);
        setRegion(textureRegion);
    }

    private Animation boxExplosion;



    private float stateTimer;

    private TextureRegion getFrame(float dt){
        TextureRegion region = new TextureRegion();
        stateTimer = stateTimer + dt;
        if(toDestroy) {

            if (stateTimer > 3f) {

                isDestroyed = true;
                toDestroy = false;
            }
           // region = boxExplosion.getKeyFrame(stateTimer);
        }
          //  region = boxExplosion.getKeyFrame(stateTimer);
        return null;
    }

    @Override
    public Items collition() {
        stateTimer = 0;
        return this;
    }


    @Override
    public void update(float dt) {

        if (toDestroy){

            GameScreen.world.destroyBody(body);
            isDestroyed = true;
            toDestroy = false;
        }
        setPosition(body.getPosition().x- getWidth()/2, body.getPosition().y-getHeight()/2);
            setRegion(textureRegion);
    }


    @Override
    public void enableDestroy() {
        stateTimer = 0;
        toDestroy = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public int getPoints() {
        return this.points;
    }


    private void defineBody(){
        int radius = 10;
/*
        mainBody = new CustomBody(game.getWorld(),posX,posY, CustomBody.BodyType.DYNAMICBODY,radius, CustomBody.ShapeType.POLYGON);
        mainBody.Collision((short)(GROUND_BIT | PLAYER_BIT),(short)ITEM_BIT);
        mainBody.Finalize(this);
*/


        myBodyDef.type = BodyDef.BodyType.StaticBody; //this will be a dynamic body
        myBodyDef.position.set( posX, posY); //a little to the left

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10f/PPM,10f/PPM);
        fd.shape = polygonShape;
        fd.filter.categoryBits = ITEM_BIT;
        fd.filter.maskBits = PLAYER_BIT | GROUND_BIT;
        fd.isSensor = true;
        body = GameScreen.world.createBody(myBodyDef);

        fixture = body.createFixture(polygonShape,1);
        fixture.setFilterData(fd.filter);
        fixture.setUserData(this);

        polygonShape.dispose();


    }

    private Fixture fixture;
    private FixtureDef fd = new FixtureDef();
    private Body body;

    private BodyDef myBodyDef = new BodyDef();
}
