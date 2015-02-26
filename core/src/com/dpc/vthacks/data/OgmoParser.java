package com.dpc.vthacks.data;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.dpc.vthacks.Level;
import com.dpc.vthacks.LevelProperties;
import com.dpc.vthacks.Road;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.objects.Base;
import com.dpc.vthacks.objects.GameSprite;
import com.dpc.vthacks.objects.LayerManager;

public class OgmoParser {
    
    public static void parse(String file, Level level) throws IOException {
        
        XmlReader reader = new XmlReader();
        
        Element root = reader.parse(Gdx.files.internal(file));
        
        Element child = null;
        Element child2 = null;
        Object obj = null;
        
        int rootChildCount = root.getChildCount();
        
        // Not recursive because this XML file isn't complex..
        for(int i = 0; i < rootChildCount; i++) {
            child = root.getChild(i);
           
            int len = child.getChildCount();
            LayerManager.Layer parsed = new LayerManager.Layer(child.getName());
            
            for(int j = 0; j < len; j++) {
                child2 = child.getChild(j);
                
                String name = child2.getName();
                
                if(name.startsWith("skyline")) {
                    TextureRegion tex = Assets.getSkylines()[Integer.parseInt(name.substring(name.indexOf("e") + 1, name.length())) - 1];
                   
                    obj = new GameSprite(tex, 0, 0);
                    
                    ((GameObject) obj).setSize(LevelProperties.WIDTH * 1.5f, AppData.height * 0.9f);
                }    
                else if(name.startsWith("Building")) {
                    obj = Factory.createBuilding(Assets.getBuildings()[Integer
                            .parseInt(name.substring(name.indexOf("g") + 1,
                                    name.length())) - 1], 0, 0);
                }
                else if(name.equals("Road")) {
                    obj = new Road(0, 0, 0, 0, LevelProperties.WIDTH, Assets.road.getRegionHeight() * 2);
                    level.getPlayer().setGround(((Road) obj).getBoundingRectangle());
                }
                else if(name.equals("EnemySpawn")) {
                    obj = new Rectangle();
                }
                else if(name.equals("Base")) {
                    obj = new Base(Assets.playerBase, 0, 0);
                }
//                else if(name.equals("Plane")) {
//                    obj = Factory.createPlayer();
//                    ((GameObject) obj).setRotation(5f);
//                }
                
                for(Entry<String, String> entry : child2.getAttributes()) {
                    switch(entry.key) {
                    case "x":
                        float x = Float.parseFloat(entry.value);
                        
                        if(obj instanceof GameObject) {
                            ((GameObject) obj).setX(x);
                        }
                        else if(obj instanceof Rectangle) {
                            ((Rectangle) obj).setX(x);
                        }
                        
                        break;
                    case "y":
                        float y = AppData.height - Float.parseFloat(entry.value);
                        
                        if(obj instanceof GameObject) {
                            ((GameObject) obj).setY(y);
                        }
                        else if(obj instanceof Rectangle) {
                            ((Rectangle) obj).setY(y);
                        }
                        
                        break;
                    case "w":
                        if(obj instanceof Rectangle) {
                            ((Rectangle) obj).setWidth(Integer.parseInt(entry.value));
                        }
                        break;
                    case "h":
                        if(obj instanceof Rectangle) {
                            ((Rectangle) obj).setHeight(Integer.parseInt(entry.value));
                        }
                        break;
                    case "scrollable":
                        if(entry.value.equals("true")) {
                            ((GameObject) obj).setScrollable(true);
                        }
                        break;
                    case "scrollX":
                        ((GameObject) obj).setScrollX(Float.parseFloat(entry.value));
                        break;
                    case "scrollY":
                        ((GameObject) obj).setScrollY(Float.parseFloat(entry.value));
                    }
                    
                }
                
                if(obj instanceof GameObject) {
                    parsed.addObject((GameObject) obj);
                }
                else if(obj instanceof Rectangle) {
                    LevelProperties.enemySpawns.add((Rectangle) obj);
                }
            }
            
            level.addLayer(parsed);
        }
        
        level.setSpawnTime(root.getFloat("zombieSpawnTime"));
    }
}
