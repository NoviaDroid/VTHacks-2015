package com.dpc.vthacks.plane;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Unit;

public class Plane extends Unit {
    private static final float PLUMMIT_TIME = 0.05f; // If no positive force applied in this time, plane will plummit
    private static final int FALL_ROTATION = -5, RISE_ROTATION = 15, FALL_DELTA_FACTOR = 4, RISE_DELTA_FACTOR = 2;
    private int targetRotation, experience;
    private boolean rising;
    private float plummitTimer;
    private Array<Bomb> bombs;
    
    public Plane(TextureRegion region, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(region, range, damage, health, velX, velY, x, y);
        
        bombs = new Array<Bomb>(45);
    }

    @Override
    public void update(float delta) {
        if(rising) {
            addVel();
            setRotation(getRotation() + (targetRotation - getRotation()) * delta * RISE_DELTA_FACTOR);
        }
        else {
            plummitTimer += delta;
            
            // Begin to plummit if no pos force has been applied lately
            if(plummitTimer >= PLUMMIT_TIME) {
                setRotation(getRotation() + (targetRotation - getRotation()) * delta * FALL_DELTA_FACTOR);
            }
        }
        
        // Make each bomb continue to fall
        for(Bomb b : bombs) {
            b.update(delta);
        }
    }

    @Override
    public void render() {
        draw(App.batch);
        
        for(Bomb b : bombs) {
            b.draw(App.batch);
        }
    }

    @Override
    public void attack(Unit enemy) {
        
    }

    @Override
    public void takeDamage(Unit attacker) {
        
    }
    
    public void strafe() {
        
    }
    
    public void addExperience(int ammount) {
        
    }
    
    public void increaseElevation() {
        rising = true;
        targetRotation = RISE_ROTATION;
        plummitTimer = 0;    
    }
    
    public void releaseBomb() {
        Bomb b = Factory.bombPool.obtain();
        b.setX(getX() + (getWidth() * 0.5f));
        b.setY(getY());
        
        bombs.add(b);
    }
    
    public void decreaseElevation() {
        rising = false;
        targetRotation = FALL_ROTATION;
    }
   
    public Array<Bomb> getBombs() {
        return bombs;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        
        for(Bomb b : bombs) {
            b.dispose();
        }
    }
  
}
