package com.dpc.vthacks.plane;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.LevelProperties;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.AnimatedUnit;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.input.GameToolbar;
import com.dpc.vthacks.properties.Properties;

public class Player extends AnimatedUnit implements OnCompletionListener {
    public class Bomb extends AnimatedUnit implements Poolable {
        private static final int TARGET_FALL_ROTATION = -90;
        private final float EXPLOSION_WIDTH, EXPLOSION_HEIGHT, NORMAL_WIDTH, NORMAL_HEIGHT;
       
        public Bomb(AtlasRegion[] frames, TextureRegion initialFrame, Properties properties) {
            super(frames, initialFrame, properties, 0.1f);
            
            int rand = MathUtils.random(4, 8);
            NORMAL_WIDTH = initialFrame.getRegionWidth();
            NORMAL_HEIGHT = initialFrame.getRegionHeight();
            EXPLOSION_WIDTH = NORMAL_WIDTH * rand;
            EXPLOSION_HEIGHT = NORMAL_HEIGHT * rand;
            
            setPlaying(false);
        }
        
        @Override
        public void update(float delta) {
            super.update(delta);
                 
            if(isAnimationFinished()) {
                Factory.bombPool.free(this);
                bombs.removeValue(this, false);
            }
            
            if(isPlaying()) {
                setSize(EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
                
                // Apply a linear interpolation on the rotation
                setRotation(getRotation() + (TARGET_FALL_ROTATION - getRotation()) * delta);       
            }
            else {
                setSize(NORMAL_WIDTH, NORMAL_HEIGHT);
                applyVel(LevelProperties.GRAVITY.cpy().sub(LevelProperties.GRAVITY.x, getVelY()));
            }
        }

        @Override
        public void render() {
            draw(App.batch);
       }

        @Override
        public void reset() {
            setRegion(getInitialFrame());
            setSize(getInitialFrame().getRegionWidth(), getInitialFrame().getRegionHeight());
            setRotation(0);
            setPosition(0, 0);
            setRotation(0);
            setPlaying(false);
        }
        
        public void triggerExplosion() {
            Assets.playExplosion();
            setRotation(0);
            setPosition(getX() - (getWidth() * 0.5f), getY() - (getHeight() * 0.5f));
            setPlaying(true);
        }
        
        @Override
        public void onDeath() {
        
        }

        @Override
        public void attack(Unit enemy) {
        
        }

        @Override
        public void onDamageTaken(float amount) {
        }

        @Override
        public void onCollision(Collidable obj) {
        }
    }

    private static final float PLUMMIT_TIME = 0.05f; // If no positive force applied in this time, plane will plummit
    private static final int FALL_ROTATION = -10, RISE_ROTATION = 10, FALL_DELTA_FACTOR = 2, RISE_DELTA_FACTOR = 1;
    private static float goalExperience = 100;
    
    private boolean rising, headingEast, strafing;
    private int targetRotation, money, level;
    private float experience, plummitTimer;
    private Array<Bomb> bombs;
    
    public Player(AtlasRegion[] regions, TextureRegion initialFrame, Properties properties) {
        super(regions, initialFrame, properties, 0.1f);
        
        bombs = new Array<Bomb>(15);  
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        // Apply gravity, velocity, and rotation to the plane
        applyForce(delta);
        
        // Make each bomb continue to fall
        for(Bomb b : bombs) {
            b.update(delta);
        }
    }

    @Override
    public void render() {
        draw(App.batch);
        
        for(Bomb b : bombs) {
            b.render();
        }
    }

    @Override
    public void onCollision(Collidable obj) {
    
    }
    
    private void applyForce(float delta) {
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
    }
    
    @Override
    public void attack(Unit enemy) {
        
    }
    
    @Override
    public void onDamageTaken(float amount) {
        
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
  
    @Override
    public void onCompletion(Music music) {
        // If the strafe sound is done, disable strafing
        strafing = false;
    }
    
    @Override
    public void onDeath() {
    
    }
    
    @Override
    public void dispose() {
        super.dispose();

        for (Bomb b : bombs) {
            b.dispose();
        }
    }
  
    public void takeMoney(int amount) {
        money -= amount;
    }
    
    public void addMoney(int amount) {
        money += amount;
        GameToolbar.setMoney(money);
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
    
    public int getMoney() {
        return money;
    }
    
    public int getExperience() {
        return (int) experience;
    }
    
    public void setStrafing(boolean strafe) {
        this.strafing = strafe;
        
        setPlaying(strafing);
    }
    
    public static int getGoalExp() {
        return (int) goalExperience;
    }

    public Bomb createBomb(AtlasRegion[] explosionFrames, TextureRegion bomb, Properties bombProperties) {
        return new Bomb(explosionFrames, bomb, bombProperties);
    }
}
