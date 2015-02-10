package com.dpc.vthacks.infantry;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.dpc.vthacks.App;
import com.dpc.vthacks.gameobject.DynamicGameObject;

public class Bullet extends DynamicGameObject {

    public Bullet(TextureRegion region, float velX, float velY, float x, float y) {
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
}
