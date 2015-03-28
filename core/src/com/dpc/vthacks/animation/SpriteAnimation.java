package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.dpc.vthacks.data.Assets;

/**
 * Simple sprite animation with no frame data
 * @author Daniel
 *
 */
public class SpriteAnimation implements Disposable {
    private Animation animation;
    private float time;
    
    public SpriteAnimation(SpriteAnimation cpy) {
        this.time = cpy.time;
        
        animation = new Animation(time, Assets.shallowCopy(cpy.getAnimation().getKeyFrames()));   
    }
    
    public SpriteAnimation(TextureRegion[] frames, float frameTime) {
        this.time = frameTime;
        animation = new Animation(frameTime, frames);
    }
    
    public SpriteAnimation cpy() {
        return new SpriteAnimation(this);
    }
    
    public TextureRegion update(float delta) {
        time += delta;

        return animation.getKeyFrame(time, true);
    }
    
    public float getStateTime() {
        return time;
    }
    
    public void setFrames(TextureRegion[] frames, float speed) {
        animation = new Animation(time, frames);
    }
    
    public TextureRegion getCurrentFrame() {
        return animation.getKeyFrame(time);
    }
    
    public Animation getAnimation() {
        return animation;
    }
    
    @Override
    public void dispose() {

    }
}