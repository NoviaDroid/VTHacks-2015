package com.dpc.vthacks.data;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.SpriteAnimation;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.level.LevelManager;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.modes.Campaign;
import com.dpc.vthacks.objects.GameSprite;
import com.dpc.vthacks.objects.Road;
import com.dpc.vthacks.properties.AnimatedUnitProperties;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.zombie.ZombieSegment;

public class Parser {
    private static final String PROPERTIES_PATH = "json/properties.json";
    private static final String WEAPONS_PATH = "json/weapons.json";
    private static final String ZOMBIES_PATH = "json/zombies.json";
    private static final String GAME_MODES_PATH = "json/Modes.json";
    private static final String CAMPAIGN_PATH = "json/campaignMap.oel";
    
    public static ObjectMap<Integer, String> parseGameModes() {
        JsonValue root = new JsonReader().parse(Gdx.files.internal(GAME_MODES_PATH));
        
        ObjectMap<Integer, String> modes = new ObjectMap<Integer, String>();
        
        modes.put(LevelManager.ENDLESS_WAVES_MODE, root.getString(LevelManager.ENDLESS_WAVES_MODE));
        modes.put(LevelManager.CAMPAIGN_MODE, root.getString(LevelManager.CAMPAIGN_MODE));
        
        return modes;
    }
    
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

    public static Array<Image> parseCampaignMap() throws IOException {
        XmlReader reader = new XmlReader();
        
        Element root = reader.parse(Gdx.files.internal(CAMPAIGN_PATH));
        
        int childrenCount = root.getChildCount();
        Element child = null;
        Image point = null;
        Array<Image> points = new Array<Image>();
        
        for(int i = 0; i < childrenCount; i++) {
            child = root.getChild(i);
            
            float x = 0, y = 0;
            
            int levelNumber = 1;
            
            for(Entry<String, String> entry : child.getAttributes()) {
                switch(entry.key) {
                case "x":
                    x = Float.parseFloat(entry.value);
                    break;
                case "y":
                    y = Float.parseFloat(entry.value);
                    break;
                case "id":
                    levelNumber = Integer.parseInt(entry.value);
                    break;
                }
            }
            
            point = new Image(Assets.campaignMapPoint);
            point.setPosition(x, y);
            
            // Save off the level number so it can be retrieved later
            point.setUserObject(levelNumber);
            
            points.add(point);
        }
            
        return points;
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
                        .vel(App.rand(zombie.getFloat("minVelX"), zombie.getFloat("maxVelX")),
                             App.rand(zombie.getFloat("minVelY"), zombie.getFloat("maxVelY")))
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
    
    public static void parseOgmoLevels(String file, Level level) throws IOException {
        
        XmlReader reader = new XmlReader();
        
        Element root = reader.parse(Gdx.files.internal(file));
        
        Element child = null;
        Element child2 = null;
        GameObject obj = null;
        
        if(level instanceof Campaign) {
            ((Campaign) level).setSurvivalTime(Integer.parseInt(root.getAttribute("survivalTime")));
        }

        int rootChildCount = root.getChildCount();

        Array<Array<GameObject>> parsedLayers = new Array<Array<GameObject>>(rootChildCount);
        
        for(int i = 0; i < rootChildCount; i++) {
            child = root.getChild(i);
           
            int numberOfChildren = child.getChildCount();

            Array<GameObject> childObjects = new Array<GameObject>();
            
            for(int j = 0; j < numberOfChildren; j++) {
                child2 = child.getChild(j);
                
                String name = child2.getName();
                
                if(name.startsWith("skyline")) {
                    TextureRegion tex = Assets.skylines[Integer.parseInt(name.substring(name.indexOf("e") + 1, name.length())) - 1];
                   
                    obj = new GameSprite(tex, 0, 0);
                    
                    obj.setSize(LevelProperties.WIDTH * 1.5f, AppData.height * 0.9f);
                }    
                else if(name.startsWith("Building")) {
                    obj = Factory.createBuilding(Assets.buildings[Integer
                            .parseInt(name.substring(name.indexOf("g") + 1,
                                    name.length())) - 1], 0, 0);
                    
                    obj.setSize(obj.getWidth() * 5, 
                                obj.getHeight() * 5);
                }
                else if(name.equals("Road")) {
                    obj = new Road(0, 0, 0, 0, LevelProperties.WIDTH, Assets.road.getRegionHeight() * 2);
                    level.getPlayer().setGround(obj.getBoundingRectangle());
                }
                
                // Index of where the object will be placed into the array
                int zOrder = 0;
                
                for(Entry<String, String> entry : child2.getAttributes()) {
                    switch(entry.key) {
                    case "x":
                        float x = Float.parseFloat(entry.value);
                        
                        if(obj instanceof GameObject) {
                            obj.setX(x);
                        }
                        
                        break;
                    case "y":
                        float y = AppData.height - Float.parseFloat(entry.value);
                        
                        if(obj instanceof GameObject) {
                            obj.setY(y);
                        }

                        break;
                    case "scrollable":
                        if(entry.value.equals("true")) {
                            obj.setScrollable(true);
                        }
                        break;
                    case "scrollX":
                        obj.setScrollX(Float.parseFloat(entry.value));
                        break;
                    case "scrollY":
                        obj.setScrollY(Float.parseFloat(entry.value));
                        break;
                    case "zOrder": 
                        zOrder = Integer.parseInt(entry.value);
                        break;
                    }

                    childObjects.add(obj);
                }
            }
            
            parsedLayers.add(childObjects);
        }
        
        level.setSpawnTime(root.getFloat("zombieSpawnTime"));
        level.setLayers(parsedLayers);
    System.out.println(level.getSpawnTime());
    }
}
