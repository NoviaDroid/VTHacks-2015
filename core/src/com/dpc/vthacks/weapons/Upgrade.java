package com.dpc.vthacks.weapons;

public class Upgrade {
    public float cost;
    public float ammo;
    public float dmg;
    
    public Upgrade() {
    
    }
    
    public Upgrade(float cost, float ammo, float dmg) {
        this.cost = cost;
        this.ammo = ammo;
        this.dmg = dmg;
    }
    
    public void setCost(float cost) {
        this.cost = cost;
    }
    
    public void setAmmo(float ammo) {
        this.ammo = ammo;
    }
    
    public void setDmg(float dmg) {
        this.dmg = dmg;
    }
    
    public float getDmg() {
        return dmg;
    }
    
    public float getAmmo() {
        return ammo;
    }
    
    public float getCost() {
        return cost;
    }
}