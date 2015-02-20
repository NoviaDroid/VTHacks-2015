package com.dpc.vthacks.properties;

import com.badlogic.gdx.math.Vector2;

public class Properties {
    private Vector2 pos, vel;
    private float range, damage, health, maxHealth;
    private int cost;
    
    public Properties(Properties cpy) {
        pos = cpy.pos.cpy();
        vel = cpy.vel.cpy();
        range = cpy.getRange();
        damage = cpy.getDamage();
        health = cpy.getHealth();
        maxHealth = cpy.getMaxHealth();
        cost = cpy.getCost();
    }
    
    public Properties() {
        pos = new Vector2();
        vel = new Vector2();
    }
    
    public Properties(Vector2 pos, Vector2 vel, float range, float damage, float health, int cost) {
        this.pos = pos;
        this.vel = vel;
        this.range = range;
        this.damage = damage;
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
    
    public float getDamage() {
        return damage;
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
    
    public void setDamage(float damage) {
        this.damage = damage;
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
}
