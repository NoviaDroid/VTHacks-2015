package com.dpc.vthacks.army;

import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.infantry.Unit;

public class Army {
    private Array<Unit> units;
    private Base base;
    
    public Army(Base base) {
        this.base = base;
        
        units = new Array<Unit>();
    }

    public Base getBase() {
        return base;
    }
    
    public Array<Unit> getUnits() {
        return units;
    }

    public void add(Unit unit) {
        units.add(unit);
    }
    
    public void setUnits(Array<Unit> units) {
        this.units = units;
    }
    
    public void render() {
        base.draw();
        
        for(Unit u : units) {
            u.render();
        }
    }
    
    public void dispose() {
        for(Unit u : units) {
            u.dispose();
        }
    }
    
    public void update(float delta) {
        for(Unit u : units) {
            u.update(delta);
            
            if(u.moving) {
                u.addVel();
            }
        }
    }
}
