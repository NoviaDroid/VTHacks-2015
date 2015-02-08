package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;

public class Soldier extends Unit {
    private SpriteAnimation animation;
    
    public Soldier(AtlasRegion[] regions, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(Assets.soldierFrames, 0.1f);
        setSize(getWidth() * 2, getHeight() * 2);
    }

    @Override
    public void update(float delta) {
        setRegion(animation.update(delta));
    }

    @Override
    public void render() {
        draw(App.batch);
    }

    @Override
    public void attack(Unit enemy) {
        
    }

    @Override
    public void takeDamage(Unit attacker) {
        
    }
    
    @Override
    public void move() {
        
    }

}
