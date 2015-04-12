package com.dpc.vthacks.weapons;

import com.dpc.vthacks.data.Assets;

/**
 * Encapsulates data for a weapon
 * @author Daniel Christopher
 * @version 3/6/15
 *
 */
public class Weapon {
    private String name;
    private String description;
    private String iconPath;
    private Integer sound; // Key to sounds map in assets
    private boolean primary; // True = primary, false = secondary
    private float minDamage;
    private float maxDamage;
    private int ammo;
    private int maxAmmo;
    private int cost;
    private int id;
    private int currentUpgradeIndex;
    private Upgrade[] upgrades;
    
    public Weapon() {
        
    }
    
    public void refillAmmo() {
        ammo = maxAmmo;
    }
    
    /**
     * Stops this weapon's shot 
     */
    public void stopSound() {
        Assets.stopSound(sound);
    }
    
    /**
     * Plays this weapon's shot 
     */
    public void playSound(float volume) {
        Assets.playSound(sound, volume);
    }
    
    public Integer getSound() {
        return sound;
    }
    
    public void setSound(Integer sound) {
        this.sound = sound;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public float getMinDamage() {
        return minDamage;
    }

    public String getDescription() {
        return description;
    }
    
    public String getIconPath() {
        return iconPath;
    }
    
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    
    public void setMinDamage(float minDamage) {
        this.minDamage = minDamage;
    }

    public String getName() {
        return name;
    }
    
    public float getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getAmmo() {
        return ammo;
    }
    
    public void decAmmo(int dec) {
        ammo -= dec;
    }
    
    public int getMaxAmmo() {
        return maxAmmo;
    }
    
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    
    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }
    
    public Upgrade[] getUpgrades() {
        return upgrades;
    }
    
    public int getCurrentUpgradeIndex() {
        return currentUpgradeIndex;
    }
    
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    
    public boolean isPrimary() {
        return primary;
    }
}
