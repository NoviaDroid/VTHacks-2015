package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class DynamicGameObject extends GameObject {
    private float velX, velY;
    
    public DynamicGameObject(TextureRegion region, float velX, float velY, float x, float y) {
        super(region, x, y);
        this.velX = velX;
        this.velY = velY;
    }
    
    @Override
    public abstract void update(float delta);
    
    @Override
    public abstract void render();
    
    public void addVel() {
        addPos(velX, velY);
    }
    
    public void subVel() {
        subPos(velX, velY);
    }
    
    public void applyVel(Vector2 vel) {
        addPos(vel.x, vel.y);
    }
    
    public void setVelX(float velX) {
        this.velX = velX;
    }
    
    public void setVel(float velX, float velY) {
        this.velX = velX;
        this.velY = velY;
    }
    
    public float getVelX() {
        return velX;
    }
    
    public float getVelY() {
        return velY;
    }
}
