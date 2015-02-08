package com.dpc.vthacks.factories;

import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.infantry.TankShell;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;

public class Factory {
    private static float playerHealth, playerDamage, playerVelX, playerVelY, playerRange;
    private static float tankHealth, tankDamage, tankCost, tankVelX, tankVelY, tankRange;
    private static float enemyHealth, enemyDamage, enemyVelX, enemyVelY, enemyRange;
    private static float soldierHealth, soldierDamage, soldierVelX, soldierVelY, soldierCost, soldierRange;
    private static float bombDamage, bombVelX, bombVelY;
    private static float tankShellVelX, tankShellVelY;
    
    private static final int NUMBER_OF_BOMBS = 10;
    
    public static final Pool<TankShell> tankShellPool = new Pool<TankShell>(NUMBER_OF_BOMBS) {

        @Override
        protected TankShell newObject() {
            return Factory.createTankShell(0, 0);
        }
        
    };
    
    public static final Pool<Bomb> bombPool = new Pool<Bomb>(NUMBER_OF_BOMBS) {

        @Override
        protected Bomb newObject() {
            return Factory.createBomb(0, 0);
        }
        
    };
    
    public static final Pool<Tank> tankPool = new Pool<Tank>() {

        @Override
        protected Tank newObject() {
            return Factory.createTank(0, 10);
        }
        
    };
    
    public static final Pool<Soldier> soldierPool = new Pool<Soldier>() {

        @Override
        protected Soldier newObject() {
            return Factory.createSoldier(0, 10);
        }
        
    };
    
    public static Bomb createBomb(float x, float y) {
        return new Bomb(bombVelX, bombVelY, x, y);
    }
    
    public static TankShell createTankShell(float x, float y) {
        return new TankShell(Assets.tankShell, tankShellVelX, tankShellVelY, x, y, x, y);
    }
    
    public static Plane createPlayer(float x, float y) {
        return new Plane(Assets.plane, playerRange, playerDamage, playerHealth, playerVelX, playerVelY, x, y);
    }
    
    public static Tank createTank(float x, float y) {
        return new Tank(Assets.tankFrames, tankRange, tankDamage, tankHealth, tankVelX, tankVelY, x, y);
    }
    
    public static Tank createEnemyTank(float x, float y) {
        return new Tank(Assets.enemyTankFrames, tankRange, tankDamage, tankHealth, tankVelX, tankVelY, x, y);
    }
    
    public static Plane createEnemyPlane(float x, float y) {
        return new Plane(Assets.plane, enemyRange, enemyDamage, enemyHealth, enemyVelX, enemyVelY, x, y);
    }
    
    public static Soldier createSoldier(float x, float y) {
        return new Soldier(Assets.soldierFrames, soldierRange, soldierDamage, soldierHealth, soldierVelX, soldierVelY, x, y);
    }

    public static Soldier createEnemySoldier(float x, float y) {
        return new Soldier(Assets.soldierFrames, soldierRange, soldierDamage, soldierHealth, soldierVelX, soldierVelY, x, y);
    }
    
    public static float getPlayerHealth() {
        return playerHealth;
    }

    public static void setPlayerHealth(float playerHealth) {
        Factory.playerHealth = playerHealth;
    }

    public static float getPlayerDamage() {
        return playerDamage;
    }

    public static void setPlayerDamage(float playerDamage) {
        Factory.playerDamage = playerDamage;
    }

    public static float getPlayerVelX() {
        return playerVelX;
    }

    public static void setPlayerVelX(float playerVelX) {
        Factory.playerVelX = playerVelX;
    }

    public static float getPlayerVelY() {
        return playerVelY;
    }

    public static void setPlayerVelY(float playerVelY) {
        Factory.playerVelY = playerVelY;
    }

    public static float getTankHealth() {
        return tankHealth;
    }

    public static void setTankHealth(float tankHealth) {
        Factory.tankHealth = tankHealth;
    }

