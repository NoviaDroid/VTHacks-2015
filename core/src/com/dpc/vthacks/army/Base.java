package com.dpc.vthacks.army;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.infantry.Unit;

public class Base extends Sprite {
    private float health;
    
    public Base(TextureRegion region) {
        super(region);
        health = 100;
        setSize(getWidth() * 3, getHeight() * 3);
    }
    
    public Base() {
        health = 100;
    }
    
    
    public void draw() {
        draw(App.batch);
    }
    
    public void onAttacked(Unit a) {
        health -= a.getDamage();
    }
}
