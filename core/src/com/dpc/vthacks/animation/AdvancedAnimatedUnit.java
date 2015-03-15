package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.App;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.AnimatedUnitProperties;

/**
 * Animated unit with meta data for each frame
 * @author Daniel Christopher
 * @version 3/5/2015
 *
 */
public abstract class AdvancedAnimatedUnit extends Unit {
    private ObjectMap<String, AdvancedSpriteAnimation> stateAnimations;
    private boolean playing;
    private String currentState;
    
    public AdvancedAnimatedUnit(String currentState,
                                AnimatedUnitProperties<AdvancedSpriteAnimation> properties,
                                float x, 
                                float y) {
        super(properties.getStateAnimations().get(currentState).getCurrentFrame().getRegion(), 
              properties, x, y);

        this.stateAnimations = properties.getStateAnimations();
        this.currentState = currentState;
        playing = false; 
    }

    public void update(float delta) {
        super.update(delta);

        if(playing) {
            //System.out.println("setRegion(animation.update(delta));");
            setRegion(stateAnimations.get(currentState).update(delta));
        }
    }
    
    public void render() {
        if(isVisible()) {
            draw(App.batch);
        }
    }
    
    /**
     * Flips every animation
     * @param x Flip on the x axis ?
     * @param y Flip on the y axis ?
     */
    public void flipAll(boolean x, boolean y) {
        for(AdvancedSpriteAnimation animation : stateAnimations.values()) {
            for(TextureRegion region : animation.getAnimation().getKeyFrames()) {
                region.flip(x, y);
            }
        }
    }
    
    public void setStateFrames(FrameData[] frames, String state, float speed) {
        stateAnimations.get(state).setFrameData(frames, speed);
    }
    
    public AdvancedSpriteAnimation getStateAnimation(String state) {
        return stateAnimations.get(state);
    }
    
    public boolean isCurrentAnimationFinished() {
        return stateAnimations.get(currentState)
                .getAnimation()
                .isAnimationFinished(stateAnimations.get(currentState)
                .getStateTime());
    }
    
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    
    public FrameData getCurrentFrame() {
        return stateAnimations.get(currentState).getCurrentFrame();
    }
    
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
    
    public void setStateAnimations(ObjectMap<String, AdvancedSpriteAnimation> stateAnimations) {
        this.stateAnimations = stateAnimations;
    }
    
    public ObjectMap<String, AdvancedSpriteAnimation> getStateAnimations() {
        return stateAnimations;
    }
    
    public AdvancedSpriteAnimation getCurrentAnimation() {
        return stateAnimations.get(currentState);
    }
    
    public boolean isPlaying() {
        return playing;
    }
}
