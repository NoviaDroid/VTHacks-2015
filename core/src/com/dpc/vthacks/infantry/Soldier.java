package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.Collidable;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.properties.Properties;

public class Soldier extends AnimatedUnit {

    public Soldier(AtlasRegion[] regions, TextureRegion initialFrame, Properties properties) {
        super(regions, initialFrame, properties, 0.1f);

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
    public void onCollision(Collidable obj) {
    
    }
    
    @Override
    public void onDamageTaken(float amount) {
    
    }
    
    @Override
    public void onDeath() {
    
    }
}