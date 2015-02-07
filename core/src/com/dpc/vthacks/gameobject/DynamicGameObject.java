package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class DynamicGameObject extends GameObject {
    private Vector2 velocity;
    
    public DynamicGameObject(TextureRegion region, Vector2 vel, float x, float y) {
        super(region, x, y);
        velocity = vel;
    }
    
    @Override
    public abstract void update(float delta);
    @Override
    public abstract void render();
    
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }
}