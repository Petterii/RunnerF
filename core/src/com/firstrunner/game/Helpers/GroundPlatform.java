package com.firstrunner.game.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Objects.Player;
import com.firstrunner.game.Objects.SkullBox;
import com.firstrunner.game.Screens.GameScreen;

import java.util.Random;

import static com.firstrunner.game.Globals.*;


class GroundPlatform extends Sprite {

    private int index;
    private static int counter = 1;
    public boolean isDestroyed;
    public boolean toDestroy;
    private Body body;

 //   private World world;
    private CreateWorldRandomized screen;

    public GroundPlatform(CreateWorldRandomized screen, float platformX) {
        this.screen = screen;
        index = counter;
        counter++;
        this.platformX = platformX;
        Random rand = new Random();
        float posY = (100*rand.nextFloat())/PPM;

        isDestroyed = false;
    //    this.world = GameScreen.world;
        // this.screen = screen;
        BodyDef bdef = new BodyDef();
        EdgeShape shape;
        FixtureDef fdef = new FixtureDef();


        bdef.type = BodyDef.BodyType.StaticBody;
        //bdef.position.set(platformX /PPM,10f/PPM);

        body = GameScreen.world.createBody(bdef);


        startPoint = (Firstrunner.FR_WIDTH*platformX) /PPM;
        endPoint = startPoint + ((float)Firstrunner.FR_WIDTH /PPM);

        shape = new EdgeShape();
        shape.set(new Vector2(startPoint, posY), new Vector2(endPoint, posY));
        fdef.shape = shape;
        fdef.density = 1f;

        fdef.filter.categoryBits = GROUND_BIT;
        fdef.filter.maskBits = WALL_DESTROYER | PLAYER_BIT | ITEM_BIT;
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();
/*
        CustomBody triggerSpawn = new CustomBody(world,body.getPosition().x+100/PPM,(float)posY/PPM, CustomBody.BodyType.STATICBODY, CustomBody.ShapeType.CIRCLE,20,true);
        triggerSpawn.Collision((short)(GROUND_BIT | PLAYER_BIT),TRIGGER_SPAWN);
        triggerSpawn.Finalize(this);
*/
    float diff = (endPoint-startPoint);
    float insidePlatform = startPoint+(diff*rand.nextFloat());
    // dont spawn on the first platform where player starts
    if (platformX != 0)
        screen.addObject( new SkullBox(screen.getWorld(),insidePlatform,posY+0.1f));
    createTrigger();
    }

    float startPoint;
    float endPoint;

    private float platformX;
    private void createTrigger(){
        Body trigger;
        BodyDef bdef = new BodyDef();
        EdgeShape shape;
        FixtureDef fdef = new FixtureDef();

        // not fully center. alittle forward triggers jump and new platformspawn
       // float centerPoint = (((Firstrunner.FR_WIDTH+platformX) +platformX)/PPM)/2;
        float centerPoint = startPoint+((endPoint-startPoint)*0.7f);

        bdef.type = BodyDef.BodyType.StaticBody;
        //bdef.position.set(centerPoint,10f/PPM);

        //trigger = GameScreen.world.createBody(bdef);

        shape = new EdgeShape();
        shape.set(new Vector2(centerPoint, 200/PPM), new Vector2(centerPoint, -10f));
        fdef.shape = shape;
        fdef.isSensor = true;

        fdef.filter.categoryBits = TRIGGER_SPAWN;

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        shape.dispose();
    }


    public void update(float delta){
      //  deleteGroundIfpassed(delta);

        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        deleteGroundIfpassed(delta);
    }

    private void deleteGroundIfpassed(float delta) {
        if (toDestroy) {
            GameScreen.world.destroyBody(body);
            isDestroyed = true;
        }
    }

    public void spawnNewPlatform(){
        CreateWorldRandomized.spawnNewPlatform();
    }

    public void deleteGround() {
        toDestroy = true;
    }
}
