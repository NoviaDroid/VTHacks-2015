package com.dpc.vthacks.properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.dpc.vthacks.objects.Weapon;

public final class WeaponManager {
    public static final int NUMBER_OF_WEAPONS = 5;
    private static final String PREFS_PATH = "WPF";
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
        for (Object obj : json.fromJson(Array.class, Weapon.class, Gdx.files.internal("json/weapons.json"))) {
            weapons.add((Weapon) obj);
        }
        
        prefs = Gdx.app.getPreferences(PREFS_PATH);
        
        // Handgun is always unlocked
        prefs.putInteger("unlocked1", 1);
        prefs.flush();

        unlockedWeapons = new Array<Weapon>();
        
        loadUnlockedWeapons();
    }

    private static void loadUnlockedWeapons() {
        for(Object id : prefs.get().values()) {
            int inumb = Integer.valueOf((String) id);
            
            // Flag the proper weapon as unlocked
            for(Weapon w : weapons) {
                if(w.getId() == inumb) {
                    unlockedWeapons.add(w);
                }
            }
        }
        
        System.err.println(unlockedWeapons);
    }
    
    public static void unlock(int weaponID) {
        // Unlock weapon with that ID
        prefs.putInteger("unlocked" + weaponID, weaponID);
        prefs.flush();
    }
    
    public static Array<Weapon> getUnlockedWeapons() {
        return unlockedWeapons;
    }
    
    public static Array<Weapon> getWeapons() {
        return weapons;
    }
}
