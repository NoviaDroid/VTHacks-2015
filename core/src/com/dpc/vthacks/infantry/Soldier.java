package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.animation.AnimatedUnit;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.properties.Properties;

public class Soldier extends AnimatedUnit {

    public Soldier(FrameData[] regions, 
                   TextureRegion initialFrame, 
                   Properties properties,
                   float x,
                   float y) {
        super(regions, initialFrame, properties, x, y);

        setSize(getWidth() * 3, getHeight() * 3);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void attack(Unit enemy) {
        Assets.playShot();
    }
    
    @Override
    public void onDamageTaken(float amount) {
    
    }
    
    @Override
    public void onDeath() {
    
    }

    @Override
    public void attack(Unit enemy, float dmg) {
    }
}