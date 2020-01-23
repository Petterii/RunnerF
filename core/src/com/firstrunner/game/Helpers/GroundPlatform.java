package com.firstrunner.game.Helpers;

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
import com.firstrunner.game.Screens.GameScreen;

import java.util.Random;

import static com.firstrunner.game.Globals.*;


class GroundPlatform extends Sprite {

    public boolean isDestroyed;
    public boolean toDestroy;
    private Body body;
    private World world;
    // private CreateWorldRandomized screen;

    public GroundPlatform(World world, float platformX) {

        Random rand = new Random();
        int posY = rand.nextInt(100);

        isDestroyed = false;
        this.world = GameScreen.world;
        // this.screen = screen;
        BodyDef bdef = new BodyDef();
        EdgeShape shape;
        FixtureDef fdef = new FixtureDef();


        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(platformX /PPM,10f/PPM);

        body = GameScreen.world.createBody(bdef);

        shape = new EdgeShape();
        shape.set(new Vector2(platformX/PPM, (float)posY/PPM), new Vector2((Firstrunner.FR_WIDTH+platformX)/PPM, (float)posY/PPM));
        fdef.shape = shape;
        fdef.density = 1f;

        fdef.filter.categoryBits = GROUND_BIT;
        body.createFixture(fdef);
        shape.dispose();
/*
        CustomBody triggerSpawn = new CustomBody(world,body.getPosition().x+100/PPM,(float)posY/PPM, CustomBody.BodyType.STATICBODY, CustomBody.ShapeType.CIRCLE,20,true);
        triggerSpawn.Collision((short)(GROUND_BIT | PLAYER_BIT),TRIGGER_SPAWN);
        triggerSpawn.Finalize(this);
*/
    createTrigger(body);
    }

    private void createTrigger(Body body){
        BodyDef bdef = new BodyDef();
        EdgeShape shape;
        FixtureDef fdef = new FixtureDef();


        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(body.getPosition().x+(Firstrunner.FR_WIDTH/2) /PPM,10f/PPM);

        body = GameScreen.world.createBody(bdef);

        shape = new EdgeShape();
        shape.set(new Vector2(body.getPosition().x-100f/PPM, 200/PPM), new Vector2(body.getPosition().x-100f/PPM, -10f));
        fdef.shape = shape;
        fdef.isSensor = true;

        fdef.filter.categoryBits = TRIGGER_SPAWN;

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);

        shape.dispose();
    }


    public void update(float delta){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
    }

    public void spawnNewPlatform(){
        CreateWorldRandomized.spawnNewPlatform();
    }
}
