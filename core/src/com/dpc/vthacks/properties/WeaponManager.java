package com.dpc.vthacks.properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.dpc.vthacks.objects.Weapon;

public class WeaponManager {
    public static final int NUMBER_OF_WEAPONS = 2;
    public static Array<Weapon> weapons;
    
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
    }
    
    public static Array<Weapon> getWeapons() {
        return weapons;
    }
}
