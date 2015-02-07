package com.dpc.vthacks.entities;

import com.badlogic.gdx.math.Vector2;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.input.InputListener;
import com.dpc.vthacks.input.InputSystem;

public class Plane extends DynamicGameObject implements InputListener {
    private boolean rising;
    private int targetRotation; // Current rotation that is being lerped to
    private int fallRotation = -90, riseRotation = 15, fallDeltaFactor = 1, riseDeltaFactor = 10;
    
    public Plane(float x, float y) {
        super(Assets.plane, 0, 15, x, y);
    }

    @Override
    public void update(float delta) {
        if(rising) {
            addVel();
            setRotation(getRotation() + (targetRotation - getRotation()) * delta * riseDeltaFactor);
        }
        else {
            setRotation(getRotation() + (targetRotation - getRotation()) * delta * fallDeltaFactor);
        }
        
    }

    @Override
    public void render() {
        draw(App.batch);
    }

    @Override
    public void onInputEvent(int event) {
        if(event == InputSystem.TOUCH_DOWN) {
            rising = true;
            targetRotation = riseRotation;
        }
        else if(event == InputSystem.TOUCH_DRAGGED) {
            
        }
        else if(event == InputSystem.TOUCH_UP) {
            rising = false;
            targetRotation = fallRotation;
        }
    }
    
}
