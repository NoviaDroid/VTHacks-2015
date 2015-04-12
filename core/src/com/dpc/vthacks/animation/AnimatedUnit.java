package com.dpc.vthacks.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameEvent;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.AnimatedUnitProperties;

/**
 * Animated unit with meta data for each frame
 * @author Daniel
 *
 */
public abstract class AnimatedUnit extends Unit {
    private ObjectMap<String, SpriteAnimation> stateAnimations;
    private String currentState;
    private boolean playing;

    public AnimatedUnit(String currentState,
                        AnimatedUnitProperties<SpriteAnimation> properties,
                        float x, 
                        float y) {
        super(properties.getStateAnimations().get(currentState).getAnimation().getKeyFrames()[0], 
              properties, 
              x, 
              y);

        this.stateAnimations = properties.getStateAnimations();
        this.currentState = currentState;
        
        playing = false; 
    }

    @Override
    public abstract void onEvent(GameEvent e);
    
    public void update(float delta) {
        if(playing) {
            stateAnimations.get(currentState).update(delta);
            setRegion(stateAnimations.get(currentState).getCurrentFrame());
        }
    }
    
    public void render() {
        if(isVisible()) {
            draw(App.batch);
        }
    }
    
    
    public void setStateAnimations(
            ObjectMap<String, SpriteAnimation> stateAnimations) {
        this.stateAnimations = stateAnimations;
    }
    
    public void setStateAnimationFrames(TextureRegion[] frames, String state, float frameTime) {
        stateAnimations.get(state).setFrames(frames, frameTime);
    }
    
    public SpriteAnimation getCurrentAnimation() {
        return stateAnimations.get(currentState);
    }
    
    public void setState(String state) {
        this.currentState = state;
    }
    
    public String getCurrentState() {
        return currentState;
    }
    
    public boolean isCurrentAnimationFinished() {
        return stateAnimations.get(currentState)
                .getAnimation()
                .isAnimationFinished(stateAnimations.get(currentState).getStateTime());
    }
    
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    
    public boolean isPlaying() {
        return playing;
    }
}
