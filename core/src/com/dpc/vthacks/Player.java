package com.dpc.vthacks;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.AnimatedUnit;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;

public class Player extends AnimatedUnit {
    private static final float PLUMMIT_TIME = 0.05f; // If no positive force applied in this time, plane will plummit
    private static final int FALL_ROTATION = -10, RISE_ROTATION = 10, FALL_DELTA_FACTOR = 2, RISE_DELTA_FACTOR = 1;
    private static float goalExperience = 100;
    private boolean rising, strafing;
    private int targetRotation, money, level;
    private float experience, plummitTimer;
    private Array<Bomb> bombs;
    private boolean isFlying, movingLeft;
    private AtlasRegion[] planeFrames, walkFrames;
    private TextureRegion nonFiringPlane;
    private Rectangle ground;
    private float animationSpeed = 1;

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
    
    public Player(Properties properties, float animationSpeed) {
        super(Assets.getPlayerWalkFrames(), new SpriteAnimation(Assets.getPlayerStandingStillFrames(), 0.25f), properties, animationSpeed);
        
        planeFrames = Assets.getPlaneFiringFrames();
        nonFiringPlane = Assets.getPlane();
        walkFrames = Assets.getPlayerWalkFrames();
        
        setSize(getWidth() * 2, getHeight() * 2);
        setPlaying(false);
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(isFlying) {
            
        }
        else {
        }
    }
    
    @Override
    public void onCollision(Collidable obj) {
    }

    @Override
    public void onDeath() {
    }

    @Override
    public void attack(Unit enemy) {
        enemy.takeDamage(MathUtils.random(getProperties().getMinDamage(), 
                                          getProperties().getMaxDamage()));
    }

    @Override
    public void onDamageTaken(float amount) {
        getParentLevel().getContext().getToolbar().setHealth(getProperties().getHealth());
    }

    public void walk(float amX, float amY) {
        setX(getX() + amX * getVelX());
        setY(getY() + amY * getVelY());
        
        float tamY = (float) Math.abs(amX * 1f);
        float tamX = (float) Math.abs(amY * 1.5f);
        
        // Set the animation speed based on how fast the player is walking
        animationSpeed = tamX + tamY;

        // Make sure the player is on the ground
        if(getY() >= ground.y + ground.height - (ground.height / 7)) {
            setY(ground.y + ground.height - (ground.height / 7));
        }
        else if(getY() <= ground.y) {
            setY(ground.y);
        }
        
        // Make sure the player is still on the map
        if(getX() + getWidth() > LevelProperties.WIDTH) {
            setX(LevelProperties.WIDTH - getWidth());
        }
        else if(getX() < 0) {
            setX(0);
        }
        
        // Stop the animation if the joystick isn't being touced
        if(amX == 0 && amY == 0) {
            if(isPlaying()) {
                setPlaying(false);
            }
        }
        else {
            if(!isPlaying()) {
                setPlaying(true);
            }
        }
        
        // Move based on which direction the joystick is
        if(amX < 0 && !movingLeft) {
            moveLeft();
        }
        else if(amX > 0 && movingLeft){
            moveRight();
        }
    }
    
    public void toggleFlyMode(boolean isFlying) {
        this.isFlying = isFlying;
        
        if(isFlying) {
            setAnimationFrames(planeFrames, 0.15f);
        }
        else {
            setAnimationFrames(walkFrames, 0.15f);
        }
    }
    
    public void increaseElevation() {
        rising = true;
        targetRotation = RISE_ROTATION;
        plummitTimer = 0;    
    }
    
    public void decreaseElevation() {
        rising = false;
        targetRotation = FALL_ROTATION;
    }
    
    public float getExperience() {
        return experience;
    }
    
    public float getGoalExp() {
        return goalExperience;
    }

    public static float getGoalExperience() {
        return goalExperience;
    }

    public static void setGoalExperience(float goalExperience) {
        Player.goalExperience = goalExperience;
    }
    
    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void moveLeft() {
        movingLeft = true;
        
        for(TextureRegion t : getAnimation().getAnimation().getKeyFrames()) {
            t.flip(true, false);
        }
        
        for(TextureRegion t : getRestingAnimation().getAnimation().getKeyFrames()) {
            t.flip(true, false);
        }
    }
    
    public void moveRight() {
        movingLeft = false;
        
        for(TextureRegion t : getAnimation().getAnimation().getKeyFrames()) {
            t.flip(true, false);
        }
        
        for(TextureRegion t : getRestingAnimation().getAnimation().getKeyFrames()) {
            t.flip(true, false);
        }
    }
    
    public boolean isRising() {
        return rising;
    }

    public Rectangle getGround() {
        return ground;
    }
    
    public void setRising(boolean rising) {
        this.rising = rising;
    }

    public boolean isStrafing() {
        return strafing;
    }

    public void setStrafing(boolean strafing) {
        this.strafing = strafing;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getPlummitTimer() {
        return plummitTimer;
    }

    public void setPlummitTimer(float plummitTimer) {
        this.plummitTimer = plummitTimer;
    }

    public Array<Bomb> getBombs() {
        return bombs;
    }

    public void setBombs(Array<Bomb> bombs) {
        this.bombs = bombs;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public void setGround(Rectangle rect) {
        this.ground = rect;
    }
    
    public Bomb createBomb(AtlasRegion[] explosionFrames, AtlasRegion atlasRegion, Properties bombProperties) {
        return new Bomb(explosionFrames, atlasRegion, bombProperties);
    }
}
