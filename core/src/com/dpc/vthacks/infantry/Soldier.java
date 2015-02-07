package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;

public class Soldier extends Unit {
    private static final float DAMAGE = 30;
    private static final float VEL_X = 11, VEL_Y = 0, HEALTH = 100;
    
    public Soldier(TextureRegion region, int cost, float x, float y) {
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
    public void takeDamage(Unit attacker) {
        
    }
    
    @Override
    public void move() {
        
    }

}
