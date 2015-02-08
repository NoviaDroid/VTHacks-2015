package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.gameobject.DynamicGameObject;

public abstract class Unit extends DynamicGameObject {
    private float damage, health, range;

    public Unit(TextureRegion region, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(region, velX, velY, x, y);
        this.range = range;
        this.health = health;
        this.damage = damage;
    }

    @Override
    public abstract void update(float delta);

    @Override
    public abstract void render();

    public abstract void attack(Unit enemy);
    
    public abstract void takeDamage(Unit attacker);
    
    
    public abstract void move();
     
    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getHealth() {
        return health;
    }
    
    public void setHealth(float health) {
        this.health = health;
    }
    
    public float getDamage() {
        return damage;
    }
    
    public void setDamage(float damage) {
        this.damage = damage;
    }

    public boolean inRange(Unit u1) {
        
        return false;
    }
}