package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.CustomBody;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;

public class Player extends Sprite {

    private enum State {SPEEDING, NORMAL, AVAILSPEED, NOTHING}

    private float stateTimer;
    private CustomBody mainBody;
    private int positionX;
    private int positionY;
    private int radius;
    private GameScreen screen;

    private World world;

    public Player(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        stateTimer = 10;
        cooldownSpeed = 0;
        time = 1;
        defineBody();
        setBounds(0,0,50/PPM,50/PPM);
        // setRegion(playerIdle.getKeyFrame(stateTimer));
    }

    private void defineBody() {
        positionX = 132;
        positionY = 160;
        radius = 10;

        mainBody = new CustomBody(world,positionX/PPM,positionY/PPM, CustomBody.BodyType.DYNAMICBODY,radius);
        mainBody.Collision((short)(GROUND_BIT | ITEM_BIT),(short)PLAYER_BIT);
        mainBody.Finalize(this);
    }

    public void collition(Items item){
        time = 0.3f;
        ((Sound) screen.getManager().get(BOX_BREAKING)).play();
        if (item instanceof SkullBox){
            SkullBox skull= (SkullBox) item;
            if (mainBody.getBody().getLinearVelocity().x > 2.5f){
                skull.enableDestroy();
        }
        }
    }

    private State getState(){
        if (mainBody.getBody().getLinearVelocity().x <= 1f && cooldownSpeed < 1f)
            return State.NORMAL;
        else if (mainBody.getBody().getLinearVelocity().x >= 2f)
            return State.SPEEDING;
        else if (stateTimer > 0.5f){
            time = 1;
            return State.AVAILSPEED;
        }
        return State.NOTHING;
    }

    public Body getMainBody() {
        return mainBody.getBody();
    }

    public void update(float dt) {
        velocity(dt);
        setPosition(mainBody.getBody().getPosition().x - getWidth() / 2, mainBody.getBody().getPosition().y - getHeight() / 2);
    }

    private boolean isTouching;

    private float velocity;
    public static float time;
    private float cooldownSpeed;

    public void velocity(float dt){
        stateTimer = stateTimer + dt;

        if (stateTimer > 0.3f) {
            switch (getState()) {
                case NORMAL:
                    cooldownSpeed = cooldownSpeed + dt;
                    velocity = 0.5f;
                    break;
                case SPEEDING:
                    velocity = -1;
                    break;
                case NOTHING:
                    velocity = 0.3f;
                    break;
            }
        }
        mainBody.getBody().setLinearVelocity(velocity*time,0);
    }

    public void addForce(float dt) {
        if (getState() == State.AVAILSPEED) {
            stateTimer = 0;
            cooldownSpeed = 0;
            velocity = 5f;
            isTouching = true;
        }
    }

    public void lowerForce(float delta) {
        //mainBody.getBody().applyForce(-10f,0,0,0, true);
        isTouching = false;
    }
}
