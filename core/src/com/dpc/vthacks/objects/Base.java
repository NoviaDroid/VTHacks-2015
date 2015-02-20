package com.dpc.vthacks.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.gameobject.GameObject;

public class Base extends GameObject {
    private float health = 100;
    
    public Base(TextureRegion region, float x, float y) {
        super(region, x, y);
        setSize(getWidth() * 3, getHeight() * 3);
    }

    @Override
    public void update(float delta) {
       
    }

    @Override
    public void render() {
        draw(App.batch);
        
        App.batch.draw(Assets.healthbar, getX(), getY() + getHeight() + 15, health, 25);
    }
    
    public void takeDamage(float amount) {
        health -= amount;
        
        if(health < 0) {
            health = 0;
        }
    }
    
    public float getHealth() {
        return health;
    }

}
