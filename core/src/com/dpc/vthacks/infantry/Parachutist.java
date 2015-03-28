package com.dpc.vthacks.infantry;

import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.animation.AnimatedUnit;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.Properties;

public class Parachutist extends AnimatedUnit {
    private boolean isFalling;
    private float fallTargetX, fallTargetY;
    
    public Parachutist(String currentState,
                       AnimatedUnitProperties<SpriteAnimation> properties, 
                       float x, float y) {
        super(currentState, properties, x, y);
        
        isFalling = true;
    }

    @Override
    public void reset() {
        super.reset();
        isFalling = true;
        fallTargetX = 0;
        fallTargetY = 0;
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(isFalling) {
            if(isFalling) {
                addPos((fallTargetX - getX()) * delta, (fallTargetY - getY()) * delta);
                
                if(MathUtil.dst(getX(), getY(), fallTargetX, fallTargetY) < 5) {
                    isFalling = false;
                }
            }
        }
    }
    
    @Override
    public void onDeath(Unit Killer) {
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }

    @Override
    public void onDamageTaken(Unit attacker, float amount) {

    }

    public void setFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }
    
    public void setFallTargetX(float fallTargetX) {
        this.fallTargetX = fallTargetX;
    }
    
    public void setFallTargetY(float fallTargetY) {
        this.fallTargetY = fallTargetY;
    }
    
    public float getFallTargetX() {
        return fallTargetX;
    }
    
    public float getFallTargetY() {
        return fallTargetY;
    }
    
    public boolean isFalling() {
        return isFalling;
    }
}
