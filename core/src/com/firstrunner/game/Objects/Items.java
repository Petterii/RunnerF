package com.firstrunner.game.Objects;

public abstract class Items {

    protected boolean toDestroy;
    protected boolean isDestroyed;

    public abstract Items collition();
    public abstract void update(float dt);
    public abstract void enableDestroy();

    public abstract boolean isDestroyed();
}
