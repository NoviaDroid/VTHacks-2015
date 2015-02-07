package com.dpc.vthacks.gameobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    private Vector2 position;
    private TextureRegion region;
    
    public GameObject(TextureRegion region, float x, float y) {
        this.region = region;
        position.x = x;
        position.y = y;
    }

    public abstract void update(float delta);
    public abstract void render();
    
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }
    
}
