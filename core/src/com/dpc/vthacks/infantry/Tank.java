package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.AnimatedUnit;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.properties.Properties;

public class Tank extends AnimatedUnit {
    
    public Tank(FrameData[] regions, 
                TextureRegion initialFrame, 
                Properties properties,
                float x,
                float y) {
        super(regions, initialFrame, properties, x, y);

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
    public void attack(Unit enemy) {
       
    }

    @Override
    public void onDeath() {
        //Factory.tankPool.free(this);
        
        // Reward the player with the kill
//        if(getParentArmy().equals(GameScreen.battle.getEnemyArmy())) {
//            GameScreen.battle.getPlayer().addExperience(killExp);
//            GameScreen.battle.getPlayer().addMoney(killMoney);
//        }
//        
//        getParentArmy().getUnits().removeValue(this, false);
    }

    @Override
    public void onDamageTaken(float amount) {
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }
}
