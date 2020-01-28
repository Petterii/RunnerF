package com.firstrunner.game.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.firstrunner.game.Firstrunner;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;

public class Background extends Sprite {


    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 100;

    public Background(GameScreen screen) {
        textureRegion = new TextureRegion((Texture) screen.getManager().get(TEXTURE_BACKGROUNDENDLESS));
        textureRegionBounds1 = new Rectangle(0 - Firstrunner.FR_WIDTH / 2, 0, Firstrunner.FR_WIDTH, Firstrunner.FR_HEIGHT);
        textureRegionBounds2 = new Rectangle(Firstrunner.FR_WIDTH / 2, 0, Firstrunner.FR_WIDTH, Firstrunner.FR_HEIGHT);

    }


    public void update(float delta) {
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRegionBounds1.x, textureRegionBounds1.y, Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);

        batch.draw(textureRegion, textureRegionBounds2.x, textureRegionBounds2.y, Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x - (delta * speed)) <= 0;
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x += delta * speed;
        textureRegionBounds2.x += delta * speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(Firstrunner.FR_WIDTH, 0, Firstrunner.FR_WIDTH,Firstrunner.FR_HEIGHT);
    }

}
