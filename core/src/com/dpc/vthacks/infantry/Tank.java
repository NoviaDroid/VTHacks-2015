package com.dpc.vthacks.infantry;

import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.properties.AnimatedUnitProperties;

public class Tank extends Parachutist {
    
    public Tank(String currentState,
                AnimatedUnitProperties<SpriteAnimation> properties,
                float x,
                float y) {
        super(currentState, properties, x, y);

        setSize(getWidth() * 2, getHeight() * 2);
        setPlaying(true);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render() {
        draw(App.batch);
    }

    @Override
    public void reset() {
        super.reset();
    }
    
    @Override
    public void attack() {
       Assets.explosion.play();
    }

    @Override
    public void onDeath(Unit killer) {
        Factory.tankPool.free(this);
       
        getParentLevel().getPlayerArmy().removeValue(this, false);
        
        killer.setAttacking(false, null);
    }

    @Override
    public void onDamageTaken(Unit attacker, float amount) {
        super.onDamageTaken(attacker, amount);
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }
}
