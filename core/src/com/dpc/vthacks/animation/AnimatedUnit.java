package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;

/**
 * Animated unit with meta data for each frame
 * @author Daniel
 *
 */
public abstract class AnimatedUnit extends Unit {
    private SpriteAnimation animation, restingAnimation;
    private TextureRegion initialFrame;
    private boolean playing;
    private final boolean useRestAnimation;
    
    public AnimatedUnit(SpriteAnimation animation, 
                        SpriteAnimation restingAnimation, 
                        Properties properties,
                        float x, 
                        float y) {
        super(animation.update(0), properties, x, y);
        this.restingAnimation = restingAnimation;
        
        useRestAnimation = true;
        this.animation = animation;
        playing = false; 
    }
    
    public AnimatedUnit(SpriteAnimation animation, 
                        TextureRegion initialFrame, 
                        Properties properties,
                        float x, 
                        float y) {
        super(initialFrame, properties, x, y);
        this.initialFrame = initialFrame;

        useRestAnimation = false;
        this.animation = animation;
        playing = false; 
    }

    public void update(float delta) {
        super.update(delta);

        if(playing) {
            //System.out.println("setRegion(animation.update(delta));");
            setRegion(animation.update(delta));
        }
        else {
            if(useRestAnimation) {
                //System.out.println("setRegion(restingAnimation.update(delta));");
                setRegion(restingAnimation.update(delta));
            }
            else {
                //System.out.println("setRegion(initialFrame)");
                setRegion(initialFrame);
            }
        }
    }
    
    public void render() {
        draw(App.batch);
    }
    
    public void setAnimationFrames(TextureRegion[] frames, float time) {
        animation = new SpriteAnimation(frames, time);
    }
    
    public SpriteAnimation getAnimation() {
        return animation;
    }
    
    public SpriteAnimation getRestingAnimation() {
        return restingAnimation;
    }
    
    public void setAnimation(SpriteAnimation animation) {
        this.animation = animation;
    }
    
    public boolean isAnimationFinished() {
        return animation.getAnimation().isAnimationFinished(animation.getStateTime());
    }
    
    public void setPlaying(boolean playing) {
        this.playing = playing;

        // If not playing anymore, change back to the initial frame
        if(!playing) {
            if(useRestAnimation) {    
                setRegion(restingAnimation.getAnimation().getKeyFrame(0));
            }
            else {
                setRegion(initialFrame);
            }
        }
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    public boolean hasRestAnimation() {
        return useRestAnimation;
    }
    
    public TextureRegion getInitialFrame() {
        return initialFrame;
    }
}
