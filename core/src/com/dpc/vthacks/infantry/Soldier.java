package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.properties.Properties;

public class Soldier extends Parachutist {
    
    public Soldier(TextureRegion[] regions, 
                   TextureRegion initialFrame, 
                   Properties properties,
                   float x,
                   float y) {
        super(new SpriteAnimation(regions, properties.getFrameTime()), initialFrame, properties, x, y);

        setSize(getWidth() * 2, getHeight() * 2);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(!isFalling()) {
            addPos(getVelocityScalarX(), getVelocityScalarY());
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void reset() {
        super.reset();
    }
    
    @Override
    public void attack() {
        Assets.playShot();
    }
    
    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        super.onDamageTaken(attacker, amount);
    //    System.err.println(getProperties().getHealth());
    }
    
    @Override
    public void onDeath(Unit killer) {
        Factory.soldierPool.free(this);
        getParentLevel().getPlayerArmy().removeValue(this, false);
        
        killer.setAttacking(false, null);
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }
}