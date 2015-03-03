package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.animation.AnimatedUnit;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.properties.Properties;

public class Parachutist extends AnimatedUnit {
    private boolean isFalling;
    private float fallTargetX, fallTargetY;
    
    public Parachutist(SpriteAnimation animation,
                       SpriteAnimation restingAnimation, Properties properties, 
                       float x, float y) {
        super(animation, restingAnimation, properties, x, y);
        
        isFalling = true;
    }
    
    public Parachutist(SpriteAnimation animation, 
                TextureRegion initialFrame, 
                Properties properties,
                float x, 
                float y) {
        super(animation, initialFrame, properties, x, y);
        
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
    public void attack() {
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }

    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        super.onDamageTaken(attacker, amount);
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
