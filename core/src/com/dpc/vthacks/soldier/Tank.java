package com.dpc.vthacks.soldier;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;

public class Tank extends Unit {
    private static final float DAMAGE = 70;
    private static final float HEALTH = 100;
    private static final float VEL_X = 8, VEL_Y = 0;
    private SpriteAnimation animation;
    
    public Tank(TextureRegion region, int cost, float x, float y) {
        super(region, cost, DAMAGE, HEALTH, VEL_X, VEL_Y, x, y);
        
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
