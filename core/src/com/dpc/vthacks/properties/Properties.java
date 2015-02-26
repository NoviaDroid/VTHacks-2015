package com.dpc.vthacks.properties;

import com.badlogic.gdx.math.Vector2;

public class Properties {
    private Vector2 pos, minVel, maxVel, vel;
    private float range, minDamage, maxDamage, health, maxHealth;
    private int cost;
    
    public Properties(Properties cpy) {
        pos = cpy.pos.cpy();
        vel = cpy.vel.cpy();
        range = cpy.getRange();
        minDamage = cpy.getMinDamage();
        maxDamage = cpy.getMaxDamage();
        health = cpy.getHealth();
        maxHealth = cpy.getMaxHealth();
        cost = cpy.getCost();
        maxVel = cpy.getMaxVel();
        minVel = cpy.getMinVel();
    }
    
    public Properties() {
        pos = new Vector2();
        vel = new Vector2();
        minVel = new Vector2();
        maxVel = new Vector2();
    }
    
    public Properties(Vector2 pos, Vector2 vel, Vector2 minVel, Vector2 maxVel, float range, float minDamage, float maxDamage, float health, int cost) {
        this.pos = pos;
        this.vel = vel;
        this.range = range;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.minVel = minVel;
        this.maxVel = maxVel;
        this.health = health;
        this.maxHealth = health;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
    
    public float getMaxHealth() {
        return maxHealth;
    }
    
    public float getMaxDamage() {
        return maxDamage;
    }
    
    public float getMinDamage() {
        return minDamage;
    }
    
    public float getHealth() {
        return health;
    }
    
    public Vector2 getPos() {
        return pos;
    }
    
    public float getRange() {
        return range;
    }
    
    public Vector2 getVel() {
        return vel;
    }
    
    public void addVel() {
        pos.add(vel);
    }
    
    public Properties addPos(Vector2 vector) {
        pos.add(vector);

        return this;
    }
    
    public Properties sclPos(float scl) {
        pos.scl(scl);

        return this;
    }
    
    public void setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
    }
    
    public void setMinDamage(float minDamage) {
        this.minDamage = minDamage;
    }
    
    public void setHealth(float health) {
        this.health = health;
    }
    
    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
    
    public void setRange(float range) {
        this.range = range;
    }
    
    public void setVel(Vector2 vel) {
        this.vel = vel;
    }
    
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public void setX(float x) {
        pos.x = x;
    }
    
    public void setY(float y) {
        pos.y = y;
    }
    
    public float getX() {
        return pos.x;
    }
    
    public float getY() {
        return pos.y;
    }
    
    public Vector2 getMaxVel() {
        return maxVel;
    }
    
    public Vector2 getMinVel() {
        return minVel;
    }
    
    public float getVelX() {
        return vel.x;
    }
    
    public float getVelY() {
        return vel.y;
    }
    
    public void setVelX(float velX) {
        vel.x = velX;
    }
    
    public void setVelY(float velY) {
        vel.y = velY;
    }

    public void setMaxVel(Vector2 maxVel) {
        this.maxVel = maxVel;
    }
    
    public void setMinVel(Vector2 minVel) {
        this.minVel = minVel;
    }
    
    public void setPos(float x, float y) {
        pos.x = x;
        pos.y = y;
    }
}
