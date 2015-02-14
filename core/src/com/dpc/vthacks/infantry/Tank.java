package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.SpriteAnimation;
import com.dpc.vthacks.screens.GameScreen;

public class Tank extends Unit {
    private SpriteAnimation animation;
    private TankShell shell;
    private static int killExp, killMoney;
    private int cost;
    
    public Tank(AtlasRegion[] regions, float range, float damage, float health, float maxHealth, float velX, float velY, float x, float y) {
        super(regions[0], range, damage, health, maxHealth, velX, velY, x, y);
        
        animation = new SpriteAnimation(regions, 0.25f);
        setShell(null);
        
        setSize(getWidth() * 2, getHeight() * 2);
    }

    @Override
    public void update(float delta) {
        setRegion(animation.update(delta));
        
        if(getShell() != null) {
            getShell().update(delta);
        }

        
        if(getHealth() <= 0) {
            //Factory.tankPool.free(this);
            
            // Reward the player with the kill
            if(getParentArmy().equals(GameScreen.battle.getEnemyArmy())) {
                GameScreen.battle.getPlayer().addExperience(killExp);
                GameScreen.battle.getPlayer().addMoney(killMoney);
            }
            
            getParentArmy().getUnits().removeValue(this, false);
        }
    }

    @Override
    public void render() {
        draw(App.batch);
        
        if(getShell() != null) {
            getShell().render();
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

    public TankShell getShell() {
        return shell;
    }

    public void setShell(TankShell shell) {
        this.shell = shell;
    }
    
    public static int getKillExp() {
        return killExp;
    }

    public static void setKillExp(int killExp) {
        Tank.killExp = killExp;
    }

    public static int getKillMoney() {
        return killMoney;
    }
    
    public static void setKillMoney(int killMoney) {
        Tank.killMoney = killMoney;
    }
    
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
}
