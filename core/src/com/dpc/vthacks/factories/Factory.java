package com.dpc.vthacks.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.objects.Building;
import com.dpc.vthacks.plane.Player;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.screens.GameScreen;

public class Factory {
    private static Properties playerProperties;
    private static Properties tankProperties;
    private static Properties enemyTankProperties;
    private static Properties enemySoldierProperties;
    private static Properties soldierProperties;
    private static Properties bombProperties;
    private static Properties tankShellProperties;
    private static Properties buildingProperties;
    private static float myArmyX, myArmyY;
    private static final int NUMBER_OF_BOMBS = 10;
    
    public static void init() {
//        myArmyY = GameScreen.battle.getMyArmy().getBase().getY();
//        myArmyX = GameScreen.battle.getMyArmy().getBase().getX();
    }
    
//    public static final Pool<TankShell> tankShellPool = new Pool<TankShell>(NUMBER_OF_BOMBS) {
//
//        @Override
//        protected TankShell newObject() {
//            return Factory.createTankShell(0, 0);
//        }
//        
//    };
    
//    public static final Pool<Bullet> bulletPool = new Pool<Bullet>(NUMBER_OF_BOMBS) {
//
//        @Override
//        protected Bullet newObject() {
//            return Factory.createBullet(0, 0);
//        }
//        
//    };
    
    public static final Pool<Player.Bomb> bombPool = new Pool<Player.Bomb>(NUMBER_OF_BOMBS) {

        @Override
        protected Player.Bomb newObject() {
            return GameScreen.getLevel().getPlayer().createBomb(Assets.getExplosionFrames(), Assets.getExplosionFrames()[0], bombProperties);
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
    
//    public static Bullet createBullet(float x, float y) {
//        return new Bullet(Assets.bullet, 20, 5, x, y);
//    }
    
//    public static TankShell createTankShell(float x, float y) {
//        return new TankShell(Assets.tankShell, tankShellVelX, tankShellVelY, x, y);
//    }
    
    public static Building createRandomBuilding(float x, float y) {
        Properties props = new Properties(buildingProperties);
        props.setX(x);
        props.setY(y);

        return new Building(Assets.getBuildings()[MathUtils.random(Assets.getBuildings().length - 1)], props);
    }
    
    public static Building createBuilding(TextureRegion building, float x, float y) {
        Properties props = new Properties(buildingProperties);
        props.setX(x);
        props.setY(y);

        return new Building(building, props);
    }
    
    public static Soldier createEnemySoldier() {
        return new Soldier(Assets.getEnemySoldierFrames(), Assets.getEnemyTankFrames()[0], new Properties(enemySoldierProperties));
    }
    
    public static Player createPlayer() {
        return new Player(Assets.getPlaneFiringFrames(), Assets.getPlane(), new Properties(playerProperties));
    }
    
    public static Tank createTank() {
        return new Tank(Assets.getTankFrames(), Assets.getTankFrames()[0], new Properties(tankProperties));
    }
    
    public static Tank createEnemyTank() {
        return new Tank(Assets.getEnemyTankFrames(), Assets.getEnemyTankFrames()[0], new Properties(enemyTankProperties));
    }
    
    public static Plane createEnemyPlane(float x, float y) {
        return new Plane(null, null, null);
    }
    
    public static Soldier createSoldier() {
        return new Soldier(Assets.getSoldierFrames(), Assets.getSoldierFrames()[0], new Properties(soldierProperties));
    }

    public static Properties getPlayerProperties() {
        return playerProperties;
    }

    public static void setPlayerProperties(Properties playerProperties) {
        Factory.playerProperties = playerProperties;
    }

    public static Properties getTankProperties() {
        return tankProperties;
    }

    public static void setTankProperties(Properties tankProperties) {
        Factory.tankProperties = tankProperties;
    }

    public static Properties getEnemyTankProperties() {
        return enemyTankProperties;
    }

    public static void setEnemyTankProperties(Properties enemyTankProperties) {
        Factory.enemyTankProperties = enemyTankProperties;
    }

    public static Properties getEnemySoldierProperties() {
        return enemySoldierProperties;
    }

    public static void setEnemySoldierProperties(Properties enemySoldierProperties) {
        Factory.enemySoldierProperties = enemySoldierProperties;
    }

    public static Properties getSoldierProperties() {
        return soldierProperties;
    }

    public static void setSoldierProperties(Properties soldierProperties) {
        Factory.soldierProperties = soldierProperties;
    }

    public static Properties getBombProperties() {
        return bombProperties;
    }

    public static void setBombProperties(Properties bombProperties) {
        Factory.bombProperties = bombProperties;
    }

    public static Properties getTankShellProperties() {
        return tankShellProperties;
    }

    public static void setTankShellProperties(Properties tankShellProperties) {
        Factory.tankShellProperties = tankShellProperties;
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

    public static int getNumberOfBombs() {
        return NUMBER_OF_BOMBS;
    }

    public static Pool<Player.Bomb> getBombpool() {
        return bombPool;
    }

    public static Pool<Tank> getTankpool() {
        return tankPool;
    }

    public static Pool<Soldier> getSoldierpool() {
        return soldierPool;
    }

    public static Pool<Tank> getEnemytankpool() {
        return enemyTankPool;
    }

    public static Pool<Soldier> getEnemysoldierpool() {
        return enemySoldierPool;
    } 
    
    public static Properties getBuildingProperties() {
        return buildingProperties;
    }
    
    public static void setBuildingProperties(Properties buildingProperties) {
        Factory.buildingProperties = buildingProperties;
    }
}
