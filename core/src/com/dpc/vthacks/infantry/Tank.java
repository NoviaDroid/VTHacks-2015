package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.properties.Properties;

public class Tank extends AnimatedUnit {
    
    public Tank(AtlasRegion[] regions, TextureRegion initialFrame, Properties properties) {
        super(regions, initialFrame, properties, 0.25f);

        setSize(getWidth() * 2, getHeight() * 2);
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
    public void onCollision(Collidable obj) {
    
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
}
