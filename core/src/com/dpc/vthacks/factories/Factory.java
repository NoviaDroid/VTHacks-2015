package com.dpc.vthacks.factories;

import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Bullet;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.infantry.TankShell;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;
import com.dpc.vthacks.screens.GameScreen;

public class Factory {
    private static float playerHealth, playerDamage, playerVelX, playerVelY, playerRange, playerMaxHealth;
    private static float tankHealth, tankDamage, tankCost, tankVelX, tankVelY, tankRange, tankMaxHealth;
    private static float enemyHealth, enemyMaxHealth, enemyDamage, enemyVelX, enemyVelY, enemyRange;
    private static float enemySoldierHealth, enemySoldierMaxHealth, enemySoldierDamage, enemySoldierVelX, enemySoldierVelY, enemySoldierRange;
    private static float soldierHealth, soldierMaxHealth, soldierDamage, soldierVelX, soldierVelY, soldierCost, soldierRange;
    private static float bombDamage, bombVelX, bombVelY;
    private static float tankShellVelX, tankShellVelY;
    private static float enemyTankHealth, enemyTankMaxHealth, enemyTankDamage, enemyTankCost, enemyTankVelX, enemyTankVelY, enemyTankRange;
    private static float myArmyX, myArmyY;
    
    private static final int NUMBER_OF_BOMBS = 10;
    
