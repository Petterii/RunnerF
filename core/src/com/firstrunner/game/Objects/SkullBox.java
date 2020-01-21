package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.firstrunner.game.Helpers.CustomBody;
import com.firstrunner.game.Screens.GameScreen;

import java.util.Random;

import static com.firstrunner.game.Globals.*;

public class SkullBox extends Items {

    private CustomBody mainBody;
    private float posX,posY;
    private GameScreen game;

    public SkullBox(GameScreen game, float x,float y) {
        this.game = game;
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
            game.getWorld().destroyBody(mainBody.getBody());
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
        int radius = 20;

        mainBody = new CustomBody(game.getWorld(),posX,posY, CustomBody.BodyType.STATICBODY,radius);
        mainBody.Collision((short)(GROUND_BIT | PLAYER_BIT),(short)ITEM_BIT);
        mainBody.Finalize(this);

    }
}
