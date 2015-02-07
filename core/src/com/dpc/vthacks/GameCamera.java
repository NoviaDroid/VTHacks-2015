package com.dpc.vthacks;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera extends OrthographicCamera {
    
    public GameCamera() {
        
    }
    
    public void resize(float width, float height) {
        setToOrtho(false, width, height);
        position.set(width * 0.5f, height * 0.5f, 0);
        update();
    }
}
