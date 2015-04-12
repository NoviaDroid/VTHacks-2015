package com.dpc.vthacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.dpc.vthacks.animation.AdvancedAnimatedUnit;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.level.LevelManager;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.weapons.Gun;
import com.dpc.vthacks.weapons.Weapon;
import com.dpc.vthacks.zombie.Zombie;

public class Player extends AdvancedAnimatedUnit {
    public static final String IDLE_RIGHT = "idleRight";
    public static final String IDLE_LEFT = "idleLeft";
    public static final String RUN_RIGHT = "runRight";
    public static final String RUN_LEFT = "runLeft";
    
    private boolean drawBehind, movingLeft, slowed;
    private boolean deathCallbackCalled; // True when the player's onGameOver method is called
    private Rectangle ground;
    private Weapon primary, secondary, currentWeapon;
    private Texture q;
    
    public Player(String currentState,
                  AnimatedUnitProperties<AdvancedSpriteAnimation> properties, 
                  float x, float y, float animationSpeed) {
        
        super(currentState,
              properties,
              x, 
              y);

        setPlaying(true);
        
        currentWeapon = primary;
    }
    
    @Override
    public void onEvent(GameEvent e) {
        int ev = e.getEvent();
        
        switch(ev) {
        case EventSystem.ENTITY_ATTACK:
            break;
        case EventSystem.TOUCH_DOWN:
            break;
        }
    }
    
    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);

        if(currentWeapon instanceof Gun) {
            ((Gun) currentWeapon).update(delta);
        }
    }

    @Override
    public void onDeath(Unit killer) {
        EventSystem.dispatch(new GameEvent(EventSystem.GAME_OVER));
    }

    @Override
    public void attack(Unit enemy, float dmg) {
        if(currentWeapon.getAmmo() > 0) {
            enemy.takeDamage(this, 
                             dmg * MathUtils.random(currentWeapon.getMinDamage(), 
                                                    currentWeapon.getMaxDamage()));
        }
    }

    public void swapWeapon() {
        // Change weapons
        if(currentWeapon.equals(primary)) {
            currentWeapon = secondary;
        }
        else {
            currentWeapon = primary;
        }
    }
    
    /**
     * Fire the gun even though it may not hit something
     */
    public void fireWeapon(Zombie z, float damageScale) {
        if(((Gun) currentWeapon).canFire()) {
            if(currentWeapon.getAmmo() > 0) {
                
                currentWeapon.stopSound();
                currentWeapon.playSound(0.5f);
    
                // Decrease ammo
                currentWeapon.decAmmo(1);

                EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_AMMO_CHANGED, currentWeapon.getAmmo()));

                if(z != null) {
                    attack(z, damageScale);
                }
                
                ((Gun) currentWeapon).setCanFire(false);
                ((Gun) currentWeapon).fire();
            }
            else {
                Assets.stopSound(Assets.OUT_OF_AMMO);
                Assets.playSound(Assets.OUT_OF_AMMO);
                
                EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_AMMO_OUT));
            }
        }
    }
    
    @Override
    public void reset() {
        super.reset();

        setPlaying(true);
        deathCallbackCalled = false;
        drawBehind = false;
        primary.refillAmmo();
        secondary.refillAmmo();
        
        EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_AMMO_CHANGED, primary.getAmmo()));
        EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_HEALTH_CHANGED, getProperties().getHealth()));
        EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_MONEY_CHANGED, 0));
        
        centerInViewport();
    }

    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_HEALTH_CHANGED, getProperties().getHealth()));
        
        if(getProperties().getHealth() <= 0 && !deathCallbackCalled) {
             onDeath(attacker);
             deathCallbackCalled = true;
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }
    
    @Override
    public void setY(float y) {
        super.setY(y);
    }
    
    public void walk(float amX, float amY) {
        if(amX == 0 && amY == 0) {
            if(getCurrentState().equals(RUN_RIGHT)) {
                setCurrentState(IDLE_RIGHT);
            }
            else if(getCurrentState().equals(RUN_LEFT)) {
                setCurrentState(IDLE_LEFT);
            }
        }

        for (Zombie z : LevelManager.getCurrentLevel().getZombies()) {
            if (getY() > z.getY()) {
                drawBehind = true;
            } else {
                drawBehind = false;
            }

            break;
        }
        
        if(slowed) {
            setX(getX() + amX * (getVelocityScalarX() * 0.65f));
            setY(getY() + amY * (getVelocityScalarY() * 0.65f));
        }
        else {
            setX(getX() + amX * getVelocityScalarX());
            setY(getY() + amY * getVelocityScalarY());
        }
        
//        float tamY = (float) Math.abs(amX * 1f);
//        float tamX = (float) Math.abs(amY * 1.5f);

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
        
        // Move based on which direction the joystick is
        if(amX < 0) {
            moveLeft();
        }
        else if(amX > 0){
            moveRight();
        }
    }
  
    public boolean isMovingLeft() {
        return movingLeft;
    }
    
    public void moveLeft() {
        movingLeft = true;
        
        setCurrentState(RUN_LEFT);
    }
    
    public void moveRight() {
        movingLeft = false;
        
        setCurrentState(RUN_RIGHT);
    }
    
    public Rectangle getGround() {
        return ground;
    }

    public void refillAmmo() {
        primary.setAmmo(primary.getMaxAmmo());
        secondary.setAmmo(secondary.getMaxAmmo());
        
        EventSystem.dispatch(new GameEvent(EventSystem.PLAYER_AMMO_CHANGED, primary.getAmmo()));
    }
    
    public void setGround(Rectangle rect) {
        this.ground = rect;
    }

    public void setAmmo(int ammo) {
        currentWeapon.setAmmo(ammo);
    }
    
    public void setPrimary(Weapon primary) {
        this.primary = primary;
    }
    
    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }
    
    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
    
    public void setSecondary(Weapon secondary) {
        this.secondary = secondary;
    }
    
    public void setDrawBehind(boolean drawBehind) {
        this.drawBehind = drawBehind;
    }
    
    public boolean isDrawingBehind() {
        return drawBehind;
    }
    
    public Weapon getPrimary() {
        return primary;
    }
    
    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }
    
    public Weapon getSecondary() {
        return secondary;
    }

    public void centerInViewport() {
        setPosition((AppData.width * 0.5f) - (getWidth() * 0.5f), 
                    (getGround().getY() + (getGround().getHeight() * 0.5f)));
    }
}
