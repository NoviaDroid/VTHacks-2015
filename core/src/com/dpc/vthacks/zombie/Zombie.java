package com.dpc.vthacks.zombie;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.animation.AnimatedUnit;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.properties.ZombieSegment;

public class Zombie extends AnimatedUnit implements Poolable {
    private boolean isFlipped;
    private boolean walkingLeft;
    
    public Zombie(AtlasRegion[] frames, Properties properties, float x, float y) {
        super(Assets.zombieAnimations.get("walking-right"), Assets.zombieAnimations.get("walking-right"), properties, x, y);
       
        getProperties().setMaxHealth(MathUtils.random(getProperties().getHealth(), 
                                                      getProperties().getHealth() * 2) + 25);
        
        setSize(getWidth() * 3, getHeight() * 3);
        
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
    
        // Set a random velocity
        getVelSCL().set(MathUtil.rand(getProperties().getMinVel().x,
                                      getProperties().getMaxVel().x),
                        MathUtil.rand(getProperties().getMaxVel().y,
                                      getProperties().getMaxVel().y));
        
        // If in range of the player, attack
        if(inRange(getParentLevel().getPlayer())) {
            if(getBoundingRectangle().overlaps(getParentLevel().getPlayer().getBoundingRectangle())) {
                if(!isAttacking()) {
                    setAttacking(true, getParentLevel().getPlayer());
                    setAnimation(Assets.zombieAnimations.get("attacking"));
                }
            }
            else {
                if(isAttacking()) {
                    setAttacking(false, null);
                }
            }
            
            // Set target based on where currently is
            if (getVelX() < 0) {
                setCurrentTarget(getParentLevel().getPlayer().getX()
                        - getWidth(), getParentLevel().getPlayer().getY());
            } else {
                setCurrentTarget(getParentLevel().getPlayer().getX(),
                        getParentLevel().getPlayer().getY());
            }
            
        }
        else {
            // If there is no target near, reset the path
            resetPath();
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
            setAnimation(Assets.zombieAnimations.get("walking-left"));
        }
        else if(getVelX() != 0){
            // Left
            setAnimation(Assets.zombieAnimations.get("walking-right"));
        }
    }
    
    @Override
    public void onDeath(Unit killer) {
        Factory.zombiePool.free(this);
        getParentLevel().getZombies().removeValue(this, false);
        
        // Add money to the money total
        getParentLevel()
                .getContext()
                .getToolbar()
                .addMoney((int) MathUtils.random(((ZombieProperties) getProperties())
                                                    .getMinKillMoney(),
                                                ((ZombieProperties) getProperties())
                                                    .getMaxKillMoney()));
    }

    @Override
    public void attack() {
        float rand = MathUtil.rand(getProperties().getMinDamage(), 
                                   getProperties().getMaxDamage());
        
        getTargetEnemy().takeDamage(this, rand);
    }

    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        super.onDamageTaken(attacker, amount);
    }

    public void resetPath() {
        setCurrentTarget(getFinalDestination().x, getFinalDestination().y);
    }
    
    public boolean isFlipped() {
        return isFlipped;
    }
    
    public void setFlipped(boolean b) {
      //  this.isFlipped = b;
    }
    
    @Override
    public void reset() {
        super.reset();
        //getProperties().setHealth(getProperties().getMaxHealth());
        
        if(isFlipped) {
            isFlipped = false;
            flip(true, false);
        }
        
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
    }
}
