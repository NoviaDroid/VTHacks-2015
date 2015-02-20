package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dpc.vthacks.properties.Properties;

public abstract class DynamicGameObject extends GameObject {
    private Vector2 vel;
    
    public DynamicGameObject(TextureRegion region, Properties properties) {
        super(region, properties.getX(), properties.getY());

        vel = new Vector2(properties.getVelX(), properties.getVelY());
    }
    
    @Override
    public abstract void update(float delta);
    
    @Override
    public abstract void render();
    
    public void applyVel(Vector2 vel) {
        addPos(vel.x, vel.y);
    }
    
    public Vector2 getVel() {
        return vel;
    }
    
    public void setVel(Vector2 vel) {
        this.vel = vel;
    }
    
    public void setVelX(float x) {
        vel.x = x;
    }
    
    public void setVelY(float y) {
        vel.y = y;
    }
    
    public float getVelX() {
        return vel.x;
    }
    
    public float getVelY() {
        return vel.y;
    }
}
