package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.properties.Properties;

public abstract class Unit extends DynamicGameObject implements Poolable {
    private Properties properties;
    private Unit targetEnemy;
    private boolean moving, attacking;
    
    public Unit(TextureRegion region, Properties properties, float x, float y) {
        super(region, properties, x, y);

        this.properties = properties;
    }

    @Override
    public void update(float delta) { 
//        if(attacking) {
//            attack();
//        }
    }
    
    @Override
    public abstract void render();

    public abstract void onDeath(Unit killer);
    
    public abstract void attack();
    
    public abstract void attack(Unit enemy, float dmg);
    
    /*
     * Returns the distance between two units
     */
    public float dst(Unit u) {
        return MathUtil.dst(u.getX(), u.getY(),
                             getX(), getY());
    }
    
    public void onDamageTaken(Unit attacker, float amount) {

        // Unit has died
        if(properties.getHealth() <= 0) {
            onDeath(attacker);
        }
    }

    @Override
    public void reset() {
        properties.health(properties.getMaxHealth());
    }
    
    public Properties getProperties() {
        return properties;
    }
    
    public void setAttacking(boolean t) {
        attacking = t;
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
    
    public void takeDamage(Unit attacker, float damage) {
        properties.health(properties.getHealth() - damage);

        onDamageTaken(attacker, damage);
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
    
    public void setAttacking(boolean attacking, Unit target) {
        this.attacking = attacking;
        this.targetEnemy = target;
    }
    
    public Unit getTargetEnemy() {
        return targetEnemy;
    }
    
    public void setTargetEnemy(Unit targetEnemy) {
        this.targetEnemy = targetEnemy;
    }
}
