package com.dpc.vthacks;

public class GameEvent {
    private Object userData;
    private int event;
    
    public GameEvent(int event, Object userData) {
        this.event = event;
        this.userData = userData;
    }
    
    public GameEvent(int event) {
        this(event, null);
    }
    
    public GameEvent() {
    
    }
    
    public int getEvent() {
        return event;
    }
    
    public void setEvent(int event) {
        this.event = event;
    }
    
    public void setUserData(Object userData) {
        this.userData = userData;
    }
    
    public Object getUserData() {
        return userData;
    }
}
