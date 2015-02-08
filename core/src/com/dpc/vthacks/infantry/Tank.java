package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.factories.Factory;

public class Tank extends Unit {
    private SpriteAnimation animation;
    private TankShell shell;
    
    public Tank(AtlasRegion[] regions, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(regions, 0.25f);
       
        setSize(getWidth() * 2, getHeight() * 2);
    }

    @Override
    public void update(float delta) {
        setRegion(animation.update(delta));
        
        if(shell != null) {
            shell.update(delta);
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
        shell = Factory.tankShellPool.obtain();
        shell.setRotation(MathUtils.atan2(enemy.getY() - getY(), enemy.getX() - getX()));
        shell.setTargetX(enemy.getX());
        shell.setTargetY(enemy.getY());
    }

    @Override
    public void move() {
        addVel();
    }

    @Override
    public void takeDamage(Unit attacker) {
        
    }
    
    public void dispose() {
        shell.dispose();
    }
}
