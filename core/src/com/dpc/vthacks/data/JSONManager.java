package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dpc.vthacks.factories.Factory;

public class JSONManager {
    private static final String PROPERTIES_PATH = "json/properties.json";
    
    public static void parseProperties() {
        FileHandle handle = Gdx.files.internal(PROPERTIES_PATH);
        
        JsonReader reader = new JsonReader();
        
        JsonValue root = reader.parse(handle);
        
        JsonValue tank = root.getChild("tank");
        JsonValue player = root.getChild("player");
        JsonValue enemyPlane = root.getChild("enemy plane");

        Factory.setEnemyDamage(enemyPlane.getFloat("damage"));
        Factory.setEnemyHealth(enemyPlane.getInt("health"));
        Factory.setEnemyVelX(enemyPlane.getFloat("velX"));
        Factory.setEnemyVelY(enemyPlane.getFloat("velY"));
        
        Factory.setPlayerDamage(player.getFloat("damage"));
        Factory.setPlayerHealth(player.getFloat("health"));
        Factory.setPlayerVelX(player.getFloat("velX"));
        Factory.setPlayerVelY(player.getFloat("velY"));
        
        Factory.setTankCost(tank.getInt("cost"));
        Factory.setTankDamage(tank.getFloat("damage"));
        Factory.setTankHealth(tank.getInt("health"));
        Factory.setTankVelX(tank.getFloat("velX"));
        Factory.setTankVelY(tank.getFloat("velY"));
    }
}
