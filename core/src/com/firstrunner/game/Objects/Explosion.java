package com.firstrunner.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.PPM;
import static com.firstrunner.game.Globals.TEXTURE_EXPLOSION;

public class Explosion extends Sprite {

    private World world;
    private Animation boxExplosion;
    private GameScreen game;
    private boolean isLoop;
    private boolean isDestroyed;
    private boolean toDestoroy;

    public Explosion(GameScreen game, float x, float y, boolean isLoop) {
        this.game = game;
        world = game.getWorld();
        isDestroyed = false;
        toDestoroy = false;
        this.isLoop = isLoop;
        initAnimation();
        setBounds(0,0,80f/PPM,80f/PPM);
        setPosition(x - 25f/PPM,y - 25f/PPM);
        setRegion(boxExplosion.getFrame());
    }

    public void update(float dt){
        if (boxExplosion.update(dt))
            setRegion(boxExplosion.getFrame());
        else
            isDestroyed = true;
    }

    private void initAnimation() {
        Texture texture = game.getManager().get(TEXTURE_EXPLOSION);
        boxExplosion = new Animation(new TextureRegion(texture), 4,7, 0.05f);
    }


    public boolean isDestroyed() {
        return isDestroyed;
    }
}
