package com.dpc.vthacks.plane;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.input.InputListener;
import com.dpc.vthacks.input.InputSystem;

public class Plane extends Unit implements InputListener {
    private static final float PLUMMIT_TIME = 0.05f; // If no positive force applied in this time, plane will plummit
    private boolean rising;
    private int targetRotation; // Current rotation that is being lerped to
    private int fallRotation = -5, riseRotation = 15, fallDeltaFactor = 4, riseDeltaFactor = 2;
    private float plummitTimer;
    private Array<Bomb> bombs;
    
    public Plane(TextureRegion region, float range, float damage, float health, float velX, float velY, float x, float y) {
        super(region, range, damage, health, velX, velY, x, y);
        
        bombs = new Array<Bomb>(45);
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
        
        // Make each bomb continue to fall
        for(Bomb b : bombs) {
            b.update(delta);
        }
    }

    @Override
    public void render() {
        draw(App.batch);
        
        for(Bomb b : bombs) {
            b.draw(App.batch);
        }
    }

    @Override
    public void attack(Unit enemy) {
        
    }

    @Override
    public void takeDamage(Unit attacker) {
        
    }

    @Override
    public void move() {
        
    }
    
    @Override
    public void onInputEvent(int event) {
        if(event == InputSystem.TOUCH_DOWN) {
            rising = true;
            targetRotation = riseRotation;
            plummitTimer = 0;
        }
        else if(event == InputSystem.TAP || event == InputSystem.B) {
            Bomb b = Factory.bombPool.obtain();
            b.setX(getX() + (getWidth() * 0.5f));
            b.setY(getY());
            
            bombs.add(b);
        }
        else if(event == InputSystem.B_UP) {

        }
        else if(event == InputSystem.TOUCH_UP) {
            rising = false;
            targetRotation = fallRotation;
        }
        else if(event == InputSystem.TOUCH_DRAGGED) {
            
        }
    }
    
    public Array<Bomb> getBombs() {
        return bombs;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        
        for(Bomb b : bombs) {
            b.dispose();
        }
    }
  
}
