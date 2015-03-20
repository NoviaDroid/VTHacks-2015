package com.dpc.vthacks.weapons;

public class Gun extends Weapon {
    private float shotDelay;
    private float shotTimer;
    private boolean canFire = true, fullAuto;
    
    public Gun() {
    
    }
    
    public void update(float delta) {
        if(!canFire) {
            shotTimer += delta;
            
            if(shotTimer >= shotDelay) {
                canFire = true;
            }
        }
    }
    
    public void fire() {
        canFire = false;
        shotTimer = 0;
    }
    
    public void setShotTimer(float shotTimer) {
        this.shotTimer = shotTimer;
    }
    
    public float getShotTimer() {
        return shotTimer;
    }
    
    public void setFullAuto(boolean fullAuto) {
        this.fullAuto = fullAuto;
    }
    
    public boolean isFullAuto() {
        return fullAuto;
    }
    
    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }
    
    public boolean canFire() {
        return canFire;
    }
    
    public float getShotDelay() {
        return shotDelay;
    }
    
    public void setShotDelay(float shotDelay) {
        this.shotDelay = shotDelay;
    }
}
