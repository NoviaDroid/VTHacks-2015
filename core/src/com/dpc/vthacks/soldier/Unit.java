package com.dpc.vthacks.soldier;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.gameobject.DynamicGameObject;

public abstract class Unit extends DynamicGameObject {
    private int cost;
    private float damage, health;
    
    public Unit(TextureRegion region, int cost, float damage, float health, float velX, float velY, float x, float y) {
        super(region, velX, velY, x, y);
        
        this.health = health;
        this.damage = damage;
        this.cost = cost;
    }

    @Override
    public abstract void update(float delta);

    @Override
    public abstract void render();

    public abstract void attack(Unit enemy);
    
    public abstract void takeDamage(Unit attacker);
    
    public abstract void move();
    
    public float getHealth() {
        return health;
    }
    
    public void setHealth(float health) {
        this.health = health;
    }
    
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public float getDamage() {
        return damage;
    }
    
    public void setDamage(float damage) {
        this.damage = damage;
    }
}
