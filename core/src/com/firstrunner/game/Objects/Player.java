package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.CreateWorldRandomized;
import com.firstrunner.game.Helpers.CustomBody;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;

public class Player extends Sprite {


    public static float getPos() {
        return mainBody.getBody().getPosition().x;
    }

    public void started() {
        startRollingSound();
        landingSound.play();
        started = true;
    }
    public void fallDown(){
        mainBody.getBody().applyForce(0,-100f,0,0,true);
    }

    private Sound jumpsound;
    private Sound rollingsound;
    private Sound speedupSound;
    private Sound landingSound;
    public void JumpTrigger() {
        jumpsound.play();
        mainBody.getBody().applyForceToCenter(0,330f,true);
    }

    public boolean isStarted() {
        return started;
    }

    public float getSpeed(){

        return 10f;
    }

    private enum State {SPEEDING, NORMAL, AVAILSPEED, NOTHING}

    private float stateTimer;
    private static CustomBody mainBody;
    private int positionX;
    private int positionY;
    private int radius;
    private GameScreen screen;
    private Texture texture;
    private World world;
    private TextureRegion textureRegion;
    private boolean isRollingSoundPlaying;

    public void stopRollingSound(){
        if (isRollingSoundPlaying)
            rollingsound.stop();
        isRollingSoundPlaying = false;
        isgrounded = false;
    }
    public void startRollingSound(){
        if (!isRollingSoundPlaying)
            rollingsound.play(0.2f);
        isRollingSoundPlaying = true;
        isgrounded = true;
    }


    public Player(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        jumpsound = screen.getManager().get(SOUND_JUMP);
        rollingsound = screen.getManager().get(SOUND_BALL_ROLLING);
        speedupSound = screen.getManager().get(SOUND_SPEEDUP);
        landingSound = screen.getManager().get(SOUND_LANDING);
        rollingsound.setLooping(1,true);

        stateTimer = 10;
        cooldownSpeed = 0;
        time = 1;
        started = false;
        defineBody();

        texture = (Texture)screen.getManager().get(PLAYER_BALL);
        textureRegion = new TextureRegion(texture,0,0,64,64);
        setOrigin(10f/PPM,10f/PPM);
        setBounds(0,0,20f/PPM,20f/PPM);
        setRegion(textureRegion);

    }

    private void defineBody() {
        positionX = 132;
        positionY = 100;
        radius = 10;

        mainBody = new CustomBody(world,positionX/PPM,positionY/PPM, CustomBody.BodyType.DYNAMICBODY,radius, CustomBody.ShapeType.CIRCLE);
        mainBody.Collision((short)(GROUND_BIT | ITEM_BIT | TRIGGER_SPAWN),(short)PLAYER_BIT);
        mainBody.Finalize(this);
    }

    public void collition(Items item){

        if (item instanceof SkullBox){
            SkullBox skull= (SkullBox) item;
            Gdx.app.log("LIB", "collition: "+getState().toString());
            if (getState() == State.SPEEDING){
                time = 0.3f;
                ((Sound) screen.getManager().get(BOX_BREAKING)).play();
                skull.enableDestroy();
            } else if (!skull.toDestroy){
                isTouching = true;
                screen.setGameover(true);
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
            if (isgrounded) {
                startRollingSound();
            }
            return State.AVAILSPEED;
        }
        return State.NOTHING;
    }

    public Body getMainBody() {
        return mainBody.getBody();
    }

    private boolean isgrounded;
    private float roationSpeed;

    public void update(float dt) {

        cooldownSpeed = cooldownSpeed +dt;
        if (!started)
            fallDown();

        roationSpeed = 2f;
        // velocity(dt);
        if (mainBody.getBody().getPosition().x < 0.0f) {
            mainBody.getBody().setLinearVelocity(2.0f,mainBody.getBody().getLinearVelocity().y);
        } else{
            mainBody.getBody().setLinearVelocity(0.0f,mainBody.getBody().getLinearVelocity().y);
        }

        velocity(dt);

        mainBody.getBody().setAngularVelocity(velocity*-1*roationSpeed);
        setPosition(mainBody.getBody().getPosition().x- getWidth()/2, mainBody.getBody().getPosition().y-getHeight()/2);
       rotate(roationSpeed*velocity*-1);
    }

    private boolean isTouching;

    private float velocity;
    public static float time;
    private float cooldownSpeed;
    private boolean started;

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
        if (!started)
            velocity = 0;
        mainBody.getBody().setLinearVelocity(velocity*time,mainBody.getBody().getLinearVelocity().y);
    }

    public void addForce(float dt) {


     if (getState() == State.AVAILSPEED && !isTouching) {
        // mainBody.getBody().applyForceToCenter(0,200f,true);
            speedupSound.play();
            stopRollingSound();
            stateTimer = 0;
            cooldownSpeed = 0;
            velocity = 5f;
        }

    }

}
