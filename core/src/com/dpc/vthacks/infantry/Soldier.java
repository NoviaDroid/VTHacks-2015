package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.screens.GameScreen;

public class Soldier extends Unit {
    private SpriteAnimation animation;
    private Array<Bullet> bullets;
    private static int killExp, killMoney;
    
    public Soldier(AtlasRegion[] regions, float range, float damage, float health, float maxHealth, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, maxHealth, velX, velY, x, y);
        
        animation = new SpriteAnimation(regions, 0.1f);
        bullets = new Array<Bullet>();
        
        setSize(getWidth() * 3, getHeight() * 3);
    }

    @Override
    public void update(float delta) {
        setRegion(animation.update(delta));

        if(getHealth() <= 0) {
            // Reward the player with the kill
            if(getParentArmy().equals(GameScreen.battle.getEnemyArmy())) {
                GameScreen.battle.getPlayer().addExperience(killExp);
                GameScreen.battle.getPlayer().addMoney(killMoney);
            }
            
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
        Assets.playShot();
             
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
    
    public static int getKillExp() {
        return killExp;
    }

    public static int getKillMoney() {
        return killMoney;
    }
    
    public static void setKillMoney(int killMoney) {
        Soldier.killMoney = killMoney;
    }
    
    public static void setKillExp(int killExp) {
        Soldier.killExp = killExp;
    }
    
    

}