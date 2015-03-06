package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.animation.AdvancedAnimatedUnit;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.objects.Gun;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.properties.ZombieSegment;

public class JSONManager {
    private static final String PROPERTIES_PATH = "json/properties.json";
    private static final String WEAPONS_PATH = "json/weapons.json";
    
    public static void parseProperties() {
        FileHandle handle = Gdx.files.internal(PROPERTIES_PATH);
        FileHandle handle1 = Gdx.files.internal(WEAPONS_PATH);
        
        JsonReader reader = new JsonReader();
        
        JsonValue root = reader.parse(handle);
        JsonValue root1 = reader.parse(handle1);
        
        JsonValue tank = root.getChild("tank");
        JsonValue soldier = root.getChild("soldier");
        JsonValue player = root.getChild("player");
        JsonValue bomb = root.getChild("bomb");
        JsonValue tankShell = root.getChild("tank shell");
        JsonValue zombie = root.getChild("zombie");
        JsonValue weapon = root1.getChild("handgun1");
        
        AnimatedUnitProperties<SpriteAnimation> tankProperties = 
                new AnimatedUnitProperties<SpriteAnimation>()
                    .cost(tank.getInt("cost"))
                    .minDamage(tank.getFloat("minDamage"))
                    .maxDamage(player.getFloat("maxDamage"))
                    .health(tank.getInt("health"))
                    .vel(tank.getFloat("velX"), tank.getFloat("velY"))
                    .range(tank.getFloat("range"))
                    .maxHealth(tank.getInt("max health"))
                    .frameTime(soldier.getFloat("frameTime"));
        
        Factory.setTankProperties(tankProperties);
        
        AnimatedUnitProperties<SpriteAnimation> soldierProperties = 
                new AnimatedUnitProperties<SpriteAnimation>()
                    .cost(soldier.getInt("cost"))
                    .minDamage(soldier.getFloat("minDamage"))
                    .maxDamage(soldier.getFloat("maxDamage"))
                    .health(soldier.getInt("health"))
                    .range(soldier.getFloat("range"))
                    .vel(soldier.getFloat("velX"), soldier.getFloat("velY"))
                    .maxHealth(soldier.getInt("max health"))
                    .frameTime(soldier.getFloat("frameTime"));
        
        Factory.setSoldierProperties(soldierProperties);
        
        Properties bombProperties = 
                new Properties()
                    .minDamage(bomb.getFloat("minDamage"))
                    .maxDamage(bomb.getFloat("maxDamage"))
                    .vel(bomb.getFloat("velX"), bomb.getFloat("velY"));
        
        Factory.setBombProperties(bombProperties);
        
        Properties buildingProperties = new Properties();
        
        Factory.setBuildingProperties(buildingProperties);
        
        ZombieProperties zombieProperties = 
                new ZombieProperties()
                    .maxDamage(zombie.getFloat("maxDamage"))
                    .minDamage(zombie.getFloat("minDamage"))
                    .health(zombie.getInt("health"))
                    .range(zombie.getFloat("range"))
                    .minVel(zombie.getFloat("minVelX"), zombie.getFloat("minVelY"))
                    .maxVel(zombie.getFloat("maxVelX"), zombie.getFloat("maxVelY"))
                    .maxHealth(zombie.getInt("max health"))
                    .minKillMoney(zombie.getInt("minKillMoney"))
                    .maxKillMoney(zombie.getInt("maxKillMoney"))
                    .vel(MathUtil.rand(zombie.getFloat("minVelX"), zombie.getFloat("maxVelX")),
                         MathUtil.rand(zombie.getFloat("minVelY"), zombie.getFloat("maxVelY")))
                    .stateAnimations(Assets.zombieAnimations);

        
        ZombieSegment[] zombieSegments = new ZombieSegment[3];
        
        JsonValue segs = root.getChild("zombie").getChild("segments");

        for(int i = 0; i < 3; i++) {
            JsonValue child = segs.get(i);

            zombieSegments[i] = new ZombieSegment();
            
            zombieSegments[i].bounds = new Rectangle(child.child.getFloat("x"),
                                                     child.child.getFloat("y"),
                                                     child.child.getFloat("width"),
                                                     child.child.getFloat("height"));

            zombieSegments[i].damageFactor = child.child.getFloat("damageFactor");
            zombieSegments[i].offsetX = zombieSegments[i].bounds.x;
            zombieSegments[i].offsetY = zombieSegments[i].bounds.y;
         }
        
        zombieProperties.segments(zombieSegments);
        
        Factory.setZombieProperties(zombieProperties);
        
        Factory.setPlayerGunOffset(new Vector2(0, 0));
   

        ObjectMap<String, AdvancedSpriteAnimation> playerAnimationData = new ObjectMap<String, AdvancedSpriteAnimation>();
  
        playerAnimationData.put("idle", Assets.getAdvancedAnimation(player, "idle", 3, true, false));
        playerAnimationData.put("running", Assets.getAdvancedAnimation(player, "running", 3, true, false));
        
        Assets.playerAnimations = playerAnimationData;
  
        Factory.setPrimaryGun(createGun(root1.child.child, weapon.getChild("firing")));
        
        AnimatedUnitProperties<AdvancedSpriteAnimation> playerProperties = 
                new AnimatedUnitProperties<AdvancedSpriteAnimation>()
                    .range(player.getFloat("range"))
                    .minDamage(player.getFloat("minDamage"))
                    .maxDamage(player.getFloat("maxDamage"))
                    .health(player.getFloat("health"))
                    .vel(player.getFloat("velX"), player.getFloat("velY"))
                    .maxHealth(player.getInt("max health"))
                    .stateAnimations(playerAnimationData);
        
        Factory.setPlayerProperties(playerProperties);
    }
    
    private static Gun createGun(JsonValue root, JsonValue child) {
        FrameData data = new FrameData(Assets.gameAtlas.findRegion(child.getString("src")), 
                                       child.getFloat("time"), 
                                       child.getFloat("handleOffsetX"),
                                       child.getFloat("handleOffsetY"));

        Gun gun = new Gun(root.getFloat("minDmg"), 
                          root.getFloat("maxDmg"),
                          root.getInt("ammo"),
                          data);
        
        return gun;
        
    }
}
