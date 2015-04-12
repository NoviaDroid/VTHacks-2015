package com.dpc.vthacks.objects;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StreetLight extends GameSprite {
    private Light source;
    public static final int RAY_RADIUS = 450;
    
    public StreetLight(RayHandler handler, TextureRegion region, float x, float y) {
        super(region, x, y);
        
        source = new PointLight(handler, 375, Color.LIGHT_GRAY, RAY_RADIUS, x, y);
    }
    
    public void setSource(Light source) {
        this.source = source;
    }
    
    public Light getSource() {
        return source;
    }
}
