package com.dpc.vthacks.plane;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.screens.GameScreen;

public class Bomb extends DynamicGameObject {
    private static final int TARGET_FALL_ROTATION = -90;
    
    public Bomb(float x, float y) {
        super(Assets.bomb, 20, 4, x, y);
    }

    @Override
    public void update(float delta) {
        // Apply gravity
        applyVel(GameScreen.gravity.cpy().sub(0, getVelY()));
        
        // Gradually rotate the bomb
        setRotation(getRotation() + (TARGET_FALL_ROTATION - getRotation()) * delta * 2);
    }

    @Override
    public void render() {
        draw(App.batch);
    }

}
