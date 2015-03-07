package com.dpc.vthacks.data;

import com.badlogic.gdx.Preferences;

public class AppData {
    public static int width;
    public static int height;

    public static void onResize(int width, int height) {
        AppData.width = width;
        AppData.height = height;
    }
    
    
}
