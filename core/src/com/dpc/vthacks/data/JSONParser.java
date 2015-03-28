package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.zombie.ZombieSegment;

public class JSONParser {
    private static final String PROPERTIES_PATH = "json/properties.json";
    private static final String WEAPONS_PATH = "json/weapons.json";
    private static final String ZOMBIES_PATH = "json/zombies.json";
    
    public static void parseProperties() {
        FileHandle handle = Gdx.files.internal(PROPERTIES_PATH);
        FileHandle handle1 = Gdx.files.internal(WEAPONS_PATH);
        
        JsonReader reader = new JsonReader();
        
        JsonValue root = reader.parse(handle);
        JsonValue root1 = reader.parse(handle1);

        parseTanks(root.getChild("tank"));
        
        parseSoldiers(root.getChild("soldier"));
        
        parseBomb(root.getChild("bomb"));

        parseZombieProperties();
        
        parseBuildings();
        
        parsePlayer(root.getChild("player"));
    }

    /**
     * Parses building properties from JSON and gives it to the factory
     * @param building
     */
    private static void parseBuildings() {
        Factory.setBuildingProperties(new Properties());
    }
    
    /**
     * Parses tank properties from JSON and gives it to the factory
     * @param tank
     */
    private static void parseTanks(JsonValue tank) {
        AnimatedUnitProperties<SpriteAnimation> tankProperties = 
                new AnimatedUnitProperties<SpriteAnimation>()
                    .cost(tank.getInt("cost"))
                    .minDamage(tank.getFloat("minDamage"))
                    .maxDamage(tank.getFloat("maxDamage"))
                    .health(tank.getInt("health"))
                    .vel(tank.getFloat("velX"), tank.getFloat("velY"))
                    .range(tank.getFloat("range"))
                    .maxHealth(tank.getInt("max health"))
                    .frameTime(tank.getFloat("frameTime"))
                    .scaleX(tank.getFloat("scaleX"))
                    .scaleY(tank.getFloat("scaleY"));
        
        Factory.setTankProperties(tankProperties);
    }

    /**
     * Parses soldier properties from JSON and gives it to the factory
     * @param soldier
     */
    private static void parseSoldiers(JsonValue soldier) {
        AnimatedUnitProperties<SpriteAnimation> soldierProperties = 
                new AnimatedUnitProperties<SpriteAnimation>()
                    .cost(soldier.getInt("cost"))
                    .minDamage(soldier.getFloat("minDamage"))
                    .maxDamage(soldier.getFloat("maxDamage"))
                    .health(soldier.getInt("health"))
                    .range(soldier.getFloat("range"))
                    .vel(soldier.getFloat("velX"), soldier.getFloat("velY"))
                    .maxHealth(soldier.getInt("max health"))
                    .frameTime(soldier.getFloat("frameTime"))
                    .scaleX(soldier.getFloat("scaleX")) 
                    .scaleY(soldier.getFloat("scaleY"));
        
        Factory.setSoldierProperties(soldierProperties);
    }

    /**
     * Parses bomb properties from JSON and gives it to the factory
     * @param bomb
     */
    private static void parseBomb(JsonValue bomb) {
        Properties bombProperties = 
                new Properties()
                    .minDamage(bomb.getFloat("minDamage"))
                    .maxDamage(bomb.getFloat("maxDamage"))
                    .vel(bomb.getFloat("velX"), bomb.getFloat("velY"))
                    .scaleX(bomb.getFloat("scaleX"))
                    .scaleY(bomb.getFloat("scaleY"));
        
        Factory.setBombProperties(bombProperties);
    }

