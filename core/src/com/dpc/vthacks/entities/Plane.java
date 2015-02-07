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
    private int fallRotation = -90, riseRotation = 15, fallDeltaFactor = 2, riseDeltaFactor = 10;
    private float plummitTimer;
    private static final float PLUMMIT_TIME = 0.15f; // If no positive force applied in this time, plane will plummit
    
    public Plane(float x, float y) {
        super(Assets.plane, 0, 18, x, y);
    }

    @Override
    public void update(float delta) {
        if(rising) {
            addVel();
            setRotation(getRotation() + (targetRotation - getRotation()) * delta * riseDeltaFactor);
        }
        else {
            plummitTimer += delta;
            
            // Begin to plummit if no pos force has been applied lately
            if(plummitTimer >= PLUMMIT_TIME) {
                setRotation(getRotation() + (targetRotation - getRotation()) * delta * fallDeltaFactor);
            }
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
            plummitTimer = 0;
        }
        else if(event == InputSystem.TOUCH_DRAGGED) {
            
        }
        else if(event == InputSystem.TOUCH_UP) {
            rising = false;
            targetRotation = fallRotation;
        }
    }
    
}
