package com.firstrunner.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.TEXTURE_BACKGROUNDENDLESS;
import static com.firstrunner.game.Screens.GameScreen.playerSpeed;

public class Hills extends Sprite {


    private final Texture background1;
    private TextureRegion region;

    private float bgwidth;

    public Hills(GameScreen screen,int bgNr) {
        background1 = (Texture)screen.getManager().get(TEXTURE_BACKGROUNDENDLESS);
        bgwidth = background1.getWidth();


        setBounds(0,0, bgwidth,Firstrunner.FR_HEIGHT);
        setRegion(background1);
        setPosition(0,-Firstrunner.FR_HEIGHT/2);
    }

    public void update(float dt,float bgOffset){
    // backgroundScrollingupdate(dt);
     setPosition(bgOffset,0-Firstrunner.FR_HEIGHT/2);
    }

    public Texture getTexture(){
        return background1;
    }
}
