package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.level.Level;

public abstract class GameObject extends Sprite {
    private boolean scrollable;
    private float scrollX, scrollY;
    private static Level parentLevel;
    
    public GameObject() {
    
    }
    
    public GameObject(TextureRegion region, float x, float y) {
        super(new TextureRegion(region));
        
        setOrigin(region.getRegionWidth() * 0.5f, region.getRegionHeight() * 0.5f);
        setX(x);
        setY(y);
    }

    public abstract void update(float delta);
    public abstract void render();
   
    public static void setParentLevel(Level parentLevel) {
        GameObject.parentLevel = parentLevel;
    }
    
    public static Level getParentLevel() {
        return parentLevel;
    }
    
    public void addPos(float x, float y) {
        setX(getX() + x);
        setY(getY() + y);
    }
    
    public void subPos(float x, float y) {
        setX(getX() - x);
        setY(getY() - y);
    }
    
    public boolean isScrollable() {
        return scrollable;
    }
   
    public float getScrollX() {
        return scrollX;
    }
    
    public float getScrollY() {
        return scrollY;
    }
    
    public void setScrollX(float scrollX) {
        this.scrollX = scrollX;
    }
    
    public void setScrollY(float scrollY) {
        this.scrollY = scrollY;
    }
    
    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }
    
    public void dispose() {
        getTexture().dispose();
    }
}
