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
    private Army myArmy, enemyArmy;
    private Plane player;
    
    public Battle(Army myArmy, Army enemyArmy) {
        this.setMyArmy(myArmy);
        this.setEnemyArmy(enemyArmy);
    }
    
    
    public void update(float delta) {
        getMyArmy().update(delta);
        getEnemyArmy().update(delta);
        
        if(getMyArmy().getBase().getHealth() <= 0 || getEnemyArmy().getBase().getHealth() <= 0) {
            GameScreen.triggerGameOver();
        }
        
        checkCollisions();
    }
    
    private void checkCollisions() {
        // TODO: DONT cheat :D . Take out the hidden unit
        for(Unit u : getMyArmy().getUnits()) {
            for(Unit u1 : getEnemyArmy().getUnits()) {

                if(MathUtil.dst(u.getX(), u.getY(), u1.getX(), u1.getY()) <= u.getRange()) {
                    u.setMoving(false);                    
                    
                   u.attack(u1);
                    u.setAttacking(true);
                }
                else {
                    u.setMoving(true);
                    u.setAttacking(false);
                }
                
                if(MathUtil.dst(u1.getX(), u1.getY(), u.getX(), u.getY()) <= u1.getRange()) {
                    u1.setMoving(false);                    
                    
                    u1.attack(u);
                    u1.setAttacking(true);
                }
                else {
                    u1.setMoving(true);
                    u1.setAttacking(false);
                }
                
                Array<Bomb> bombs = GameScreen.getPlayer().getBombs();
                
                for(Bomb b : bombs) {
                    if(!b.isAlive()) {
                        if(b.getBoundingRectangle().overlaps(getEnemyArmy().getBase().getBoundingRectangle())) {
                            getEnemyArmy().getBase().loseLife(1);
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
                    
                    if(t.getShell() != null) {
                        if (t.getShell().getBoundingRectangle().overlaps(u1.getBoundingRectangle())) {
                            
                            //t.shell.triggerExplosion();
                            u1.takeDamage(u);
                        }
                    }
                }
                
                if(u1 instanceof Tank) {
                    Tank t = (Tank) u1;
                    
                    if(t.getShell() != null) {
                        if (t.getShell().getBoundingRectangle().overlaps(u.getBoundingRectangle())) {
                  
                                //t.shell.triggerExplosion();
                                u.takeDamage(u1);
                            
                        }
                    }
                }
            }
        }
    }
    
    public void render() {
        getMyArmy().render();
        getEnemyArmy().render();
    }
    
    public void dispose() {
        getMyArmy().dispose();
        getEnemyArmy().dispose();
    }


    public Plane getPlayer() {
        return player;
    }


    public void setPlayer(Plane player) {
        this.player = player;
    }


    public Army getMyArmy() {
        return myArmy;
    }


    public void setMyArmy(Army myArmy) {
        this.myArmy = myArmy;
    }


    public Army getEnemyArmy() {
        return enemyArmy;
    }


    public void setEnemyArmy(Army enemyArmy) {
        this.enemyArmy = enemyArmy;
    }
}
