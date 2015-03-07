package com.dpc.vthacks;

public class MathUtil {
    public static float rand(float min, float max) {
        return min + (float)(Math.random() * (max - min + 1));
    }
    
    public static float dst(float x, float y, float x1, float y1) {
        return (float) Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
    }
}
