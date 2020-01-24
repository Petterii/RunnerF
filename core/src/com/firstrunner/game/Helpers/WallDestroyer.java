package com.firstrunner.game.Helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;


public class WallDestroyer extends Sprite {

    private Body body;
    private GameScreen screen;

    public WallDestroyer(GameScreen screen) {
       this.screen = screen;

        createTrigger();
    }

    private void createTrigger(){
        // Body trigger;
        BodyDef bdef = new BodyDef();
        PolygonShape shape;
        FixtureDef fdef = new FixtureDef();


        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(-150f /PPM,0f/PPM);

        body = GameScreen.world.createBody(bdef);

        shape = new PolygonShape();
        shape.setAsBox(2/PPM,200);
        // shape.set(new Vector2(-30f/PPM, 200/PPM), new Vector2(0f/PPM, -10f/PPM));
        fdef.shape = shape;
        fdef.isSensor = true;

        fdef.filter.categoryBits = WALL_DESTROYER;

        //fdef.filter.maskBits = GROUND_BIT | PLAYER_BIT;
        body.setLinearVelocity(1f,0f);
        Fixture fixture = body.createFixture(fdef);
        fixture.setFilterData(fdef.filter);
        fixture.setUserData(this);
        body.setGravityScale(0);
        shape.dispose();


    }

    public void update(float dt){
        body.setTransform(screen.getPlayerX() -10f,0f,0);
        setPosition(body.getPosition().x,body.getPosition().y);
    }
}
