package com.dpc.vthacks.army;

import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.infantry.Unit;

public class Battle {
    public Army myArmy, enemyArmy;
    
    public Battle(Army myArmy, Army enemyArmy) {
        this.myArmy = myArmy;
        this.enemyArmy = enemyArmy;
    }
    
    
    public void update(float delta) {
        myArmy.update(delta);
        enemyArmy.update(delta);
        
        for(Unit u : myArmy.getUnits()) {
            for(Unit u1 : enemyArmy.getUnits()) {
                if(MathUtil.dst(u.getX(), u.getY(), u1.getX(), u1.getY()) <= u.getRange()) {
                    if(u.moving) {
                        u.stop();                    
                        u.attack(u1);
                    }

                }
                
                if(MathUtil.dst(u.getX(), u.getY(), u1.getX(), u1.getY()) <= u1.getRange()) {
                    if(u1.moving) {
                        u1.stop();                    
                        u1.attack(u);
                    }
                   
                }
                
                if(u instanceof Tank) {
                    Tank t = (Tank) u;
                    
                    if(t.shell != null) {
                        if (t.shell.getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                            if(u1 instanceof Tank) {
                                t.shell.triggerExplosion();
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void render() {
        myArmy.render();
        enemyArmy.render();
    }
    
    public void dispose() {
        myArmy.dispose();
        enemyArmy.dispose();
    }
}
