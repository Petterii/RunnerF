package com.firstrunner.game.Objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Helpers.CreateWorldRandomized;
import com.firstrunner.game.Screens.MainMenu;

import static com.firstrunner.game.Globals.*;


public class MainBall extends Sprite {


    private Texture texture;
    private TextureRegion textureRegion;
    private MainMenu screen;
    private boolean animate;
    private  float speed;

    public MainBall(MainMenu screen) {
        animate = false;
        this.screen = screen;
        speed = 1500f;
        float width = TEXTURE_BOUNCINGBALL_WIDTH;
        float height = TEXTURE_BOUNCINGBALL_HEIGHT;
        texture = (Texture)this.screen.getGame().getManager().get(PLAYER_BALL);
        textureRegion = new TextureRegion(texture,0,0,64,64);

        //setOrigin(-32f/PPM,-32f/PPM);
        setBounds(0,0,200f,200f);
        setRegion(textureRegion);
        setPosition(-50, Firstrunner.FR_HEIGHT-100);
        // todo add texture
    }

    float time = 0;
    public void update(float dt){
        if (animate){
            time += dt;
            setPosition(-50f+(time*speed),Firstrunner.FR_HEIGHT-100f);
        }
    }


    public void enablemovement(){
        ((Sound) screen.getGame().getManager().get(SOUND_SPEEDUP)).play();
        animate = true;
    }

    public boolean isMoving() {
        return animate;
    }

    public boolean isOutside() {
        if (getX() > 600f)
            return true;
    return false;
    }
}
