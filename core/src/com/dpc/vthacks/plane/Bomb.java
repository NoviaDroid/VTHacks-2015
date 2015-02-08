package com.dpc.vthacks.plane;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Sounds;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.screens.GameScreen;

public class Bomb extends DynamicGameObject implements Poolable {
    private static final int TARGET_FALL_ROTATION = -90;
    private boolean isDead;
    private SpriteAnimation explosion;
    
    public Bomb(float velX, float velY, float x, float y) {
        super(Assets.bomb, velX, velY, x, y);
        
        explosion = new SpriteAnimation(Assets.explosionFrames, 0.15f);
    }

    @Override
    public void update(float delta) {
        if(!isDead) {
            // Apply gravity
            applyVel(GameScreen.gravity.cpy().sub(0, getVelY()));
            addVel();
            
            // Gradually rotate the bomb
            setRotation(getRotation() + (TARGET_FALL_ROTATION - getRotation()) * delta * 2);
        }
        else {
            setRegion(explosion.update(delta));
            
            // If we are done, enough with this object.
            if(explosion.getAnimation().isAnimationFinished(explosion.getStateTime())) {
                Factory.bombPool.free(this);
                GameScreen.getPlayer().getBombs().removeValue(this, false);
            }
        }
    }

    @Override
    public void reset() {
        isDead = false;
        setRegion(Assets.bomb);
        setPosition(0, 0);
        explosion = new SpriteAnimation(Assets.explosionFrames, 0.15f);;
    }
    
    public void triggerExplosion() {
        if(!isDead) {
            Sounds.explosion.play();
        }
        
        isDead = true;
    }
    
    @Override
    public void render() {
        draw(App.batch);
    }

}
