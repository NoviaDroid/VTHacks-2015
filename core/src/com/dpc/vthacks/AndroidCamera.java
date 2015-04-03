package com.dpc.vthacks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class AndroidCamera extends OrthographicCamera {
    private Vector3 target;
    private static final float CAMERA_SPEED = 7;
    
    public AndroidCamera(int width, int height){
        this(width, height, width * 0.5f, height * 0.5f);
    }
    
    public AndroidCamera(int width, int height, float zoom) {
        this(width, height, 0, 0, zoom);
    }
    
    public AndroidCamera(int width, int height, float x, float y) {
        this(width, height, x, y, 1);
    }
    
    public AndroidCamera(int width, int height, float x, float y, float zoom){
        super(width, height);
        this.target = new Vector3();
        this.zoom = zoom;
        this.position.x = x;
        this.position.y = y;
    }
    
    public void lerp(float x, float y, float delta) {
        position.lerp(target.set(x, y, 0), delta * CAMERA_SPEED);
        update();
    }
}