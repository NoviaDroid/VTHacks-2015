package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;

/**
 * Provides meta data for each frame
 * @author Daniel
 *
 */
public abstract class AdvancedAnimatedUnit extends Unit {
    private AdvancedSpriteAnimation animation, restingAnimation;
    private TextureRegion initialFrame;
    private boolean playing;
    private final boolean useRestAnimation;
    
    public AdvancedAnimatedUnit(AdvancedSpriteAnimation animation, 
                        AdvancedSpriteAnimation restingAnimation, 
                        Properties properties,
                        float x, 
                        float y) {
        super(animation.update(0), properties, x, y);
        this.restingAnimation = restingAnimation;
        
        useRestAnimation = true;
        this.animation = animation;
        playing = false; 
    }
    
    public AdvancedAnimatedUnit(FrameData[] frames, 
                        TextureRegion initialFrame, 
                        Properties properties,
                        float x, 
                        float y) {
        super(initialFrame, properties, x, y);
        this.initialFrame = initialFrame;

        useRestAnimation = false;
        animation = new AdvancedSpriteAnimation(frames);
        playing = false; 
    }

    public FrameData getCurrentFrame() {
        return animation.getCurrentFrame();
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
    
    public void setAnimationFrames(FrameData[] frames) {
        animation = new AdvancedSpriteAnimation(frames);
    }
    
    public AdvancedSpriteAnimation getAnimation() {
        return animation;
    }
    
    public AdvancedSpriteAnimation getRestingAnimation() {
        return restingAnimation;
    }
    
    public void setAnimation(AdvancedSpriteAnimation animation) {
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
