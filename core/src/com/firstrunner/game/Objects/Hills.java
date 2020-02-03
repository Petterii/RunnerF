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
    private boolean fromMain;
    private float bgwidth;

    public Hills(Texture texture,int bgNr,boolean fromMain) {
        this.fromMain = fromMain;
        background1 = texture;
        bgwidth = background1.getWidth();

        region = new TextureRegion(texture,0,0,437,208);
        if (fromMain)
            setBounds(0,0, 437,Firstrunner.FR_HEIGHT*2);
        else
            setBounds(0,0, 437,Firstrunner.FR_HEIGHT);

        setRegion(region);
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
