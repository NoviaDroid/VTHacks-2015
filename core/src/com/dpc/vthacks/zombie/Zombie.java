package com.dpc.vthacks.zombie;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.AnimatedUnit;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.level.LevelManager;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.ZombieProperties;

public class Zombie extends AnimatedUnit implements Poolable {
    public static final int TIER_1 = 1;
    public static final int TIER_2 = 2;
    public static final int TIER_3 = 3;
    private int tier;
    private float attackSpeed; // Every x amount of time, attack
    private float attackTimer; // Counts time for attack speed
    
    public Zombie(String currentState, 
                  AnimatedUnitProperties<SpriteAnimation> properties, 
                  float x, float y) {
        super(currentState,
              properties, x, y);
       
        attackSpeed = ((ZombieProperties) properties).getAttackSpeed();
        
        attackTimer = attackSpeed;
        
        getProperties().maxHealth(MathUtils.random(getProperties().getHealth(), 
                                                   getProperties().getHealth() * 2) + 25);
        
        init();
    }

    @Override
    public void addPos(float x, float y) {
        super.addPos(x, y);
        
        // Update every segment
        for(ZombieSegment seg : ((ZombieProperties) getProperties()).getSegments()) {
            seg.bounds.x = getBoundingRectangle().x + seg.offsetX;
            seg.bounds.y = getBoundingRectangle().y + seg.offsetY;
        }
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
    
        if(isAttacking()) {
            attackTimer += delta;
            
            if(attackTimer >= attackSpeed) {
                attackTimer = 0;
                
                attack(getTargetEnemy(), App.rand(getProperties().getMinDamage(), 
                                                       getProperties().getMaxDamage()));
            }
        }
        
        // Set a random velocity
        getVelSCL().set(App.rand(getProperties().getMinVel().x,
                                      getProperties().getMaxVel().x),
                                      App.rand(getProperties().getMaxVel().y,
                                      getProperties().getMaxVel().y));
        
        float px = LevelManager.getPlayer().getX();
        float py = LevelManager.getPlayer().getY();
        float x = getX();
        float y = getY();
        
        // If in range of the player, attack
        if(inRange(LevelManager.getPlayer())) {
            
            float range = 0;
            
            if(x < px) {
                range = getWidth() * 0.65f;
            }
            else if(x > px) {
                range = LevelManager.getPlayer().getWidth() * 0.65f;
            }
            
            if(App.dst(x, y, px, py) <= range &&
               App.dst(0, getY(), 0, py) < LevelManager.getPlayer().getHeight()) {
                
                //if(!isAttacking()) {
                    setAttacking(true, LevelManager.getPlayer());
                //}
                
                // Slow the player
                LevelManager.getPlayer().setSlowed(true);
            }
            else {
                if(isAttacking()) {
                    setAttacking(false, null);
                }
            }
            
            // Set target based on where currently is
            if (getVelX() < 0) {
                setCurrentTarget(px
                        - getWidth(), py);
            } else {
                setCurrentTarget(px,
                                 py);
            }
            
        }
        else {
            // If there is no target near, reset the path
         //   resetPath();
            if (getVelX() < 0) {
                setCurrentTarget(px
                        - getWidth(), py);
            } else {
                setCurrentTarget(px,
                                 py);
            }
        }

        // Continue to move if not attacking
        if(!isAttacking()) {
            addPos(getVelX(), getVelY());
        }
    }

    @Override
    public void render() {
        draw(App.batch);
    }

    @Override
    public void setCurrentTarget(float x, float y) {
        super.setCurrentTarget(x, y);
        
        // Assign the right animation
        if(getVelX() < 0) {
            // Right
            setState("walking-left");
        }
        else if(getVelX() != 0){
            // Left
            setState("walking-right");
        }
    }
    
    @Override
    public void onDeath(Unit killer) {
        Factory.zombiePool.free(this);
        LevelManager.getCurrentLevel().remove(this);
        
        // Add money to the money total
        LevelManager.getCurrentLevel()
                .getContext()
                .getToolbar()
                .addMoney((int) MathUtils.random(((ZombieProperties) getProperties())
                                                    .getMinKillMoney(),
                                                ((ZombieProperties) getProperties())
                                                    .getMaxKillMoney()));
        LevelManager.getCurrentLevel().onZombieKilled();
    }

    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        if(getProperties().getHealth() <= 0) {
            onDeath(attacker);
        }
    }

    @Override
    public void reset() {
        super.reset();

        init();
    }
    
    @Override
    public void init() {
        super.init();

        setCurrentTarget(0, 50);
        setAttacking(false, null);
        setPlaying(true);
    }

    @Override
    public void attack(Unit enemy, float dmg) {
        enemy.takeDamage(this, dmg);
    }
    
    public int getTier() {
        return tier;
    }
    
    public void setTier(int tier) {
        this.tier = tier;
    }
}
