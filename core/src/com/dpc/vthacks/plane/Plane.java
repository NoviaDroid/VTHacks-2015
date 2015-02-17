package com.dpc.vthacks.plane;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.input.GameToolbar;

public class Plane extends Unit implements OnCompletionListener {
    private static final float PLUMMIT_TIME = 0.05f; // If no positive force applied in this time, plane will plummit
    private static final int FALL_ROTATION = -5, RISE_ROTATION = 10, FALL_DELTA_FACTOR = 3, RISE_DELTA_FACTOR = 1;
    private int targetRotation, money, level;
    private float experience;
    private static float goalExperience = 100;
    private boolean rising, headingEast, strafing;
    private float plummitTimer;
    private Array<Bomb> bombs;
    private SpriteAnimation fireAnimation;
    
    public Plane(AtlasRegion[] regions, TextureRegion initialRegion, float range, float damage, float health, float maxHealth, float velX, float velY, float x, float y) {
        super(initialRegion, range, damage, health, maxHealth, velX, velY, x, y);
        
        bombs = new Array<Bomb>(45);  
        fireAnimation = new SpriteAnimation(regions, 0.1f);
    }

    @Override
    public void update(float delta) {
        if(rising) {
            addVel();
            
            if(headingEast) {
                setRotation(getRotation() + (targetRotation - getRotation()) * delta * RISE_DELTA_FACTOR);
            }
            else {
                setRotation(getRotation() - (targetRotation + getRotation()) * delta * RISE_DELTA_FACTOR);  
            }
        }
        else {
            plummitTimer += delta;
            
            // Begin to plummit if no pos force has been applied lately
            if(plummitTimer >= PLUMMIT_TIME) {
                if(headingEast) {
                    setRotation(getRotation() + (targetRotation - getRotation()) * delta * FALL_DELTA_FACTOR);
                }
                else {
                    setRotation(getRotation() - (targetRotation + getRotation()) * delta * FALL_DELTA_FACTOR);  
                }
            }
        }
        
        if(strafing) {
            setRegion(fireAnimation.update(delta));
        }
        else {
            setRegion(Assets.plane);
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
        setHealth(getHealth() - attacker.getDamage());
        GameToolbar.setHealth(getHealth());
    }
    
    public void onLevelReached() {
        
    }
    
    public void setStrafing(boolean strafe) {
        this.strafing = strafe;
    }
    
    public static int getGoalExp() {
        return (int) goalExperience;
    }
    
    public void addExperience(int amount) {
        // Check for a level ups
        if(experience >= goalExperience) {
            experience = 1;
            level++;
            goalExperience = (float) Math.pow(level, 4) + 100;
            return;
        }
        else {
            experience += amount;
        }
        
        GameToolbar.setExperience((int) experience);
    }
    
    public void takeMoney(int amount) {
        money -= amount;
    }
    
    public void addMoney(int amount) {
        money += amount;
        GameToolbar.setMoney(money);
    }
    
    public int getMoney() {
        return money;
    }
    
    public int getExperience() {
        return (int) experience;
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
    
    public void setHeadingEast(boolean headingEast) {
        this.headingEast = headingEast;
    }
    
    public boolean isHeadingEast() {
        return headingEast;
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

    @Override
    public void onCompletion(Music music) {
        strafing = false;
        System.out.println("on completion");
    }
  
}
