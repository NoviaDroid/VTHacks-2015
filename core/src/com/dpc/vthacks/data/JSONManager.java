package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.objects.Gun;
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
        
        Properties playerProperties = new Properties();
        
        playerProperties.setRange(player.getFloat("range"));
        playerProperties.setMinDamage(player.getFloat("minDamage"));
        playerProperties.setMaxDamage(player.getFloat("maxDamage"));
        playerProperties.setHealth(player.getFloat("health"));
        playerProperties.setVelX(player.getFloat("velX"));
        playerProperties.setVelY(player.getFloat("velY"));
        playerProperties.setMaxHealth(player.getInt("max health"));
        
        Factory.setPlayerProperties(playerProperties);
        
        Properties tankProperties = new Properties();
        
        tankProperties.setCost(tank.getInt("cost"));
        tankProperties.setMinDamage(tank.getFloat("minDamage"));
        tankProperties.setMaxDamage(player.getFloat("maxDamage"));
        tankProperties.setHealth(tank.getInt("health"));
        tankProperties.setVelX(tank.getFloat("velX"));
        tankProperties.setVelY(tank.getFloat("velY"));
        tankProperties.setRange(tank.getFloat("range"));
        tankProperties.setMaxHealth(tank.getInt("max health"));
        tankProperties.setFrameTime(soldier.getFloat("frameTime"));
        
        Factory.setTankProperties(tankProperties);
        
        Properties soldierProperties = new Properties();
        
        soldierProperties.setCost(soldier.getInt("cost"));
        soldierProperties.setMinDamage(soldier.getFloat("minDamage"));
        soldierProperties.setMaxDamage(soldier.getFloat("maxDamage"));
        soldierProperties.setHealth(soldier.getInt("health"));
        soldierProperties.setRange(soldier.getFloat("range"));
        soldierProperties.setVelX(soldier.getFloat("velX"));
        soldierProperties.setVelY(soldier.getFloat("velY"));
        soldierProperties.setMaxHealth(soldier.getInt("max health"));
        soldierProperties.setFrameTime(soldier.getFloat("frameTime"));
        
        Factory.setSoldierProperties(soldierProperties);
        
        Properties bombProperties = new Properties();
        
        bombProperties.setMinDamage(bomb.getFloat("minDamage"));
        bombProperties.setMaxDamage(bomb.getFloat("maxDamage"));
        bombProperties.setVelX(bomb.getFloat("velX"));
        bombProperties.setVelY(bomb.getFloat("velY"));
        
        Factory.setBombProperties(bombProperties);
        
        Properties tankShellProperties = new Properties();
        
        tankShellProperties.setVelX(tankShell.getFloat("velX"));
        tankShellProperties.setVelY(tankShell.getFloat("velY"));
        
        Factory.setTankShellProperties(tankShellProperties);
        
        Properties buildingProperties = new Properties();
        
        Factory.setBuildingProperties(buildingProperties);
        
        ZombieProperties zombieProperties = new ZombieProperties();
        
        zombieProperties.setMaxDamage(zombie.getFloat("maxDamage"));
        zombieProperties.setMinDamage(zombie.getFloat("minDamage"));
        zombieProperties.setHealth(zombie.getInt("health"));
        zombieProperties.setRange(zombie.getFloat("range"));
        zombieProperties.setMinVel(new Vector2(zombie.getFloat("minVelX"), zombie.getFloat("minVelY")));
        zombieProperties.setMaxVel(new Vector2(zombie.getFloat("maxVelX"), zombie.getFloat("maxVelY")));
        zombieProperties.setMaxHealth(zombie.getInt("max health"));
        zombieProperties.setMinKillMoney(zombie.getInt("minKillMoney"));
        zombieProperties.setMaxKillMoney(zombie.getInt("maxKillMoney"));
        zombieProperties.setVel(new Vector2(MathUtil.rand(zombieProperties.getMinVel().x, zombieProperties.getMaxVel().x),
                                            MathUtil.rand(zombieProperties.getMinVel().y, zombieProperties.getMaxVel().y)));

        
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
        
        zombieProperties.setSegments(zombieSegments);
        
        Factory.setZombieProperties(zombieProperties);
        
        Factory.setPlayerGunOffset(new Vector2(0, 0));
   

        ObjectMap<String, AdvancedSpriteAnimation> playerAnimationData = new ObjectMap<String, AdvancedSpriteAnimation>();
  
        playerAnimationData.put("stationary", createAnimation(player, "stationary", 3));
        playerAnimationData.put("walking", createAnimation(player, "walking", 3));
        
        Assets.playerAnimationData = playerAnimationData;
  
        Factory.setPrimaryGun(createGun(root1.child.child, weapon.getChild("firing")));
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
    
    private static AdvancedSpriteAnimation createAnimation(JsonValue root, String child, int len) {
        JsonValue w = root.getChild(child);

        FrameData[] frames = new FrameData[len];
        
        for(int i = 0; i < frames.length; i++) {
            JsonValue c = w.get(i).child;
    
            frames[i] = new FrameData(Assets.gameAtlas.findRegion(c.getString("img")),
                    c.getFloat("time"), c.getFloat("handOffsetX"),
                    c.getFloat("handOffsetY"));
        
        }
        
        return new AdvancedSpriteAnimation(frames);
    }
}
