package com.firstrunner.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.firstrunner.game.Helpers.CreateWorldRandomized;
import com.firstrunner.game.Screens.GameScreen;

import static com.firstrunner.game.Globals.*;

public class Arrow extends Sprite {

    private CreateWorldRandomized screen;

    public Arrow(CreateWorldRandomized screen, float x, float y) {
    this.screen = screen;
    Texture texture = screen.getGameScreen().getManager().get(TEXTURE_ARROW);

    setPosition(x,y);
    setBounds(x-0.1f,y, 20f/PPM,100f/PPM);
    setRegion(texture);
    }

}
