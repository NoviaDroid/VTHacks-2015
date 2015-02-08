package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.army.Army;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Sounds;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.DynamicGameObject;

public class TankShell extends DynamicGameObject implements Poolable {
    private float targetX, targetY;
    private SpriteAnimation animation;
    private boolean animated;
    public Tank parentTank;
    private final int EXP_WIDTH, EXP_HEIGHT;
    private final int N_WIDTH, N_HEIGHT;
    
    public TankShell(TextureRegion region, Tank parentTank, float velX, float velY, float targetX, float targetY, float x, float y) {
        super(region, velX, velY, x, y);
        
        N_WIDTH = getRegionWidth();
        N_HEIGHT = getRegionHeight();
        EXP_WIDTH = Assets.explosionFrames[0].getRegionWidth() * 3;
        EXP_HEIGHT = Assets.explosionFrames[0].getRegionHeight() * 3;
        
        this.targetX = targetX;
        this.targetY = targetY;
        animation = new SpriteAnimation(Assets.explosionFrames, 0.09f);
        
   //     setSize(region.getRegionWidth() * 2f, region.getRegionHeight() * 2f);
    }

    @Override
    public void update(float delta) {
        if(!animated) {
            if(getWidth() == EXP_WIDTH && getHeight() == EXP_HEIGHT) {
                setSize(N_WIDTH, N_HEIGHT);
            }
            
            setX(getX() + (getTargetX() - getX()) * delta * 10);
            setY(getY() + (getTargetY() - getY()) * delta * 10);
        }
        else {
            if(getWidth() == N_WIDTH && getHeight() == N_HEIGHT) {
                setSize(EXP_WIDTH, EXP_HEIGHT);
            }
            
            setRegion(animation.update(delta));
            
            if(animation.getAnimation().isAnimationFinished(animation.getStateTime())) {
                Factory.tankShellPool.free(this);
                parentTank.shell = null;
            }
        }
    }

    public void triggerExplosion() {
        if(!animated) {
            Sounds.explosion.play();
            animated = true;
        }
        
    }
    
    @Override
    public void render() {
        draw(App.batch);
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    @Override
    public void reset() {

    }
 
}
