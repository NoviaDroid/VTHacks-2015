package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dpc.vthacks.App;
import com.dpc.vthacks.gameobject.DynamicGameObject;

public class TankShell extends DynamicGameObject implements Poolable {
    public Tank parentTank;
    
    public TankShell(TextureRegion region, float velX, float velY, float x, float y) {
        super(region, velX, velY, x, y);
    }

    @Override
    public void update(float delta) {
        addVel();
    }

    @Override
    public void render() {
        draw(App.batch);
    }

    @Override
    public void reset() {
    }
 
}
