package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.properties.Properties;
import com.dpc.vthacks.properties.ZombieProperties;

public class JSONManager {
    private static final String PROPERTIES_PATH = "json/properties.json";
    
    public static void parseProperties() {
        FileHandle handle = Gdx.files.internal(PROPERTIES_PATH);
        
        JsonReader reader = new JsonReader();
        
        JsonValue root = reader.parse(handle);
        
        JsonValue tank = root.getChild("tank");
        JsonValue soldier = root.getChild("soldier");
        JsonValue player = root.getChild("player");
        JsonValue bomb = root.getChild("bomb");
        JsonValue tankShell = root.getChild("tank shell");
        JsonValue zombie = root.getChild("zombie");
        
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
        
        Factory.setZombieProperties(zombieProperties);
    }
}
