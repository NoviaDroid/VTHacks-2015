package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dpc.vthacks.properties.Properties;

public abstract class DynamicGameObject extends GameObject {
    private Vector2 finalDestination, currentTarget; // Final destination, temporary target
    private Vector2 vel; // Moves the object
    private Vector2 velSCL; // Factor on how much to move
        
    public DynamicGameObject(TextureRegion region, Properties properties, float x, float y) {
        super(region, x, y);

        velSCL = properties.getVel();
        
        init();
        System.err.println(velSCL + " OKAY");
    }
    
    @Override
    public abstract void update(float delta);
    
    @Override
    public abstract void render();
    
    public void setCurrentTarget(float x, float y) {
        currentTarget.set(x, y);
        
        vel.set(currentTarget.x - getX(), currentTarget.y - getY());
        vel.nor(); 

        vel.x *= velSCL.x;
        vel.y *= velSCL.y;
    }
    
    public Vector2 getVel() {
        return vel;
    }
    
    public float getVelX() {
        return vel.x;
    }
    
    public float getVelY() {
        return vel.y;
    }
    
    public Vector2 getVelSCL() {
        return velSCL;
    }
    
    public void setVelX(float x) {
        vel.x = x;
    }
    
    public void setVelY(float y) {
        vel.y = y;
    }
    
    public void setVel(Vector2 vel) {
        this.vel = vel;
    }
    
    public float getVelocityScalarX() {
        return velSCL.x;
    }
    
    public float getVelocityScalarY() {
        return velSCL.y;
    }
    
    public Vector2 getCurrentTarget() {
        return currentTarget;
    }
    
    public float getCurrentTargetX() {
        return currentTarget.x;
    }
    
    public float getCurrentTargetY() {
        return currentTarget.y;
    }
    
    public void applyVel(Vector2 vel) {
        addPos(vel.x, vel.y);
    }

    public Vector2 getFinalDestination() {
        return finalDestination;
    }
    
    public void setFinalDestination(Vector2 finalDestination) {
        this.finalDestination = finalDestination;
    }
    
    public void init() {
        finalDestination = new Vector2(0, 50);
        currentTarget = new Vector2(finalDestination);
        vel = new Vector2(5, 5);
    }
    
    public void setVelSCL(Vector2 velSCL) {
        this.velSCL = velSCL;
    }
    
    public void setCurrentTarget(Vector2 currentTarget) {
        this.currentTarget = currentTarget;
    }
}
