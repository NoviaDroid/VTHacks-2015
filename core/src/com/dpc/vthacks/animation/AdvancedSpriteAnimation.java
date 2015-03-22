package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.dpc.vthacks.data.Assets;

/**
 * Provides meta-data for each frame
 * @author Daniel
 *
 */
public class AdvancedSpriteAnimation implements Disposable {
    private Animation animation;
    private FrameData[] frameData;
    private float time;
    private int index;
    
    public AdvancedSpriteAnimation(AdvancedSpriteAnimation cpy, boolean flipFramesX) {
        TextureRegion[] frames = cpy.getAnimation().getKeyFrames();
        
        if(flipFramesX) {
            frames = Assets.copyFlip(frames, true, false);
        }
        
        animation = new Animation(cpy.time, frames);
        frameData = cpy.frameData;
        time = cpy.time;
    }
    
    public AdvancedSpriteAnimation(FrameData[] frameData) {
        this.frameData = frameData;
        
        init(0.15f);
    }
    
    private void init(float time) {
        this.time = time;
        
        TextureRegion[] d = new TextureRegion[frameData.length];
        
        for(int i = 0; i < frameData.length; i++) {
            d[i] = frameData[i].getRegion();
        }

        animation = new Animation(time, d);
        animation.setPlayMode(PlayMode.LOOP);
    }
    
    public TextureRegion update(float delta) {
        time += delta;
        
        index = animation.getKeyFrameIndex(time);

        return animation.getKeyFrame(time, true);
    }
    
    public void setFrameData(FrameData[] frameData, float speed) {
        this.frameData = frameData;
        
        init(speed);
    }
    
    public FrameData getCurrentFrame() {
        return frameData[index];
    }
    
    public FrameData[] getFrameData() {
        return frameData;
    }
    
    public float getStateTime() {
        return time;
    }
    
    public Animation getAnimation() {
        return animation;
    }
    
    @Override
    public void dispose() {
    }

}