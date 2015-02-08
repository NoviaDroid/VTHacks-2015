package com.dpc.vthacks.plane;

import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.screens.GameScreen;

public class Bomb extends DynamicGameObject {
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
            
            if(getTexture().equals(Assets.explosionFrames[Assets.explosionFrames.length - 1])) {
                
            }
        }
    }

    public void triggerExplosion() {
        isDead = true;
    }
    
    @Override
    public void render() {
        draw(App.batch);
    }

}