    public static void init() {
        myArmyY = GameScreen.battle.getMyArmy().getBase().getY();
        myArmyX = GameScreen.battle.getMyArmy().getBase().getX();
    }
    
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
            return Factory.createTank();
        }
        
    };
    
    public static final Pool<Soldier> soldierPool = new Pool<Soldier>(NUMBER_OF_BOMBS) {

        @Override
        protected Soldier newObject() {
            return Factory.createSoldier();
        }
        
    };
    
    public static final Pool<Bullet> bulletPool = new Pool<Bullet>(NUMBER_OF_BOMBS) {

        @Override
        protected Bullet newObject() {
            return Factory.createBullet(0, 0);
        }
        
    };
    
    public static final Pool<Tank> enemyTankPool = new Pool<Tank>(NUMBER_OF_BOMBS) {

        @Override
        protected Tank newObject() {
            return Factory.createEnemyTank();
        }
        
    };
    
    public static final Pool<Soldier> enemySoldierPool = new Pool<Soldier>(NUMBER_OF_BOMBS) {

        @Override
        protected Soldier newObject() {
            return Factory.createEnemySoldier();
        }
        
    };
    
    public static Bullet createBullet(float x, float y) {
        return new Bullet(Assets.bullet, 20, 5, x, y);
    }
    
    public static Bomb createBomb(float x, float y) {
        return new Bomb(bombDamage, bombVelX, bombVelY, x, y);
    }
    
    public static TankShell createTankShell(float x, float y) {
        return new TankShell(Assets.tankShell, tankShellVelX, tankShellVelY, x, y);
    }
    
    public static Soldier createEnemySoldier() {
        return new Soldier(Assets.getEnemySoldierFrames(), enemySoldierRange, enemySoldierDamage, enemySoldierHealth, enemySoldierMaxHealth, enemySoldierVelX, enemySoldierVelY, GameScreen.battle.getEnemyArmy().getBase().getX(), GameScreen.battle.getEnemyArmy().getBase().getY());
    }
    
    public static Plane createPlayer() {
        Plane plane = new Plane(Assets.getPlaneFiringFrames(), Assets.getPlane(), playerRange, playerDamage, playerHealth, enemyMaxHealth, playerVelX, playerVelY, myArmyX, myArmyY);
        plane.setParentArmy(GameScreen.battle.getMyArmy());
        return plane;
    }
    
    public static Tank createTank() {
        Tank t = new Tank(Assets.getTankFrames(), tankRange, tankDamage, tankHealth, tankMaxHealth, tankVelX, tankVelY, myArmyX, myArmyY);
        t.setParentArmy(GameScreen.battle.getMyArmy());
        return t;
    }
    
    public static Tank createEnemyTank() {
        Tank t = new Tank(Assets.getEnemyTankFrames(), enemyTankRange, enemyTankDamage, enemyTankHealth, enemyTankMaxHealth, enemyTankVelX, enemyTankVelY, GameScreen.battle.getEnemyArmy().getBase().getX(), GameScreen.battle.getEnemyArmy().getBase().getY());
        t.setParentArmy(GameScreen.battle.getEnemyArmy());
        return t;
    }
    
    public static Plane createEnemyPlane(float x, float y) {
        Plane t = new Plane(null, null, enemyRange, enemyDamage, enemyHealth, enemyMaxHealth, enemyVelX, enemyVelY, x, y);
        t.setParentArmy(GameScreen.battle.getEnemyArmy());
        return t;
    }
    
    public static Soldier createSoldier() {
        Soldier s =  new Soldier(Assets.getSoldierFrames(), soldierRange, soldierDamage, soldierHealth, soldierMaxHealth, soldierVelX, soldierVelY, myArmyX, myArmyY);;
        s.setParentArmy(GameScreen.battle.getMyArmy());
        return s;
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

    public static float getEnemySoldierHealth() {
        return enemySoldierHealth;
    }

    public static void setEnemySoldierHealth(float enemySoldierHealth) {
        Factory.enemySoldierHealth = enemySoldierHealth;
    }

    public static float getEnemySoldierDamage() {
        return enemySoldierDamage;
    }

    public static void setEnemySoldierDamage(float enemySoldierDamage) {
        Factory.enemySoldierDamage = enemySoldierDamage;
    }

    public static float getEnemySoldierVelX() {
        return enemySoldierVelX;
    }

    public static void setEnemySoldierVelX(float enemySoldierVelX) {
        Factory.enemySoldierVelX = enemySoldierVelX;
    }

    public static float getEnemySoldierVelY() {
        return enemySoldierVelY;
    }

    public static void setEnemySoldierVelY(float enemySoldierVelY) {
        Factory.enemySoldierVelY = enemySoldierVelY;
    }

    public static float getEnemySoldierRange() {
        return enemySoldierRange;
    }

    public static void setEnemySoldierRange(float enemySoldierRange) {
        Factory.enemySoldierRange = enemySoldierRange;
    }

    public static float getEnemyTankHealth() {
        return enemyTankHealth;
    }

    public static void setEnemyTankHealth(float enemyTankHealth) {
        Factory.enemyTankHealth = enemyTankHealth;
    }

    public static float getEnemyTankDamage() {
        return enemyTankDamage;
    }

    public static void setEnemyTankDamage(float enemyTankDamage) {
        Factory.enemyTankDamage = enemyTankDamage;
    }

    public static float getEnemyTankCost() {
        return enemyTankCost;
    }

    public static void setEnemyTankCost(float enemyTankCost) {
        Factory.enemyTankCost = enemyTankCost;
    }

    public static float getEnemyTankVelX() {
        return enemyTankVelX;
    }

    public static void setEnemyTankVelX(float enemyTankVelX) {
        Factory.enemyTankVelX = enemyTankVelX;
    }

    public static float getEnemyTankVelY() {
        return enemyTankVelY;
    }

    public static void setEnemyTankVelY(float enemyTankVelY) {
        Factory.enemyTankVelY = enemyTankVelY;
    }

    public static float getEnemyTankRange() {
        return enemyTankRange;
    }

    public static void setEnemyTankRange(float enemyTankRange) {
        Factory.enemyTankRange = enemyTankRange;
    }

    public static float getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public static void setPlayerMaxHealth(float playerMaxHealth) {
        Factory.playerMaxHealth = playerMaxHealth;
    }

    public static float getTankMaxHealth() {
        return tankMaxHealth;
    }

    public static void setTankMaxHealth(float tankMaxHealth) {
        Factory.tankMaxHealth = tankMaxHealth;
    }

    public static float getEnemySoldierMaxHealth() {
        return enemySoldierMaxHealth;
    }

    public static void setEnemySoldierMaxHealth(float enemySoldierMaxHealth) {
        Factory.enemySoldierMaxHealth = enemySoldierMaxHealth;
    }

    public static float getSoldierMaxHealth() {
        return soldierMaxHealth;
    }

    public static void setSoldierMaxHealth(float soldierMaxHealth) {
        Factory.soldierMaxHealth = soldierMaxHealth;
    }

    public static float getEnemyTankMaxHealth() {
        return enemyTankMaxHealth;
    }

    public static void setEnemyTankMaxHealth(float enemyTankMaxHealth) {
        Factory.enemyTankMaxHealth = enemyTankMaxHealth;
    }

    public static float getMyArmyX() {
        return myArmyX;
    }

    public static void setMyArmyX(float myArmyX) {
        Factory.myArmyX = myArmyX;
    }

    public static float getMyArmyY() {
        return myArmyY;
    }

    public static void setMyArmyY(float myArmyY) {
        Factory.myArmyY = myArmyY;
    }

    public static float getEnemyMaxHealth() {
        return enemyMaxHealth;
    }

    public static void setEnemyMaxHealth(float enemyMaxHealth) {
        Factory.enemyMaxHealth = enemyMaxHealth;
    }

}
