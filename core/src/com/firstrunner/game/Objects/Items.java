package com.firstrunner.game.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Items extends Sprite {

    protected boolean toDestroy;
    protected boolean isDestroyed;

    public abstract Items collition();
    public abstract void update(float dt);
    public abstract void enableDestroy();

    public abstract boolean isDestroyed();

    public abstract int getPoints();
}
