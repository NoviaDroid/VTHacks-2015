package com.dpc.vthacks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.properties.Properties;

public abstract class Collidable extends DynamicGameObject {
    
    public Collidable(TextureRegion region, Properties properties) {
        super(region, properties);
    }
    
    public abstract void onCollision(Collidable object);
}
