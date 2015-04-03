package com.dpc.vthacks.level;

import com.dpc.vthacks.Player;

public class LevelManager {
    public static final int CAMPAIGN_MODE = 0;
    public static final int ENDLESS_WAVES_MODE = 1;
    
    private static Level currentLevel;
    private static Player player;
    private static int currentLevelMode;

    public static int getCurrentLevelMode() {
        return currentLevelMode;
    }
    
    public static Level getCurrentLevel() {
        return currentLevel;
    }
    
    public static Player getPlayer() {
        return player;
    }
    
    public static void setCurrentLevel(Level currentLevel) {
        LevelManager.currentLevel = currentLevel;
    }
    
    public static void setPlayer(Player player) {
        LevelManager.player = player;
    }
}
