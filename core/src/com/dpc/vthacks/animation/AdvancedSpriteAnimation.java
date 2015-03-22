package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Provides meta-data for each frame
 * @author Daniel
 *
 */
public class AdvancedSpriteAnimation implements Disposable {
    private Animation animation;
    private FrameData[] frameData;
    private int index;
    private float time;
    
    public AdvancedSpriteAnimation(AdvancedSpriteAnimation cpy, boolean flipFrames) {
        TextureRegion[] frames = cpy.getAnimation().getKeyFrames();
        
        if(flipFrames) {
            frames = new TextureRegion[cpy.animation.getKeyFrames().length];
            
            for(int i = 0; i < frames.length; i++) {
                frames[i] = new TextureRegion(cpy.animation.getKeyFrames()[i]);
                frames[i].flip(true, false);
            }
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
       // System.err.println(frameData[index].getAnchorOffsetY());
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