    /**
     * Parses player properties from JSON and gives it to the factory
     * @param player
     */
    private static void parsePlayer(JsonValue player) {
        ObjectMap<String, AdvancedSpriteAnimation> playerAnimationData = new ObjectMap<String, AdvancedSpriteAnimation>();
  
        AdvancedSpriteAnimation playerIdle = Assets.getAdvancedAnimation(player, 
                                                                         Player.IDLE_RIGHT, 
                                                                         3, true, false);
        
        AdvancedSpriteAnimation playerRun = Assets.getAdvancedAnimation(player, 
                                                                        Player.RUN_RIGHT, 
                                                                        3, true, false);
        
        playerAnimationData.put(Player.IDLE_RIGHT, playerIdle);
        
        playerAnimationData.put(Player.RUN_RIGHT, playerRun);
        
        AdvancedSpriteAnimation playerIdleLeft = new AdvancedSpriteAnimation(playerIdle, true);
        AdvancedSpriteAnimation playerRunLeft = new AdvancedSpriteAnimation(playerRun, true);
        
        playerAnimationData.put(Player.IDLE_LEFT, playerIdleLeft);

        playerAnimationData.put(Player.RUN_LEFT, playerRunLeft);

        
        Assets.playerAnimations = playerAnimationData;

        AnimatedUnitProperties<AdvancedSpriteAnimation> playerProperties = 
                new AnimatedUnitProperties<AdvancedSpriteAnimation>()
                    .range(player.getFloat("range"))
                    .health(player.getFloat("health"))
                    .vel(player.getFloat("velX"), player.getFloat("velY"))
                    .maxHealth(player.getInt("max health"))
                    .stateAnimations(playerAnimationData)
                    .scaleX(player.getFloat("scaleX"))
                    .scaleY(player.getFloat("scaleY"));

        Factory.setPlayerProperties(playerProperties);
    }
    
    /**
     * Parses zombie properties from JSON and gives it to the factory
     */
    private static void parseZombieProperties() {
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(ZOMBIES_PATH));
        
        for(int i = 0; i < 3; i++) {
            int tier = Integer.parseInt(root.get(i).name);
            JsonValue zombie = root.get(i).child;
 
            ZombieProperties zombieProperties = 
                    new ZombieProperties()
                        .maxDamage(zombie.getFloat("maxDamage"))
                        .minDamage(zombie.getFloat("minDamage"))
                        .health(zombie.getInt("health"))
                        .range(zombie.getFloat("range"))
                        .minVel(zombie.getFloat("minVelX"), zombie.getFloat("minVelY"))
                        .maxVel(zombie.getFloat("maxVelX"), zombie.getFloat("maxVelY"))
                        .maxHealth(zombie.getInt("maxHealth"))
                        .minKillMoney(zombie.getInt("minKillMoney"))
                        .maxKillMoney(zombie.getInt("maxKillMoney"))
                        .vel(MathUtil.rand(zombie.getFloat("minVelX"), zombie.getFloat("maxVelX")),
                             MathUtil.rand(zombie.getFloat("minVelY"), zombie.getFloat("maxVelY")))
                        .stateAnimations(Assets.zombieAnimations)
                        .attackSpeed(zombie.getFloat("attackSpeed"))
                        .scaleX(zombie.getFloat("scaleX"))
                        .scaleY(zombie.getFloat("scaleY"));
            
            ZombieSegment[] zombieSegments = new ZombieSegment[3];
            
            JsonValue segs = zombie.getChild("segments");

            for(int j = 0; j < 3; j++) {
                JsonValue child = segs.get(j);

                zombieSegments[j] = new ZombieSegment();
                
                zombieSegments[j].bounds = new Rectangle(child.child.getFloat("x"),
                                                         child.child.getFloat("y"),
                                                         child.child.getFloat("width"),
                                                         child.child.getFloat("height"));

                zombieSegments[j].damageFactor = child.child.getFloat("damageFactor");
                zombieSegments[j].offsetX = zombieSegments[j].bounds.x;
                zombieSegments[j].offsetY = zombieSegments[j].bounds.y;
             }
            
            zombieProperties.segments(zombieSegments);
            
            Factory.addZombieProperty(tier, zombieProperties);
        }
    }
    
    public static void parseLevels() {
        JsonReader reader = new JsonReader();
        
        JsonValue lvlRoot = reader.parse(Gdx.files.internal("json/levels.json"));
        ObjectMap<String, String> levelMap = new ObjectMap<String, String>();
        
        int numbOfChildren = 6;
        
        for(int i = 0; i < numbOfChildren; i++) {
            levelMap.put(lvlRoot.get(i).getString("level"),
                         lvlRoot.get(i).getString("file"));
        }
        
        LevelProperties.setLevels(levelMap);
    }
}
