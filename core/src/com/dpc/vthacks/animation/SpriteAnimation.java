package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class SpriteAnimation implements Disposable {
    private Animation animation;
    private FrameData[] frameData;
    private int index;
    private float time;
    
    public SpriteAnimation(FrameData[] frameData) {
        this.frameData = frameData;
        
        TextureRegion[] d = new TextureRegion[frameData.length];
        
        for(int i = 0; i < frameData.length; i++) {
            d[i] = frameData[i].getRegion();
        }

        animation = new Animation(0.15f, d);
        animation.setPlayMode(PlayMode.LOOP);
    }
    
    public TextureRegion update(float delta) {
        time += delta;
        
        index = animation.getKeyFrameIndex(time);
       // System.err.println(frameData[index].getAnchorOffsetY());
        return animation.getKeyFrame(time, true);
    }
    
    public void render(SpriteBatch batch, float delta) throws Exception {
        time += delta;
        batch.draw(animation.getKeyFrame(time, true), 50, 50);
        throw new Exception("not implemented completely lol");
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