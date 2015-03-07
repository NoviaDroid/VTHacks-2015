package com.dpc.vthacks.objects;

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
    private float minDamage;
    private float maxDamage;
    private int ammo;
    private int maxAmmo;
    
    public Weapon() {
        
    }
    
    @Override
    public String toString() {
        return "name: " + name + "\ndescription: " + 
               description + "\nminDamage" + 
                minDamage + "\nammo" + 
               ammo + "\nmaxAmmo" + maxAmmo;
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
}
