package com.firstrunner.game.Objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    Array<TextureRegion> frames;
    float maxFrameTime;
    float currentFrameTime;
    int frameCountWidth;
    int frameCountHeight;
    int frame;


    public Animation(TextureRegion region, int frameCountWidth,int frameCountHeight, float cycleTime){
        frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCountWidth;
        int frameHeight = region.getRegionHeight() / frameCountHeight;
        for(int j = 0; j < frameCountHeight; j++){
            for(int i = 0; i < frameCountWidth; i++){
                temp = new TextureRegion(region, i * frameWidth, j*frameHeight , frameWidth, frameHeight);
                frames.add(temp);
            }
        }
        this.frameCountHeight = frameCountHeight;
        this.frameCountWidth = frameCountWidth;
        maxFrameTime = (cycleTime / frameCountWidth) + (cycleTime / frameCountHeight);
        frame = 0;
    }

    // return Ok. if false possibly loop done
    public boolean update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCountWidth+frameCountHeight) {
            frame = 0;
            return false;
        }
        return true;
    }

    public void flip(){
        for(TextureRegion region : frames)
            region.flip(true, false);
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
