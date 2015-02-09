package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;

public class Tank extends Unit {
    private SpriteAnimation animation;
    public TankShell shell;
    
    public Tank(AtlasRegion[] regions, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(regions, 0.25f);
        shell = null;
        
        setSize(getWidth() * 2, getHeight() * 2);
    }

    @Override
    public void update(float delta) {
        setRegion(animation.update(delta));
        
        if(shell != null) {
            shell.update(delta);
        }

        
        if(getHealth() <= 0) {
            //Factory.tankPool.free(this);
            parentArmy.getUnits().removeValue(this, false);
        }
    }

    @Override
    public void render() {
        draw(App.batch);
        
        if(shell != null) {
            shell.render();
        }
    }

    @Override
    public void attack(Unit enemy) {
       
    }
    
    @Override
    public void takeDamage(Unit attacker) {
        setHealth(getHealth() - attacker.getDamage());
    }
    
    public void dispose() {
    }
}
