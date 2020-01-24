package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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

    public SkullBox(World world, float x,float y) {
        // this.game = game;
        //this.world = world;

        posX = x;
        posY = y;
        toDestroy = false;
        defineBody();
    }

    @Override
    public Items collition() {
        return this;
    }

    @Override
    public void update(float dt) {
        if (toDestroy){
            GameScreen.world.destroyBody(body);
            isDestroyed = true;
            toDestroy = false;
        }
    }

    @Override
    public void enableDestroy() {
        toDestroy = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    private void defineBody(){
        int radius = 10;
/*
        mainBody = new CustomBody(game.getWorld(),posX,posY, CustomBody.BodyType.DYNAMICBODY,radius, CustomBody.ShapeType.POLYGON);
        mainBody.Collision((short)(GROUND_BIT | PLAYER_BIT),(short)ITEM_BIT);
        mainBody.Finalize(this);
*/


        myBodyDef.type = BodyDef.BodyType.DynamicBody; //this will be a dynamic body
        myBodyDef.position.set( 300f/PPM, 60f/PPM); //a little to the left

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10f/PPM,10f/PPM);
        fd.shape = polygonShape;
        fd.filter.categoryBits = ITEM_BIT;
        fd.filter.maskBits = PLAYER_BIT | GROUND_BIT;

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
