package com.dpc.vthacks.soldier;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;

public class Tank extends Unit {
    private static final float DAMAGE = 70;
    private static final float HEALTH = 100;
    private static final float VEL_X = 8, VEL_Y = 0;
    
    public Tank(TextureRegion region, int cost, float x, float y) {
        super(region, cost, DAMAGE, HEALTH, VEL_X, VEL_Y, x, y);
    }

    @Override
    public void update(float delta) {
        
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
