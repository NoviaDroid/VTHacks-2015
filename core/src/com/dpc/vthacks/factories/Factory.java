package com.dpc.vthacks.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.objects.AmmoCrate;
import com.dpc.vthacks.objects.GameSprite;
import com.dpc.vthacks.objects.Gun;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.zombie.Zombie;

public class Factory {
    private static AnimatedUnitProperties<AdvancedSpriteAnimation> playerProperties;
    private static AnimatedUnitProperties<SpriteAnimation> tankProperties;
    private static AnimatedUnitProperties<SpriteAnimation> soldierProperties;
    private static Properties bombProperties;
    private static Properties buildingProperties;
    private static Gun primaryGun, secondaryGun;
    private static ZombieProperties zombieProperties;
    private static Vector2 playerGunOffset;
    
    private static final int NUMBER_OF_BOMBS = 15;
    
    public static void init() {
//        myArmyY = GameScreen.battle.getMyArmy().getBase().getY();
//        myArmyX = GameScreen.battle.getMyArmy().getBase().getX();
    }
    
    public static final Pool<AmmoCrate> ammoCratePool = new Pool<AmmoCrate>(NUMBER_OF_BOMBS) {

        @Override
        protected AmmoCrate newObject() {
            return new AmmoCrate(Assets.ammoCrate, 0, 0);
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
        Zombie z = new Zombie("walking-right", 
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

        GameSprite b = new GameSprite(Assets.buildings[MathUtils.random(Assets.buildings.length - 1)], 
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
    public static Player createPlayer() {
        Player p = new Player("idle",
                              new AnimatedUnitProperties<AdvancedSpriteAnimation>(playerProperties), 
                              0, 0, 0.15f);
        
        p.setGunOffset(playerGunOffset);
        p.setPrimary(primaryGun);
        p.setCurrentWeapon(p.getPrimary());
        
        return p;
    }
    
    public static Tank createTank() {
        return new Tank("", 
                        new AnimatedUnitProperties<SpriteAnimation>(tankProperties),
                        0,
                        0);
    }
    
    public static Soldier createSoldier() {
        return new Soldier("",
                          new AnimatedUnitProperties<SpriteAnimation>(soldierProperties),
                          0,
                          0);
    }

    public static Properties getPlayerProperties() {
        return playerProperties;
    }

    public static void setPlayerProperties(AnimatedUnitProperties<AdvancedSpriteAnimation> playerProperties) {
        Factory.playerProperties = playerProperties;
    }

    public static Properties getTankProperties() {
        return tankProperties;
    }

    public static void setTankProperties(AnimatedUnitProperties<SpriteAnimation> tankProperties) {
        Factory.tankProperties = tankProperties;
    }

    public static Properties getSoldierProperties() {
        return soldierProperties;
    }

    public static void setSoldierProperties(AnimatedUnitProperties<SpriteAnimation> soldierProperties) {
        Factory.soldierProperties = soldierProperties;
    }

    public static Properties getBombProperties() {
        return bombProperties;
    }

    public static void setBombProperties(Properties bombProperties) {
        Factory.bombProperties = bombProperties;
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
