package com.dpc.vthacks.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public final class WeaponManager {
    public static final int NUMBER_OF_WEAPONS = 5;
    private static final String UPGRADE_KEY = "currentUpgrade";
    private static final String PREFS_PATH = "weaponPreferencessS";
    private static Preferences prefs;
    private static Array<Weapon> weapons;
    private static Array<Weapon> secondaryWeapons;
    private static Array<Weapon> primaryWeapons;
    private static Array<Weapon> unlockedPrimaryWeapons;
    private static Array<Weapon> unlockedSecondaryWeapons;
    
    public static void load() {
        primaryWeapons = new Array<Weapon>(NUMBER_OF_WEAPONS);
        secondaryWeapons = new Array<Weapon>(NUMBER_OF_WEAPONS);
        weapons = new Array<Weapon>(NUMBER_OF_WEAPONS);
       
        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);

        // Load every weapon in from the json file
        for (Object obj : json.fromJson(Array.class, Gun.class, Gdx.files.internal("json/weapons.json"))) {
            
            System.out.println(((Weapon) obj).isPrimary());
            
            if(((Weapon) obj).isPrimary()) {
                primaryWeapons.add((Weapon) obj);
            }
            else {
                secondaryWeapons.add((Weapon) obj);
            }
            
            weapons.add((Weapon) obj);
        }
        
        prefs = Gdx.app.getPreferences(PREFS_PATH);

         //prefs.clear();
     //    prefs.flush();
         
        // Handgun is always unlocked
        prefs.putInteger("unlocked1", 1);
        prefs.putInteger("unlocked2", 2);
        prefs.flush();
      
        unlockedPrimaryWeapons = new Array<Weapon>();
        unlockedSecondaryWeapons = new Array<Weapon>();
        
        loadUnlockedWeapons();
    }

    private static void loadUnlockedWeapons() {
        for(Object id : prefs.get().values()) {
            int inumb = Integer.valueOf(String.valueOf(id));
          
            // Flag the proper weapon as unlocked
            for(Weapon w : weapons) {
                if(w.getId() == inumb) {
                    if(w.isPrimary()) {
                        System.err.println("yah it is");
                        unlockedPrimaryWeapons.add(w);
                    }
                    else {
                        unlockedSecondaryWeapons.add(w);
                        System.err.println("nah");
                    }
                }
            }
        }
    }
    
    public static boolean isSecondaryUnlocked(int weaponID) {
        for(Weapon weapon : unlockedSecondaryWeapons) {
            if(weapon.getId() == weaponID) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isUnlocked(int weaponID) {
        // Could be simplified, favoring android performance :/
        
        for(Weapon w : unlockedPrimaryWeapons) {
            if(w.getId() == weaponID) {
                return true;
            }
        }
        
        for(Weapon w : unlockedSecondaryWeapons) {
            if(w.getId() == weaponID) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isPrimaryUnlocked(int weaponID) {
        for(Weapon weapon : unlockedPrimaryWeapons) {
            if(weapon.getId() == weaponID) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void unlockNextUpgrade(Weapon w) {
        int current = prefs.getInteger(w.getId() + UPGRADE_KEY, 0);


        if(current < getWeapon(w.isPrimary(), w.getId()).getUpgrades().length) {
            prefs.putInteger(w.getId() + UPGRADE_KEY, current + 1);
            prefs.flush();
            
            System.out.println("unlocked upgrade " + current + " for weapon " + w.getId());
        }
        else {
            System.out.println("unable to unlock upgrade for weapon " + w.getId());
        }
    }
    
    private static Weapon getWeapon(boolean primary, int id) {
        if(primary) {
            return getPrimary(id);
        }
        else {
            return getSecondary(id);
        }
    }

    private static Weapon getSecondary(int weaponID) {
        for(Weapon w : secondaryWeapons) {
            if(w.getId() == weaponID) {
                return w;
            }
        }
        
        return null;
    }
    
    private static Weapon getPrimary(int weaponID) {
        for(Weapon w : primaryWeapons) {
            if(w.getId() == weaponID) {
                return w;
            }
        }
        
        return null;
    }
    
    public static void unlockSecondary(int weaponID) {
        unlock(weaponID);
        
        unlockedSecondaryWeapons.add(getSecondary(weaponID));
    }
    
    public static void unlockPrimary(int weaponID) {
        unlock(weaponID);
        
        unlockedPrimaryWeapons.add(getPrimary(weaponID));
    }
    
    private static void unlock(int weaponID) {
        // Unlock weapon with that ID
        prefs.putInteger("unlocked" + weaponID, weaponID);
        prefs.flush();
    }
    
    public static Array<Weapon> getAllWeapons() {
        return weapons;
    }
    
    public static Array<Weapon> getUnlockedSecondaryWeapons() {
        return unlockedSecondaryWeapons;
    }
    
    public static Array<Weapon> getUnlockedPrimaryWeapons() {
        return unlockedPrimaryWeapons;
    }
    
    public static Array<Weapon> getPrimaryWeapons() {
        return primaryWeapons;
    }
    
    public static Array<Weapon> getSecondaryWeapons() {
        return secondaryWeapons;
    }
}
