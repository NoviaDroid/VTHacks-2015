package com.dpc.vthacks.level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

public class LevelProperties {
    public static final float WIDTH = 2400;
    public static final float X_GRAV = 11;
    public static final Vector2 GRAVITY = new Vector2(X_GRAV, -5.5f);
    private static ObjectMap<String, String> levels; // Level #, Level file
    
    public static void setLevels(ObjectMap<String, String> levels) {
        LevelProperties.levels = levels;
    }
    
    public static ObjectMap<String, String> getLevels() {
        return levels;
    }
}   
