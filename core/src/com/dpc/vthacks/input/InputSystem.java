package com.dpc.vthacks.input;

import com.badlogic.gdx.utils.Array;

public class InputSystem {
    public static final int TOUCH_DOWN = 0;
    public static final int TAP = 1;
    public static final int TOUCH_UP = 2;
    public static final int TOUCH_DRAGGED = 3;
    public static final int B = 4;
    
    private static Array<InputListener> listeners;
    
    public static void initialize() {
        listeners = new Array<InputListener>();
    }
    
    public static void dispatchEvent(int event) {
        for(InputListener l : listeners) {
            l.onInputEvent(event);
        }
    }
    
    public static void register(InputListener listener) {
        listeners.add(listener);
    }
    
    public static void unregister(InputListener listener) {
        listeners.removeValue(listener, false);
    }
}
