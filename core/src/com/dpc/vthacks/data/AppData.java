package com.dpc.vthacks.data;

public class AppData {
    public static int width;
    public static int height;
    private static int money;
    
    public static void onResize(int width, int height) {
        AppData.width = width;
        AppData.height = height;
    }

    public static int getMoney() {
        return money;
    }

    public static void setMoney(int money) {
        AppData.money = money;
    }
    
    public static void addMoney(int money) {
        AppData.money += money;
    }
}
