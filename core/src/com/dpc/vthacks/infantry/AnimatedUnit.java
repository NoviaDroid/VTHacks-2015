package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.properties.Properties;

public abstract class AnimatedUnit extends Unit {
    private SpriteAnimation animation, restingAnimation;
    private TextureRegion initialFrame;
    private boolean playing;
    private final boolean useRestAnimation;
    
    public AnimatedUnit(AtlasRegion[] frames, SpriteAnimation restingAnimation, Properties properties, float animationSpeed) {
        super(frames[0], properties);
        this.restingAnimation = restingAnimation;
        
        useRestAnimation = true;
        animation = new SpriteAnimation(frames, animationSpeed);
        playing = false; 
    }
    
    public AnimatedUnit(AtlasRegion[] frames, TextureRegion initialFrame, Properties properties, float animationSpeed) {
        super(initialFrame, properties);
        this.initialFrame = initialFrame;

        useRestAnimation = false;
        animation = new SpriteAnimation(frames, animationSpeed);
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
    
    public void setAnimationFrames(AtlasRegion[] frames, float speed) {
        animation = new SpriteAnimation(frames, speed);
    }
    
    public SpriteAnimation getAnimation() {
        return animation;
    }
    
    public SpriteAnimation getRestingAnimation() {
        return restingAnimation;
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
