package com.dpc.vthacks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dpc.vthacks.data.AppData;

public class GameCamera extends OrthographicCamera {
    private Viewport viewport;
    private Vector3 target;
    private float cameraSpeed;
    
    public GameCamera() {
        target = new Vector3();
        setToOrtho(false, AppData.width, AppData.height);
        viewport = new StretchViewport(AppData.width, AppData.height, this);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void resize(int width, int height) {
        viewport = new ExtendViewport(AppData.width, AppData.height, this);
        viewport.update(width, height);
        position.set(width * 0.5f, height * 0.5f, 0);
        setToOrtho(false, width, height);
        update();
    }
    
    public void lerp(float x, float y, float delta) {
        position.lerp(target.set(x, y, 0), delta * cameraSpeed);
        update();
    }
}
