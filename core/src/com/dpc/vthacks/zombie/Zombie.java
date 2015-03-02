package com.dpc.vthacks.zombie;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.properties.ZombieSegment;

public class Zombie extends Unit implements Poolable {
    private Vector2 dest, targ, currentTarget; // Final destination, temporary target
    private Vector2 vel;
    private boolean isFlipped;
    
    public Zombie(AtlasRegion[] frames, Properties properties, float x, float y) {
        super(Assets.zombie, properties, x, y);
        
        getProperties().setMaxHealth(MathUtils.random(getProperties().getHealth(), 
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
               
        if(getParentLevel().getPlayer().getX() < getX()) {
            if(MathUtil.dst(getX(), 
                            getY(), 
                            getParentLevel().getPlayer().getX(), 
                            getParentLevel().getPlayer().getY()) < getParentLevel().getPlayer().getWidth()) {
                attack(getParentLevel().getPlayer());
            }
            else {
                addPos(vel.x, vel.y);
            }
        }
        else {
            if(MathUtil.dst(getX(), 
                    getY(), 
                    getParentLevel().getPlayer().getX(), 
                    getParentLevel().getPlayer().getY()) < getWidth()) {
                attack(getParentLevel().getPlayer());
            }
            else {
                addPos(vel.x, vel.y);
            }   
        }
    }

    @Override
    public void render() {
        draw(App.batch);
    }

    @Override
    public void onDeath() {
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
        
        System.out.println(((ZombieProperties) getProperties()).getMaxKillMoney());
    }

    @Override
    public void attack(Unit enemy) {
        enemy.takeDamage(MathUtils.random(getProperties().getMinDamage(),
                                          getProperties().getMaxDamage()));
    }

    @Override
    public void onDamageTaken(float amount) {
    }
    
    public Vector2 getTarg() {
        return targ;
    }
    
    public Vector2 getDest() {
        return dest;
    }
    
    public Vector2 getCurrentTarget() {
        return currentTarget;
    }
    
    public void setCurrentTarget(float x, float y) {
        currentTarget.set(x, y);
        
        vel.set(currentTarget.x - getX(), currentTarget.y - getY());
        vel.nor(); 
        
        vel.x *= getVelX(); 
        vel.y *= getVelY();
    }
    
    public void setTarg(Vector2 targ) {
        this.targ = targ;
    }
    
    public void setDest(Vector2 dest) {
        this.dest = dest;
    }

    public void resetPath() {
        setCurrentTarget(dest.x, dest.y);
    }
    
    public boolean isFlipped() {
        return isFlipped;
    }
    
    public void setFlipped(boolean b) {
        this.isFlipped = b;
    }
    
    @Override
    public void reset() {
        getProperties().setHealth(getProperties().getMaxHealth());
        
        if(isFlipped) {
            isFlipped = false;
            flip(true, false);
        }
        
        init();
    }
    
    public void init() {
        dest = new Vector2(0, 50);
        currentTarget = new Vector2(dest);
        targ = new Vector2();
        vel = new Vector2();
        
        setCurrentTarget(0, 50);
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }
}
