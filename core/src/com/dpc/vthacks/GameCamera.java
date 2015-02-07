package com.dpc.vthacks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dpc.vthacks.data.AppData;

public class GameCamera extends OrthographicCamera {
    private Viewport viewport;
    
    public GameCamera() {
        setToOrtho(false, AppData.width, AppData.height);
        position.set(AppData.width * 0.5f, (AppData.height * 0.5f) - (AppData.height / 4), 0);
        zoom = 0.5f;
        update();
        
       // viewport = new Stret(480, 800, this);
        
       // viewport.update(AppData.width, AppData.height);
    }
    
    public void resize(int width, int height) {
        //viewport.update(width, height);    
        
        //setToOrtho(false, width, height);
        //position.set(AppData.width * 0.5f, AppData.height * 0.5f, 0);
        //update();
    }
}
