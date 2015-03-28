package com.dpc.vthacks.properties;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Encapsulates data for an animated unit
 * @author Daniel Christopher
 * @version 3/5/15
 *
 */
public class AnimatedUnitProperties<T> extends Properties {
    private ObjectMap<String, T> stateAnimations;

    public AnimatedUnitProperties() {
        super();
    }
    
    public AnimatedUnitProperties(AnimatedUnitProperties<T> cpy) {
        super(cpy);

        stateAnimations = cpy.stateAnimations;
    }
    
    
    @Override
    public String toString() {
        return super.toString() + String.format("%s", stateAnimations.toString());
    }
    
    @Override
    public AnimatedUnitProperties<T> scaleX(float scaleX) {
        super.scaleX(scaleX);
        
        return this;
    }
    
    @Override
    public AnimatedUnitProperties<T> scaleY(float scaleY) {
        super.scaleY(scaleY);
        
        return this;
    }
    
    @Override
    public AnimatedUnitProperties<T> frameTime(float frameTime) {
        super.frameTime(frameTime);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> cost(int cost) {
        super.cost(cost);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> health(float health) {
        super.health(health);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> maxDamage(float maxDamage) {
        super.maxDamage(maxDamage);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> minDamage(float minDamage) {
        super.minDamage(minDamage);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> range(float range) {
        super.range(range);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> vel(float vx, float vy) {
        super.vel(vx, vy);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> minVel(float vx, float vy) {
        super.minVel(vx, vy);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> maxVel(float vx, float vy) {
        super.maxVel(vx, vy);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> maxHealth(float maxHealth) {
        super.maxHealth(maxHealth);
        
        return this;
    }

    @Override
    public AnimatedUnitProperties<T> build() {
        super.build();
        
        return this;
    }

    public AnimatedUnitProperties<T> stateAnimations(ObjectMap<String, T> stateAnimations) {
        this.stateAnimations = stateAnimations;
        
        return this;
    }
    
    public ObjectMap<String, T> getStateAnimations() {
        return stateAnimations;
    }
}
