package com.dpc.vthacks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dpc.vthacks.data.AppData;

public class GameCamera extends OrthographicCamera {
    
    public GameCamera() {
    }
    
    public void resize(int width, int height) {
        setToOrtho(false, width, height);
        position.set(AppData.width * 0.5f, AppData.height * 0.5f, 0);
        zoom = 6;
        update();
    }
}
