package com.dpc.vthacks.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.objects.GameSprite;
import com.dpc.vthacks.objects.Gun;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.zombie.Zombie;

public class Factory {
    private static Properties playerProperties;
    private static Properties tankProperties;
    private static Properties enemyTankProperties;
    private static Properties enemySoldierProperties;
    private static Properties soldierProperties;
    private static Properties bombProperties;
    private static Properties tankShellProperties;
    private static Properties buildingProperties;
    private static Gun primaryGun, secondaryGun;
    private static ZombieProperties zombieProperties;
    private static Vector2 playerGunOffset;
    
    private static final int NUMBER_OF_BOMBS = 100;
    
    public static void init() {
//        myArmyY = GameScreen.battle.getMyArmy().getBase().getY();
//        myArmyX = GameScreen.battle.getMyArmy().getBase().getX();
    }
    
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
    
    public static final Pool<Zombie> zombiePool = new Pool<Zombie>(NUMBER_OF_BOMBS) {

        @Override
        protected Zombie newObject() {
            return Factory.createZombie();
        }
        
    };
    
    public static Gun createPrimaryGun() {
        return new Gun(primaryGun);
    }
    
    public static Gun createSecondaryGun() {
        return new Gun(secondaryGun);
    }
    
    public static Zombie createZombie() {
        Zombie z = new Zombie(Assets.enemySoldierFrames, 
                              new ZombieProperties(zombieProperties),
                              0,
                              0);
        
        z.setVelX(MathUtils.random(z.getProperties().getMinVel().x, 
                                   z.getProperties().getMaxVel().x));

        z.setVelY(MathUtils.random(z.getProperties().getMinVel().y, 
                                   z.getProperties().getMaxVel().y));
        return z;
    }
    
    public static GameSprite createRandomBuilding(float x, float y) {
        Properties props = new Properties(buildingProperties);

        GameSprite b = new GameSprite(Assets.getBuildings()[MathUtils.random(Assets.getBuildings().length - 1)], 
                                      props,
                                      x,
                                      y);
        
        return b;
    }
    
    public static GameSprite createBuilding(TextureRegion building, float x, float y) {
        Properties props = new Properties(buildingProperties);

        GameSprite b = new GameSprite(building, props, x, y);
        b.setX(x);
        b.setY(y);
        
        return b;
    }
    
    public static Soldier createEnemySoldier() {
        return null;
      //  return new Soldier(Assets.enemySoldierFrames, Assets.enemySoldierFrames[0], new Properties(enemySoldierProperties));
    }
    
    public static Player createPlayer() {
        Player p = new Player(new Properties(playerProperties), 0, 0, 0.15f);
        p.setGunOffset(playerGunOffset);
        p.setPrimary(primaryGun);
        p.setCurrentWeapon(p.getPrimary());
        
        return p;
    }
    
    public static Tank createTank() {
        return null;
      //  return new Tank(Assets.tankFrames, Assets.tankFrames[0], new Properties(tankProperties));
    }
    
    public static Tank createEnemyTank() {
        return null;
     //   return new Tank(Assets.enemyTankFrames, Assets.enemyTankFrames()[0], new Properties(enemyTankProperties));
    }
    
    public static Player createEnemyPlane(float x, float y) {
        return new Player(null, x, y, 0.25f);
    }
    
    public static Soldier createSoldier() {
        return null;
    //    return new Soldier(Assets.soldierFrames, Assets.soldierFrames[0], new Properties(soldierProperties));
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

    public static int getNumberOfBombs() {
        return NUMBER_OF_BOMBS;
    }
    
    public static Pool<Tank> getTankpool() {
        return tankPool;
    }

    public static Pool<Soldier> getSoldierpool() {
        return soldierPool;
    }
    
    public static Properties getZombieProperties() {
        return zombieProperties;
    }
    
    public static Vector2 getPlayerGunOffset() {
        return playerGunOffset;
    }
    
    public static void setPlayerGunOffset(Vector2 playerGunOffset) {
        Factory.playerGunOffset = playerGunOffset;
    }
    
    public static void setZombieProperties(ZombieProperties zombieProperties) {
        Factory.zombieProperties = zombieProperties;
    }
    
    public static Properties getBuildingProperties() {
        return buildingProperties;
    }
    
    public static Gun getPrimaryGun() {
        return primaryGun;
    }
    
    public static Gun getSecondaryGun() {
        return secondaryGun;
    }
   
    public static void setPrimaryGun(Gun primaryGun) {
        Factory.primaryGun = primaryGun;
    }
    
    public static void setSecondaryGun(Gun secondaryGun) {
        Factory.secondaryGun = secondaryGun;
    }
    
    public static void setBuildingProperties(Properties buildingProperties) {
        Factory.buildingProperties = buildingProperties;
    }
}
