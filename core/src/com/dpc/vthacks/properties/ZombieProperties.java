package com.dpc.vthacks.properties;

import com.badlogic.gdx.math.Vector2;

public class ZombieProperties extends Properties {
    private int minKillMoney, maxKillMoney; // How much money played awarded when killed
    private ZombieSegment[] segments;
    
    public ZombieProperties(ZombieProperties cpy) {
        super(cpy);
        
        segments = new ZombieSegment[cpy.getSegments().length];
        
        for(int i = 0; i < cpy.getSegments().length; i++) {
            segments[i] = new ZombieSegment(cpy.getSegments()[i]);
        }
        
        minKillMoney = cpy.getMinKillMoney();
        maxKillMoney = cpy.getMaxKillMoney();
    }
    
    public ZombieProperties() {
        super();
    }
    
    public ZombieProperties(Vector2 vel, Vector2 minVel, Vector2 minKillExp, Vector2 maxKillExp, Vector2 maxVel, float range, float minDamage, float maxDamage, float health, int cost) {
        super(vel, minVel, maxVel, range, minDamage, maxDamage, health, cost);
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
    
    public ZombieSegment[] getSegments() {
        return segments;
    }
    
    public void setSegments(ZombieSegment[] segments) {
        this.segments = segments;
    }
}
