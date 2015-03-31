package com.dpc.vthacks.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public final class WeaponManager {
    public static final int NUMBER_OF_WEAPONS = 5;
    private static final String UPGRADE_KEY = "currentUpgrade";
    private static final String PREFS_PATH = "WEAPON_PREFFS";
    private static Preferences prefs;
    private static Array<Weapon> weapons;
    private static Array<Weapon> unlockedWeapons;
    
    public static void load() {
        weapons = new Array<Weapon>(NUMBER_OF_WEAPONS);

        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);

        // Load every weapon in from the json file
        for (Object obj : json.fromJson(Array.class, Gun.class, Gdx.files.internal("json/weapons.json"))) {
            weapons.add((Weapon) obj);
        }
        
        prefs = Gdx.app.getPreferences(PREFS_PATH);

         //prefs.clear();
     //    prefs.flush();
         
        // Handgun is always unlocked
        prefs.putInteger("unlocked1", 1);
        prefs.putInteger("unlocked2", 2);
        prefs.flush();
      
        unlockedWeapons = new Array<Weapon>();
        
        loadUnlockedWeapons();
        
        unlockNextUpgrade(1);
    }

    private static void loadUnlockedWeapons() {
        for(Object id : prefs.get().values()) {
            int inumb = Integer.valueOf(String.valueOf(id));
            
            // Flag the proper weapon as unlocked
            for(Weapon w : weapons) {
                if(w.getId() == inumb) {
                    unlockedWeapons.add(w);
                }
            }
        }
    }
    
    public static boolean isUnlocked(int weaponID) {
        for(Weapon weapon : unlockedWeapons) {
            if(weapon.getId() == weaponID) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void unlockNextUpgrade(int weaponID) {
        int current = prefs.getInteger(weaponID + UPGRADE_KEY, 0);
        
        if(current < getWeapon(weaponID).getUpgrades().length) {
            prefs.putInteger(weaponID + UPGRADE_KEY, current + 1);
            prefs.flush();
            
            System.out.println("unlocked upgrade " + current + " for weapon " + weaponID);
        }
        else {
            System.out.println("unable to unlock upgrade for weapon " + weaponID);
        }
    }
    
    private static Weapon getWeapon(int weaponID) {
        for(Weapon w : weapons) {
            if(w.getId() == weaponID) {
                return w;
            }
        }
        
        return null;
    }
    
    public static void unlock(int weaponID) {
        // Unlock weapon with that ID
        prefs.putInteger("unlocked" + weaponID, weaponID);
        prefs.flush();
        
        unlockedWeapons.add(getWeapon(weaponID));
    }
    
    public static Array<Weapon> getUnlockedWeapons() {
        return unlockedWeapons;
    }
    
    public static Array<Weapon> getWeapons() {
        return weapons;
    }
}
