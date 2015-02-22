package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.properties.Properties;

public abstract class Unit extends Collidable {
    private Properties properties;
    private boolean moving, attacking;
    
    public Unit(TextureRegion region, Properties properties) {
        super(region, properties);

        this.properties = properties;
    }

    @Override
    public void update(float delta) {
        // Unit has died
        if(properties.getHealth() < 0) {
            onDeath();
        }
    }
    
    @Override
    public abstract void onCollision(Collidable obj);
        
    @Override
    public abstract void render();

    public abstract void onDeath();
    
    public abstract void attack(Unit enemy);

    public abstract void onDamageTaken(float amount);
    
    public Properties getProperties() {
        return properties;
    }
    
    public void subVel() {
        setPosition(getX() - properties.getVelX(), getY() - properties.getVelY());
    }
    
    public void addVel() {
        setPosition(getX() + properties.getVelX(), getY() + properties.getVelY());
    }
    
    public void applyGravity(Vector2 vector) {
        setPosition(getX() + vector.x, getY() + vector.y);
    }
    
    public void takeDamage(float damage) {
        properties.setHealth(properties.getHealth() - damage);
        onDamageTaken(damage);
    }
    
    public boolean inRange(Unit u1) {
        return MathUtil.dst(getX(), getY(), u1.getX(), u1.getY()) <= properties.getRange();
    }

    public boolean isMoving() {
        return moving;
    }
    
    public boolean isAttacking() {
        return attacking;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
