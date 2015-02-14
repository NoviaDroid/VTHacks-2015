package com.dpc.vthacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GfxHelper {
    public static float oldX, oldY;
    
    public static Sprite resize(Sprite img) {
        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();
 
        float changeX = x / oldX;
        float changeY = y / oldY;
 
        img.setX(img.getX() * changeX);
        img.setY(img.getY() * changeY);
        img.setSize(img.getWidth() * changeX, img.getHeight() * changeY);
        
        return img;
    }
}