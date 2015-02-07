package com.dpc.vthacks.infantry;

import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;

public class Soldier extends Unit {
    private SpriteAnimation animation;
    
    public Soldier(float damage, float health, float velX, float velY, float x, float y) {
        super(Assets.soldierFrames[0], damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(Assets.soldierFrames, 0.1f);
        setSize(getWidth() * 4, getHeight() * 4);
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
