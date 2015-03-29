package com.dpc.vthacks.factories;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.objects.AmmoCrate;
import com.dpc.vthacks.objects.GameSprite;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.weapons.Weapon;
import com.dpc.vthacks.zombie.Zombie;

public class Factory {
    private static AnimatedUnitProperties<AdvancedSpriteAnimation> playerProperties;
    private static AnimatedUnitProperties<SpriteAnimation> tankProperties;
    private static AnimatedUnitProperties<SpriteAnimation> soldierProperties;
    private static Properties bombProperties;
    private static Properties buildingProperties;
    private static Weapon primaryGun, secondaryGun;
    private static ObjectMap<Integer, ZombieProperties> zombieProperties = new ObjectMap<Integer, ZombieProperties>();
    private static Vector2 playerGunOffset;
    private static int currentZombieCreationTier = 1; // Current tier of zombie to obtain from the pool
    private static final int BASE_SIZE = 10;
    
    public static void init() {
//        myArmyY = GameScreen.battle.getMyArmy().getBase().getY();
//        myArmyX = GameScreen.battle.getMyArmy().getBase().getX();
    }
    
    public static final Pool<AmmoCrate> ammoCratePool = new Pool<AmmoCrate>(5) {

        @Override
        protected AmmoCrate newObject() {
            return new AmmoCrate(Assets.ammoCrate, 0, 0);
        }
        
    };
    
    public static final Pool<Zombie> zombiePool = new Pool<Zombie>(BASE_SIZE) {

        @Override
        protected Zombie newObject() {
            return Factory.createZombie();
        }
        
    };

    /**
     * Creates a zombie with the properties matching the tier string
     * Tier is defined in zombie.
     * @param tier
     * @return
     */
    public static Zombie createZombie() {
        ZombieProperties cpy = 
                new ZombieProperties(zombieProperties.get(currentZombieCreationTier));

        Zombie z = new Zombie("walking-right", 
                              cpy,
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
        AnimatedUnitProperties<AdvancedSpriteAnimation> cpy = 
                new AnimatedUnitProperties<AdvancedSpriteAnimation>(playerProperties);

        for(AdvancedSpriteAnimation a : cpy.getStateAnimations().values()) {
            a = new AdvancedSpriteAnimation(a, false);
        }
        
        Player p = new Player(Player.IDLE_RIGHT,
                              cpy, 
                              0, 0, 0.15f);

        primaryGun.setAmmo(primaryGun.getMaxAmmo());
        secondaryGun.setAmmo(secondaryGun.getMaxAmmo());
        
        p.setPrimary(primaryGun);
        p.setSecondary(secondaryGun);
        
        p.setCurrentWeapon(p.getPrimary());
        
        return p;
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
        return BASE_SIZE;
    }
    
    public static Vector2 getPlayerGunOffset() {
        return playerGunOffset;
    }
    
    public static void setPlayerGunOffset(Vector2 playerGunOffset) {
        Factory.playerGunOffset = playerGunOffset;
    }
    
    public static void addZombieProperty(int tier, ZombieProperties props) {
        zombieProperties.put(tier, props);
    }
    
    public static Properties getBuildingProperties() {
        return buildingProperties;
    }
    
    public static Weapon getPrimaryGun() {
        return primaryGun;
    }
    
    public static Weapon getSecondaryGun() {
        return secondaryGun;
    }
   
    public static void setPrimaryGun(Weapon primaryGun) {
        Factory.primaryGun = primaryGun;
    }
    
    public static void setSecondaryGun(Weapon secondaryGun) {
        Factory.secondaryGun = secondaryGun;
    }
    
    public static void setBuildingProperties(Properties buildingProperties) {
        Factory.buildingProperties = buildingProperties;
    }
    
    public static void setCurrentZombieCreationTier(int currentZombieCreationTier) {
        Factory.currentZombieCreationTier = currentZombieCreationTier;
    }
}
