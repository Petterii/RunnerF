package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.FONT_ITALIC;
import static com.firstrunner.game.Globals.FONT_PNG;
import static com.firstrunner.game.Globals.PPM;

public class FloatingText {

    BitmapFont font;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    private float x,y;
    private float lastX,lastY;
    private float goingup;
    private float upspeed = 30f;
    private int points;
    private boolean toDestroy,isDestroyed;

    public FloatingText(GameScreen screen, int points, float x, float y) {
        //texture = screen.getManager().get(FONT_PNG);
    //    textureRegion
        this.x = 0f;
        this.y = 0f;
        lastX = x;
        lastY = y;
        this.points = points;
        this.fade = 1.0f;
        initFont();

    }

    private void initFont() {
        goingup = 0f;
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_ITALIC));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 8;
        fontParameter.borderWidth = 1;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.GREEN;
        font = fontGenerator.generateFont(fontParameter);
    }

    float timer;
    float fade;
    public void udpate(float dt, float newBox2dXpos, float newBox2dYpos){
        float diffX,diffY;
        diffX = newBox2dXpos -lastX;
        diffY = newBox2dYpos -lastY;
        x = -diffX*PPM;

        goingup += dt;
        y = -(diffY*PPM)+(goingup*upspeed);

        if (timer > 0.3f)
            fade -= dt;
        if (fade < 0.0f)
            fade = 1.0f;
        timer += dt;
        if (timer > 1f)
            isDestroyed = true;
    }

    public void draw(SpriteBatch batch){
        font.setColor(1, 1, 1, fade);
        font.draw(batch,"+"+points,x,y);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
