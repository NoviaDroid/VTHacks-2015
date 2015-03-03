package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    
    public TextureRegion update(float delta) {
        time += delta;

        return animation.getKeyFrame(time, true);
    }
    
    public void render(SpriteBatch batch, float delta) throws Exception {
        time += delta;
        batch.draw(animation.getKeyFrame(time, true), 50, 50);
        throw new Exception("not implemented completely lol");
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