package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Sounds;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.screens.GameScreen;

public class Soldier extends Unit {
    private SpriteAnimation animation;
    private Array<Bullet> bullets;
    
    public Soldier(AtlasRegion[] regions, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, velX, velY, x, y);
        
        animation = new SpriteAnimation(regions, 0.1f);
        bullets = new Array<Bullet>();
        
        setSize(getWidth() * 3, getHeight() * 3);
    }

    @Override
    public void update(float delta) {
        setRegion(animation.update(delta));

        if(getHealth() <= 0) {
            getParentArmy().getUnits().removeValue(this, false);
        }
        
        for(Bullet b : bullets) {
            b.update(delta);
        }
    }

    @Override
    public void render() {
        draw(App.batch);
        
//        for(Bullet b : bullets) {
//            b.render();
//        }
    }

    @Override
    public void attack(Unit enemy) {
        Sounds.playShot();
             
        Bullet bullet = Factory.bulletPool.obtain();
        bullet.setX(getX() + getWidth() * 0.5f);
        bullet.setY(getY() + getHeight() * 0.5f);

        if(getParentArmy().equals(GameScreen.battle.getEnemyArmy())) {
            bullet.setVel(-20, 0);
        }
        else if(getParentArmy().equals(GameScreen.battle.getMyArmy())) {
            bullet.setVel(20, 0);
        }
        
        
        bullets.add(bullet);
        
    }

    @Override
    public void takeDamage(Unit attacker) {
        setHealth(getHealth() - attacker.getDamage());
    }  

    public Array<Bullet> getBullets() {
        return bullets;
    }
}