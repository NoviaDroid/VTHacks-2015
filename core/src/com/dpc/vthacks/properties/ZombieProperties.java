package com.dpc.vthacks.properties;

import com.badlogic.gdx.math.Vector2;

public class ZombieProperties extends Properties {
    private int minKillMoney, maxKillMoney; // How much money played awarded when killed
    
    public ZombieProperties(ZombieProperties cpy) {
        super(cpy);
        
        minKillMoney = cpy.getMinKillMoney();
        maxKillMoney = cpy.getMaxKillMoney();
    }
    
    public ZombieProperties() {
        super();
    }
    
    public ZombieProperties(Vector2 pos, Vector2 vel, Vector2 minVel, Vector2 minKillExp, Vector2 maxKillExp, Vector2 maxVel, float range, float minDamage, float maxDamage, float health, int cost) {
        super(pos, vel, minVel, maxVel, range, minDamage, maxDamage, health, cost);
    }

    public int getMinKillMoney() {
        return minKillMoney;
    }

    public void setMinKillMoney(int minKillMoney) {
        this.minKillMoney = minKillMoney;
    }

    public int getMaxKillMoney() {
        return maxKillMoney;
    }

    public void setMaxKillMoney(int maxKillMoney) {
        this.maxKillMoney = maxKillMoney;
    }
}
