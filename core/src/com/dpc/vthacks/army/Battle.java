package com.dpc.vthacks.army;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Bullet;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;
import com.dpc.vthacks.screens.GameScreen;

public class Battle {
    public Army myArmy, enemyArmy;
    public Plane player;
    
    public Battle(Army myArmy, Army enemyArmy) {
        this.myArmy = myArmy;
        this.enemyArmy = enemyArmy;
    }
    
    
    public void update(float delta) {
        myArmy.update(delta);
        enemyArmy.update(delta);
        
        if(myArmy.getBase().getHealth() <= 0 || enemyArmy.getBase().getHealth() <= 0) {
            GameScreen.triggerGameOver();
        }

        checkCollisions();
    }
    
    private void checkCollisions() {
        // TODO: DONT cheat :D . Take out the hidden unit
        for(Unit u : myArmy.getUnits()) {
            for(Unit u1 : enemyArmy.getUnits()) {

                if(MathUtil.dst(u.getX(), u.getY(), u1.getX(), u1.getY()) <= u.getRange()) {
                    u.moving = false;                    
                    
                    u.attack(u1);
                    u.setAttacking(true);
                }
                else {
                    u.moving = true;
                    u.setAttacking(false);
                }
                
                if(MathUtil.dst(u1.getX(), u1.getY(), u.getX(), u.getY()) <= u1.getRange()) {
                    u1.moving = false;                    
                    
                    u1.attack(u);
                    u1.setAttacking(true);
                }
                else {
                    u1.moving = true;
                    u1.setAttacking(false);
                }
                
                Array<Bomb> bombs = GameScreen.getPlayer().getBombs();
                
                for(Bomb b : bombs) {
                    if(!b.isAlive()) {
                        if(b.getBoundingRectangle().overlaps(enemyArmy.getBase().getBoundingRectangle())) {
                            enemyArmy.getBase().loseLife(1);
                            b.triggerExplosion();
                        }
    
                        if (b.getBoundingRectangle().overlaps(u.getBoundingRectangle())) {
                            u.takeDamage(b.getDamage());
                            b.triggerExplosion();
                        }
                        
                        if (b.getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                            u1.takeDamage(b.getDamage());
                            b.triggerExplosion();
                        }
                    }
                }
                
                Array<Bullet> bullets;
                
                if(u instanceof Soldier) {
                    Soldier s = (Soldier) u;
                    
                    bullets = s.getBullets();
                    
                    Iterator<Bullet> it = bullets.iterator();
                    
                    while(it.hasNext()) {
                        Bullet b = it.next();
                        
                        if(b.getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                            u1.takeDamage(s.getDamage());
                            Factory.bulletPool.free(b);
                            it.remove();
                        }
                    }
                }
                
                if(u1 instanceof Soldier) {
                    Soldier s = (Soldier) u1;
                    
                    bullets = s.getBullets();
                    
                    Iterator<Bullet> it = bullets.iterator();
                    
                    while(it.hasNext()) {
                        Bullet b = it.next();
                        
                        if(b.getBoundingRectangle().overlaps(u.getBoundingRectangle())) {
                            u.takeDamage(s.getDamage());
                            Factory.bulletPool.free(b);
                            it.remove();
                        }
                    }
                }

                if(u instanceof Tank) {
                    Tank t = (Tank) u;
                    
                    if(t.shell != null) {
                        if (t.shell.getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                            
                            //t.shell.triggerExplosion();
                            u1.takeDamage(u);
                        }
                    }
                }
                
                if(u1 instanceof Tank) {
                    Tank t = (Tank) u1;
                    
                    if(t.shell != null) {
                        if (t.shell.getBoundingRectangle().overlaps(u.getBoundingRectangle())) {
                  
                                //t.shell.triggerExplosion();
                                u.takeDamage(u1);
                            
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
