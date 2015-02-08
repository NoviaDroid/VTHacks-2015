package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.gameobject.DynamicGameObject;

public class TankShell extends DynamicGameObject {
    private float targetX, targetY;
    
    public TankShell(TextureRegion region, float velX, float velY, float targetX, float targetY, float x, float y) {
        super(region, velX, velY, x, y);
        
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void update(float delta) {
        setX((getX() + (targetX - getX())) * delta * getVelX());
        setY((getY() + (targetY - getY())) * delta * getVelY());
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
 
}
