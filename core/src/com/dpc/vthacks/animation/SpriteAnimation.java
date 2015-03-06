package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Simple sprite animation with no frame data
 * @author Daniel
 *
 */
public class SpriteAnimation implements Disposable {
    private Animation animation;
    private float time;
    
    public SpriteAnimation(SpriteAnimation cpy, float time) {
        this.time = time;

        animation = new Animation(time, cpy.getAnimation().getKeyFrames());
    }
    
    public SpriteAnimation(TextureRegion[] frames, float frameTime) {
        animation = new Animation(frameTime, frames);
    }
    
    public SpriteAnimation cpy() {
        return new SpriteAnimation(this, time);
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