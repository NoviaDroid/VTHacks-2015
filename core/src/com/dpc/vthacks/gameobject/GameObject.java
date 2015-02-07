package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject extends Sprite {

    public GameObject(TextureRegion region, float x, float y) {
        super(region);
        
        setOrigin(region.getRegionWidth() * 0.5f, region.getRegionHeight() * 0.5f);
        setX(x);
        setY(y);
    }

    public abstract void update(float delta);
    public abstract void render();
    
    public void addPos(float x, float y) {
        setX(getX() + x);
        setY(getY() + y);
    }
    
    public void subPos(float x, float y) {
        setX(getX() - x);
        setY(getY() - y);
    }
    
    public void dispose() {
        getTexture().dispose();
    }
}
