package com.dpc.vthacks.army;

import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;
import com.dpc.vthacks.screens.GameScreen;

public class Battle {
    public Army myArmy, enemyArmy;
    
    public Battle(Army myArmy, Army enemyArmy) {
        this.myArmy = myArmy;
        this.enemyArmy = enemyArmy;
    }
    
    
    public void update(float delta) {
        myArmy.update(delta);
        enemyArmy.update(delta);
        
        // TODO: DONT cheat :D . Take out the hidden unit
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
                
                Array<Bomb> bombs = GameScreen.getPlayer().getBombs();
                for (Bomb b : bombs) {
                    if (b.getBoundingRectangle().overlaps(u.getBoundingRectangle())) {
                        u.takeDamage(b.getDamage());
                        b.triggerExplosion();
                    }
                    
                    if (b.getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                        u1.takeDamage(b.getDamage());
                        b.triggerExplosion();
                    }
                }
                
                if(u instanceof Tank) {
                    Tank t = (Tank) u;
                    
                    if(t.shell != null) {
                        if (t.shell.getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                            
                                t.shell.triggerExplosion();
                                u1.takeDamage(u);
                                //t.moving = true;
                            
                        }
                    }
                }
                
                if(u1 instanceof Tank) {
                    Tank t = (Tank) u1;
                    
                    if(t.shell != null) {
                        if (t.shell.getBoundingRectangle().overlaps(u.getBoundingRectangle())) {
                  
                                t.shell.triggerExplosion();
                                u.takeDamage(u1);
                               // t.moving = true;
                            
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
