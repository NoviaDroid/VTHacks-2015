package com.dpc.vthacks.plane;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.screens.GameScreen;

public class Bomb extends DynamicGameObject implements Poolable {
    private static final int TARGET_FALL_ROTATION = -90;
    private final int EXP_WIDTH, EXP_HEIGHT;
    private final int N_WIDTH, N_HEIGHT;
    private boolean isDead;
    private float damage;
    private SpriteAnimation explosion;
    
    public Bomb(float damage, float velX, float velY, float x, float y) {
        super(Assets.bomb, velX, velY, x, y);
        this.damage = damage;
        
        N_WIDTH = getRegionWidth();
        N_HEIGHT = getRegionHeight();
        int fct = MathUtils.random(2, 5);
                
        EXP_WIDTH = Assets.getExplosionFrames()[0].getRegionWidth() * fct;
        EXP_HEIGHT = Assets.getExplosionFrames()[0].getRegionHeight() * fct;
        
        explosion = new SpriteAnimation(Assets.getExplosionFrames(), 0.1f);
    }

    public boolean isAlive() {
        return isDead;
    }
    
    @Override
    public void update(float delta) {
        if(!isDead) {
            if(getWidth() == EXP_WIDTH && getHeight() == EXP_HEIGHT) {
                setSize(N_WIDTH, N_HEIGHT);
            }
            
            // Apply gravity
            applyVel(GameScreen.gravity.cpy().sub(GameScreen.gravity.x, getVelY()));
            
            // Gradually rotate the bomb
            setRotation(getRotation() + (TARGET_FALL_ROTATION - getRotation()) * delta * 2);
        }
        else {
            if(getWidth() == N_WIDTH && getHeight() == N_HEIGHT) {
                setSize(EXP_WIDTH, EXP_HEIGHT);
            }
            
            setRegion(explosion.update(delta));

            // If we are done, enough with this object.
            if(explosion.getAnimation().isAnimationFinished(explosion.getStateTime())) {
                Factory.bombPool.free(this);
                GameScreen.getPlayer().getBombs().removeValue(this, false);
            }
        }
    }

    public float getDamage() {
        return damage;
    }
    
    @Override
    public void reset() {
        isDead = false;
        setRegion(Assets.bomb);
        setRotation(0);
        setPosition(0, 0);
        explosion = new SpriteAnimation(Assets.getExplosionFrames(), 0.1f);
        setRotation(0);
        setSize(N_WIDTH, N_HEIGHT);
    }
    
    public void triggerExplosion() {
        if(!isDead) {
            Assets.playExplosion();
            setRotation(0);
            setPosition(getX() - (getWidth() * 0.5f), getY() - (getHeight() * 0.5f));
        }
        
        isDead = true;
    }
    
    @Override
    public void render() {
        draw(App.batch);
    }

}
