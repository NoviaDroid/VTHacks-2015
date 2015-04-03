package com.dpc.vthacks.data;


public class AppData {
    public static int width;
    public static int height;
    public static int TARGET_WIDTH = 1024;
    public static int TARGET_HEIGHT = 768;
    
    public static void onResize(int width, int height) {
        AppData.width = width;
        AppData.height = height;
    }  
}
