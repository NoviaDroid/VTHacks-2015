package com.dpc.vthacks.infantry;

import com.badlogic.gdx.utils.Array;

public class Army {
    private Array<Unit> units;
    
    public Army() {
        units = new Array<Unit>();
    }
    
    public void update(float delta) {
        for(Unit u : units) {
            u.update(delta);
        }
    }
    
    public void render() {
        for(Unit u : units) {
            u.render();
        }
    }
    
    public void addUnit(Unit u) {
        units.add(u);
    }
    
    public void removeUnit(Unit u) {
        units.removeValue(u, false);
    }
}
