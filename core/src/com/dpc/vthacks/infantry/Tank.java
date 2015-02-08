package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;

public class Tank extends Unit {
    private SpriteAnimation animation;
    
    public Tank(AtlasRegion[] regions, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(regions, 0.25f);
       
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
    public void move() {
        addVel();
    }

    @Override
    public void takeDamage(Unit attacker) {
        
    }
}
