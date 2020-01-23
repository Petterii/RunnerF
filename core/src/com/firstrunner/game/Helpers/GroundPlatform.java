package com.firstrunner.game.Helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;


class GroundPlatform extends Sprite {

    public boolean isDestroyed;
    public boolean toDestroy;
    private Body body;
    private World world;
    // private CreateWorldRandomized screen;

    public GroundPlatform(World world, float platformX) {
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
        shape.set(new Vector2(platformX/PPM, 0.11f/PPM), new Vector2((Firstrunner.FR_WIDTH+platformX)/PPM, 0.1f/PPM));
        fdef.shape = shape;
        fdef.density = 1f;
        fdef.filter.categoryBits = GROUND_BIT;
        body.createFixture(fdef);
        shape.dispose();

        CustomBody triggerSpawn = new CustomBody(world,(platformX+(Firstrunner.FR_WIDTH/2)) /PPM,0, CustomBody.BodyType.STATICBODY, CustomBody.ShapeType.CIRCLE,20,true);
        triggerSpawn.Collision((short)(GROUND_BIT | PLAYER_BIT),TRIGGER_SPAWN);
        triggerSpawn.Finalize(this);

    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
    }

    public void spawnNewPlatform(){
        CreateWorldRandomized.spawnNewPlatform();
    }
}
