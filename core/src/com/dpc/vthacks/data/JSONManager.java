package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.properties.Properties;

public class JSONManager {
    private static final String PROPERTIES_PATH = "json/properties.json";
    
    public static void parseProperties() {
        FileHandle handle = Gdx.files.internal(PROPERTIES_PATH);
        
        JsonReader reader = new JsonReader();
        
        JsonValue root = reader.parse(handle);
        
        JsonValue tank = root.getChild("tank");
        JsonValue soldier = root.getChild("soldier");
        JsonValue player = root.getChild("player");
        JsonValue enemyPlane = root.getChild("enemy plane");
        JsonValue bomb = root.getChild("bomb");
        JsonValue tankShell = root.getChild("tank shell");
        JsonValue enemySoldier = root.getChild("enemy soldier");
        JsonValue enemyTank = root.getChild("enemy tank");
        
        Properties enemyProperties = new Properties();
        
        enemyProperties.setDamage(enemyPlane.getFloat("damage"));
        enemyProperties.setHealth(enemyPlane.getInt("health"));
        enemyProperties.setVelX(enemyPlane.getFloat("velX"));
        enemyProperties.setVelY(enemyPlane.getFloat("velY"));
        enemyProperties.setRange(enemyPlane.getFloat("range"));
        enemyProperties.setMaxHealth(enemyPlane.getInt("max health"));
        
        Properties playerProperties = new Properties();
        
        playerProperties.setRange(player.getFloat("range"));
        playerProperties.setDamage(player.getFloat("damage"));
        playerProperties.setHealth(player.getFloat("health"));
        playerProperties.setVelX(player.getFloat("velX"));
        playerProperties.setVelY(player.getFloat("velY"));
        playerProperties.setMaxHealth(player.getInt("max health"));
        
        Factory.setPlayerProperties(playerProperties);
        
        Properties tankProperties = new Properties();
        
        tankProperties.setCost(tank.getInt("cost"));
        tankProperties.setDamage(tank.getFloat("damage"));
        tankProperties.setHealth(tank.getInt("health"));
        tankProperties.setVelX(tank.getFloat("velX"));
        tankProperties.setVelY(tank.getFloat("velY"));
        tankProperties.setRange(tank.getFloat("range"));
        tankProperties.setMaxHealth(tank.getInt("max health"));

        Factory.setTankProperties(tankProperties);
        
        Properties soldierProperties = new Properties();
        
        soldierProperties.setCost(soldier.getInt("cost"));
        soldierProperties.setDamage(soldier.getFloat("damage"));
        soldierProperties.setHealth(soldier.getInt("health"));
        soldierProperties.setRange(soldier.getFloat("range"));
        soldierProperties.setVelX(soldier.getFloat("velX"));
        soldierProperties.setVelY(soldier.getFloat("velY"));
        soldierProperties.setMaxHealth(soldier.getInt("max health"));
        
        Factory.setSoldierProperties(soldierProperties);
        
        Properties bombProperties = new Properties();
        
        bombProperties.setDamage(bomb.getFloat("damage"));
        bombProperties.setVelX(bomb.getFloat("velX"));
        bombProperties.setVelY(bomb.getFloat("velY"));
        
        Factory.setBombProperties(bombProperties);
        
        Properties tankShellProperties = new Properties();
        
        tankShellProperties.setVelX(tankShell.getFloat("velX"));
        tankShellProperties.setVelY(tankShell.getFloat("velY"));
        
        Factory.setTankShellProperties(tankShellProperties);
        
        Properties enemySoldierProperties = new Properties();
        
        enemySoldierProperties.setDamage(enemySoldier.getFloat("damage"));
        enemySoldierProperties.setHealth(enemySoldier.getInt("health"));
        enemySoldierProperties.setVelX(enemySoldier.getFloat("velX"));
        enemySoldierProperties.setVelY(enemySoldier.getFloat("velY"));
        enemySoldierProperties.setRange(enemySoldier.getFloat("range"));
        enemySoldierProperties.setMaxHealth(enemySoldier.getInt("max health"));
        
        Factory.setEnemySoldierProperties(enemySoldierProperties);
        
        Properties enemyTankProperties = new Properties();
        
        enemyTankProperties.setDamage(enemyTank.getFloat("damage"));
        enemyTankProperties.setHealth(enemyTank.getInt("health"));
        enemyTankProperties.setRange(enemyTank.getFloat("range"));
        enemyTankProperties.setVelX(enemyTank.getFloat("velX"));
        enemyTankProperties.setVelY(enemyTank.getFloat("velY"));
        enemyTankProperties.setMaxHealth(enemyTank.getInt("max health"));
        
        Factory.setEnemyTankProperties(enemyTankProperties);
        
        Properties buildingProperties = new Properties();
        
        Factory.setBuildingProperties(buildingProperties);
    }
}
