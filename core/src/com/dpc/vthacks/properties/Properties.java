package com.dpc.vthacks.properties;

import com.badlogic.gdx.math.Vector2;

/**
 * Encapsulates properties for a given unit
 * @author Daniel Christopher
 * @version 3/5/15
 *
 */
public class Properties {
    private Vector2 minVel, maxVel, vel;
    private float frameTime;
    private float range, minDamage, maxDamage, health, maxHealth, scaleX, scaleY;
    private int cost;
    
    public Properties(Properties cpy) {
        vel = cpy.vel.cpy();
        range = cpy.range;
        minDamage = cpy.minDamage;
        maxDamage = cpy.maxDamage;
        health = cpy.health;
        maxHealth = cpy.maxHealth;
        cost = cpy.cost;
        maxVel = cpy.maxVel.cpy();
        minVel = cpy.minVel.cpy();
        frameTime = cpy.frameTime;
        scaleX = cpy.scaleX;
        scaleY = cpy.scaleY;
    }
    
    public Properties() {
        vel = new Vector2();
        minVel = new Vector2();
        maxVel = new Vector2();
    }

    @Override
    public String toString() {
        return String.format("%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n",
                            "Min Vel: " + minVel,
                            "Max Vel: " + maxVel,
                            "Vel: " + vel,
                            "Frame Time: " + frameTime,
                            "Range: " + range,
                            "Min Damage: " + minDamage,
                            "Max Damage: " + maxDamage,
                            "Health: " + health,
                            "Max Health: " + maxHealth,
                            "Scale X: " + scaleX,
                            "Scale Y: " + scaleY,
                            "Cost: " + cost);
    }
    
    public float getScaleX() {
        return scaleX;
    }
    
    public float getScaleY() {
        return scaleY;
    }
    
    public Properties scaleX(float scaleX) {
        this.scaleX = scaleX;
        
        return this;
    }
    
    public Properties scaleY(float scaleY) {
        this.scaleY = scaleY;
        
        return this;
    }
    
    public Properties frameTime(float frameTime) {
        this.frameTime = frameTime;
        
        return this;
    }
    
    public Properties cost(int cost) {
        this.cost = cost;
        
        return this;
    }
    
    public Properties health(float health) {
        this.health = health;
        
        return this;
    }
    
    public Properties maxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
        
        return this;
    }
    
    public Properties minDamage(float minDamage) {
        this.minDamage = minDamage;
        
        return this;
    }
    
    public Properties range(float range) {
        this.range = range;
        
        return this;
    }
    
    public Properties vel(float vx, float vy) {
        vel.set(vx, vy);
        
        return this;
    }
    
    public Properties minVel(float vx, float vy) {
        minVel.set(vx, vy);
        
        return this;
    }
    
    public Properties maxVel(float vx, float vy) {
        maxVel.set(vx, vy);
        
        return this;
    }
    
    public Properties maxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        
        return this;
    }
    
    public Properties build() {
        return this;
    }

    public Vector2 getMinVel() {
        return minVel;
    }

    public Vector2 getMaxVel() {
        return maxVel;
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
    
    public float getFrameTime() {
        return frameTime;
    }

    public float getRange() {
        return range;
    }

    public float getMinDamage() {
        return minDamage;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public int getCost() {
        return cost;
    }
}
