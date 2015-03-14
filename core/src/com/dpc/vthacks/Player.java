package com.dpc.vthacks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.animation.AdvancedAnimatedUnit;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.objects.Weapon;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.zombie.Zombie;

public class Player extends AdvancedAnimatedUnit {
    private int money;
    private boolean shotDelayed, drawBehind;
    private final float FIRE_DELAY = 0.15f; // Delay between shots
    private float fireTimer; // Current time between shot
    private boolean movingLeft;
    private Rectangle ground;
    private Vector2 gunOffset; // X and Y positions of tip of the gun relative to and inside of the bounding box
    private Weapon primary, secondary;
    private Weapon currentWeapon;
   
    public Player(String currentState,
                  AnimatedUnitProperties<AdvancedSpriteAnimation> properties, 
                  float x, float y, float animationSpeed) {
        super(currentState,
              properties,
              x, 
              y);
        
        setSize(getWidth() * 2, getHeight() * 2);
        
        setPlaying(true);
        
        currentWeapon = primary;
        
        shotDelayed = false;
    }
    
    @Override
    public void render() {      
        super.render();
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(shotDelayed) {
            fireTimer += delta;
            
            if(fireTimer >= FIRE_DELAY) {
                fireTimer = 0;
                shotDelayed = false;
            }
        }
    }

    @Override
    public void onDeath(Unit killer) {
        getParentLevel().onGameOver();
    }

    @Override
    public void attack(Unit enemy, float dmg) {
        if(currentWeapon.getAmmo() > 0) {
            enemy.takeDamage(this, dmg * MathUtils.random(currentWeapon.getMinDamage(), currentWeapon.getMaxDamage()));
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
    public void blindFire() {
        if(!shotDelayed) {
            if(currentWeapon.getAmmo() > 0) {
                int dex = MathUtils.random(0, Assets.playerSounds.length - 1);
                Assets.playerSounds[dex].stop();
                Assets.playerSounds[dex].play();
    
                // Decrease ammo
                currentWeapon.decAmmo(1);
                
                // Update the ammo label
                getParentLevel().getContext().getToolbar().setAmmo(currentWeapon.getAmmo());
                
                if(movingLeft) {
                    setX(getX() + 1);
                    setY(getY() + 1);    
                }
                else {
                    setX(getX() - 1);
                    setY(getY() + 1);
                }
                
                
            }
            else {
                Assets.outOfAmmo.stop();
                Assets.outOfAmmo.play();
                getParentLevel().getContext().getToolbar().shakeAmmo();
            }
        }
        
        shotDelayed = true;
    }
    
    @Override
    public void attack() {
        
    }
    
    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        super.onDamageTaken(attacker, amount);
        getParentLevel().getContext().getToolbar().setHealth(getProperties().getHealth());
        System.err.println("my health: " + getProperties().getHealth());
        
        if(getProperties().getHealth() <= 0) {
             onDeath(attacker);
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
        if(amX + amY == 0) {
            setCurrentState("idle");
        }
        else {
            setCurrentState("running");
        }
        
        for (Zombie z : getParentLevel().getZombies()) {
            if (getY() > z.getY()) {
                drawBehind = true;
            } else {
                drawBehind = false;
            }

            break;
        }
        
        setX(getX() + amX * getVelocityScalarX());
        setY(getY() + amY * getVelocityScalarY());
        
        float tamY = (float) Math.abs(amX * 1f);
        float tamX = (float) Math.abs(amY * 1.5f);

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
        if(amX < 0 && !movingLeft) {
            moveLeft();
        }
        else if(amX > 0 && movingLeft){
            moveRight();
        }
    }
  
    public boolean isMovingLeft() {
        return movingLeft;
    }
    
    public void moveLeft() {
        movingLeft = true;
        
        // Flip every animation
        flipAll(true, false);
    }
    
    public void moveRight() {
        movingLeft = false;
        
        // Flip every animation
        flipAll(true, false);
    }
    
    public Rectangle getGround() {
        return ground;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addAmmo(int am) {
        currentWeapon.setAmmo(currentWeapon.getAmmo() + am);
    }
    
    public void refillAmmo() {
        currentWeapon.setAmmo(currentWeapon.getMaxAmmo());
        getParentLevel().getContext().getToolbar().setAmmo(currentWeapon.getAmmo());
    }
    
    public void setGround(Rectangle rect) {
        this.ground = rect;
    }
    
    public Vector2 getGunOffset() {
        return gunOffset;
    }
    
    public float getGunOffsetX() {
        return gunOffset.x;
    }
    
    public float getGunOffsetY() {
        return gunOffset.y;
    }
    
    public void setGunOffset(Vector2 gunOffset) {
        this.gunOffset = gunOffset;
    }
    
    public void setGunOffsetX(float x) {
        gunOffset.x = x;
    }
    
    public void setGunOffsetY(float y) {
        gunOffset.y = y;
    }
    
    public float getGunX() {
        return gunOffset.x + getX();
    }
    
    public float getGunY() {
        return gunOffset.y + getY();
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
    
    public Weapon getSecondary() {
        return secondary;
    }
}
