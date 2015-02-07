package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;

public class Tank extends Unit {
    private SpriteAnimation animation;
    
    public Tank(float damage, float health, float velX, float velY, float x, float y) {
        super(Assets.tankFrames[0], 0, damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(Assets.tankFrames, 0.25f);
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
        
    }

    @Override
    public void takeDamage(Unit attacker) {
        
    }
}
