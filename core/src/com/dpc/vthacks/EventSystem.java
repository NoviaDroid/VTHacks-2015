package com.dpc.vthacks;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

public class EventSystem {
    public static final int TOUCH_DOWN = 0;
    public static final int ZOMBIE_DEATH = 1;
    public static final int ENTITY_ATTACK = 2;
    public static final int PLAYER_MONEY_CHANGED = 3;
    public static final int PLAYER_AMMO_CHANGED = 4;
    public static final int PLAYER_HEALTH_CHANGED = 5;
    public static final int GAME_OVER = 6;
    public static final int GAME_STARTED = 7;
    public static final int WAVE_ENDED = 8;
    public static final int WAVE_STARTED = 9;
    public static final int PLAYER_AMMO_OUT = 10;
    
    private static Array<EventListener> listeners;
    
    private static final class EventListener {
        private IListener listener;
        private Integer event;
        
        private EventListener(IListener listener, Integer event) {
            super();
            this.listener = listener;
            this.event = event;
        }
    } 
    
    public static void initialize() {
        listeners = new Array<EventListener>();
    }
    
    public static void dispatch(GameEvent e) {
        int size = listeners.size;
        
        for (int i = 0; i < size; i++) {
            EventListener l = listeners.get(i);
            
            if (l.event.intValue() == e.getEvent()) {
                l.listener.onEvent(e);
            }
        }
    }
    
    public static void register(int event, IListener listener) {
        listeners.add(new EventListener(listener, event));
    }
    
    public static void unregister(IListener listener) {
        Iterator<EventListener> iterator = listeners.iterator();
        
        while(iterator.hasNext()) {
            if(iterator.next().listener.equals(listener)) {
                iterator.remove();
            }
        }
    }
}