    public static float getTankDamage() {
        return tankDamage;
    }

    public static void setTankDamage(float tankDamage) {
        Factory.tankDamage = tankDamage;
    }

    public static float getTankCost() {
        return tankCost;
    }

    public static void setTankCost(float tankCost) {
        Factory.tankCost = tankCost;
    }

    public static float getTankVelX() {
        return tankVelX;
    }

    public static void setTankVelX(float tankVelX) {
        Factory.tankVelX = tankVelX;
    }

    public static float getTankVelY() {
        return tankVelY;
    }

    public static void setTankVelY(float tankVelY) {
        Factory.tankVelY = tankVelY;
    }

    public static float getEnemyHealth() {
        return enemyHealth;
    }

    public static void setEnemyHealth(float enemyHealth) {
        Factory.enemyHealth = enemyHealth;
    }

    public static float getEnemyDamage() {
        return enemyDamage;
    }

    public static void setEnemyDamage(float enemyDamage) {
        Factory.enemyDamage = enemyDamage;
    }

    public static float getEnemyVelX() {
        return enemyVelX;
    }

    public static void setEnemyVelX(float enemyVelX) {
        Factory.enemyVelX = enemyVelX;
    }

    public static float getEnemyVelY() {
        return enemyVelY;
    }

    public static void setEnemyVelY(float enemyVelY) {
        Factory.enemyVelY = enemyVelY;
    }

    public static float getPlayerRange() {
        return playerRange;
    }

    public static void setPlayerRange(float playerRange) {
        Factory.playerRange = playerRange;
    }

    public static float getTankRange() {
        return tankRange;
    }

    public static void setTankRange(float tankRange) {
        Factory.tankRange = tankRange;
    }

    public static float getEnemyRange() {
        return enemyRange;
    }

    public static void setEnemyRange(float enemyRange) {
        Factory.enemyRange = enemyRange;
    }

    public static float getSoldierHealth() {
        return soldierHealth;
    }

    public static void setSoldierHealth(float soldierHealth) {
        Factory.soldierHealth = soldierHealth;
    }

    public static float getSoldierDamage() {
        return soldierDamage;
    }

    public static void setSoldierDamage(float soldierDamage) {
        Factory.soldierDamage = soldierDamage;
    }

    public static float getSoldierVelX() {
        return soldierVelX;
    }

    public static void setSoldierVelX(float soldierVelX) {
        Factory.soldierVelX = soldierVelX;
    }

    public static float getSoldierVelY() {
        return soldierVelY;
    }

    public static void setSoldierVelY(float soldierVelY) {
        Factory.soldierVelY = soldierVelY;
    }

    public static float getSoldierCost() {
        return soldierCost;
    }

    public static void setSoldierCost(float soldierCost) {
        Factory.soldierCost = soldierCost;
    }

    public static float getSoldierRange() {
        return soldierRange;
    }

    public static void setSoldierRange(float soldierRange) {
        Factory.soldierRange = soldierRange;
    }

    public static float getBombDamage() {
        return bombDamage;
    }

    public static void setBombDamage(float bombDamage) {
        Factory.bombDamage = bombDamage;
    }

    public static float getBombVelX() {
        return bombVelX;
    }

    public static void setBombVelX(float bombVelX) {
        Factory.bombVelX = bombVelX;
    }

    public static float getBombVelY() {
        return bombVelY;
    }

    public static void setBombVelY(float bombVelY) {
        Factory.bombVelY = bombVelY;
    }

    public static float getTankShellVelX() {
        return tankShellVelX;
    }

    public static void setTankShellVelX(float tankShellVelX) {
        Factory.tankShellVelX = tankShellVelX;
    }

    public static float getTankShellVelY() {
        return tankShellVelY;
    }

    public static void setTankShellVelY(float tankShellVelY) {
        Factory.tankShellVelY = tankShellVelY;
    }

}
