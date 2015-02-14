package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.plane.Plane;

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
        
        Factory.setEnemyDamage(enemyPlane.getFloat("damage"));
        Factory.setEnemyHealth(enemyPlane.getInt("health"));
        Factory.setEnemyVelX(enemyPlane.getFloat("velX"));
        Factory.setEnemyVelY(enemyPlane.getFloat("velY"));
        Factory.setEnemyRange(enemyPlane.getFloat("range"));
        
        Factory.setPlayerRange(player.getFloat("range"));
        Factory.setPlayerDamage(player.getFloat("damage"));
        Factory.setPlayerHealth(player.getFloat("health"));
        Factory.setPlayerVelX(player.getFloat("velX"));
        Factory.setPlayerVelY(player.getFloat("velY"));
        
        Factory.setTankCost(tank.getInt("cost"));
        Factory.setTankDamage(tank.getFloat("damage"));
        Factory.setTankHealth(tank.getInt("health"));
        Factory.setTankVelX(tank.getFloat("velX"));
        Factory.setTankVelY(tank.getFloat("velY"));
        Factory.setTankRange(tank.getFloat("range"));
        
        Factory.setSoldierCost(soldier.getInt("cost"));
        Factory.setSoldierDamage(soldier.getFloat("damage"));
        Factory.setSoldierHealth(soldier.getInt("health"));
        Factory.setSoldierRange(soldier.getFloat("range"));
        Factory.setSoldierVelX(soldier.getFloat("velX"));
        Factory.setSoldierVelY(soldier.getFloat("velY"));

        Factory.setBombDamage(bomb.getFloat("damage"));
        Factory.setBombVelX(bomb.getFloat("velX"));
        Factory.setBombVelY(bomb.getFloat("velY"));
        
        Factory.setTankShellVelX(tankShell.getFloat("velX"));
        Factory.setTankShellVelY(tankShell.getFloat("velY"));
        
        Factory.setEnemySoldierDamage(enemySoldier.getFloat("damage"));
        Factory.setEnemySoldierHealth(enemySoldier.getInt("health"));
        Factory.setEnemySoldierVelX(enemySoldier.getFloat("velX"));
        Factory.setEnemySoldierVelY(enemySoldier.getFloat("velY"));
        Factory.setEnemySoldierRange(enemySoldier.getFloat("range"));
        
        Factory.setEnemyTankDamage(enemyTank.getFloat("damage"));
        Factory.setEnemyTankHealth(enemyTank.getInt("health"));
        Factory.setEnemyTankRange(enemyTank.getFloat("range"));
        Factory.setEnemyTankVelX(enemyTank.getFloat("velX"));
        Factory.setEnemyTankVelY(enemyTank.getFloat("velY"));
        
        Soldier.setKillExp(enemySoldier.getInt("kill exp"));
        Tank.setKillExp(enemyTank.getInt("kill exp"));
    }
}
