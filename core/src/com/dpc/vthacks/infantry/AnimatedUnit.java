package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.properties.Properties;

public abstract class AnimatedUnit extends Unit {
    private SpriteAnimation animation;
    private TextureRegion initialFrame;
    private boolean playing;
    
    public AnimatedUnit(AtlasRegion[] frames, TextureRegion initialFrame, Properties properties, float animationSpeed) {
        super(initialFrame, properties);
        this.initialFrame = initialFrame;

        animation = new SpriteAnimation(frames, animationSpeed);
        playing = true; 
    }

    public void update(float delta) {
        super.update(delta);
        
        if(playing) {
            setRegion(animation.update(delta));
        }
        else {
            setRegion(animation.update(delta));
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
    
    public boolean isAnimationFinished() {
        return animation.getAnimation().isAnimationFinished(animation.getStateTime());
    }
    
    public void setPlaying(boolean playing) {
        this.playing = playing;
        
        // If not playing anymore, change back to the initial frame
        if(!playing) {
            setRegion(initialFrame);
        }
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    public TextureRegion getInitialFrame() {
        return initialFrame;
    }
}
