package com.dpc.vthacks.entities;

import com.badlogic.gdx.math.Vector2;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.gameobject.DynamicGameObject;
import com.dpc.vthacks.input.InputListener;
import com.dpc.vthacks.input.InputSystem;

public class Plane extends DynamicGameObject implements InputListener {
    
    public Plane(float x, float y) {
        super(Assets.plane, new Vector2(0, 15), x, y);
    }

    @Override
    public void update(float delta) {
        
    }

    @Override
    public void render() {
        
    }

    @Override
    public void onInputEvent(int event) {
        if(event == InputSystem.TOUCH_DOWN) {
            
        }
        else if(event == InputSystem.TOUCH_DRAGGED) {
            
        }
        else if(event == InputSystem.TOUCH_UP) {
            
        }
    }
    
}
