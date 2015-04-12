package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class CampaignData {
    private static final String PREFS_FILE = "campaigndata";
    private static final String CURRENT_LEVEL = "currentlevel";
    private static Preferences prefs;
    private static int currentLevel;
    
    public static void initialize() {
        prefs = Gdx.app.getPreferences(PREFS_FILE);
        
        currentLevel = prefs.getInteger(CURRENT_LEVEL, 1);
    }
    
    public static void setCurrentLevel(int currentLevel) {
        CampaignData.currentLevel = currentLevel;
        
        prefs.putInteger(CURRENT_LEVEL, currentLevel);
        prefs.flush();
    }
    
    public static int getCurrentLevel() {
        return currentLevel;
    }
}
