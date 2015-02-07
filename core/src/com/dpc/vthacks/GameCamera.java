package com.dpc.vthacks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dpc.vthacks.data.AppData;

public class GameCamera extends OrthographicCamera {
    private Viewport viewport;
    
    public GameCamera() {
        viewport = new ExtendViewport(480, 800, this);
        
        setToOrtho(false, AppData.width, AppData.height);
        position.set(AppData.width * 0.5f, AppData.height * 0.5f, 0);
        update();
    }
    
    public void resize(float width, float height) {
        viewport.update((int) width, (int) height);
    }
}
