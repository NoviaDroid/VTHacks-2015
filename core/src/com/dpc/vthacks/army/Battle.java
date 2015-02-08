package com.dpc.vthacks.army;

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
                if(u.inRange(u1)) {
                    u.attack(u1);
                }
                
                if(u1.inRange(u)) {
                    u1.attack(u);
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
