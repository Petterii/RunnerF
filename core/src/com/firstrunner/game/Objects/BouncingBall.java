package com.firstrunner.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Helpers.CreateWorldRandomized;

import javax.xml.soap.Text;

import static com.firstrunner.game.Globals.*;

public class BouncingBall extends Items {

    private World world;
    private Texture texture;
    private TextureRegion textureRegion;
    private CreateWorldRandomized screen;
    private Body body;


    public BouncingBall(CreateWorldRandomized screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        defineBody(x,y);
        float width = TEXTURE_BOUNCINGBALL_WIDTH;
        float height = TEXTURE_BOUNCINGBALL_HEIGHT;
        texture = (Texture)this.screen.getGameScreen().getManager().get(TEXTURE_BOUNCINGBALL);
        textureRegion = new TextureRegion(texture,0,0,64,64);

        //setOrigin(-32f/PPM,-32f/PPM);
        setBounds(0,0,20f/PPM,20f/PPM);
        setRegion(textureRegion);

        // todo add texture
    }

    public Body getBody(){
        return body;
    }

    private void defineBody(float x,float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(3f/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1.0f; // Make it bounce a little bit
        fixtureDef.filter.categoryBits = ITEM_BIT;
        fixtureDef.filter.maskBits = PLAYER_BIT | GROUND_BIT;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setLinearVelocity(BALL_XVELOCITYSPEED,body.getLinearVelocity().y);

        circle.dispose();

        body.applyForce(-1f,BALLFORCEUPP,-1f,BALLFORCEUPP,true);
    }

    @Override
    public Items collition() {
        return this;
    }

    public void update(float dt){
        setPosition(body.getPosition().x-(10f/PPM),body.getPosition().y-(10f/PPM));
    }

    @Override
    public void enableDestroy() {
        toDestroy = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public int getPoints() {
        return 0;
    }

}
