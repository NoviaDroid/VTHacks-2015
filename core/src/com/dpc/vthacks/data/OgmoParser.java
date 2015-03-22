package com.dpc.vthacks.data;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.objects.GameSprite;
import com.dpc.vthacks.objects.Road;

public class OgmoParser {
    
    public static void parse(String file, Level level) throws IOException {
        
        XmlReader reader = new XmlReader();
        
        Element root = reader.parse(Gdx.files.internal(file));
        
        Element child = null;
        Element child2 = null;
        GameObject obj = null;
        
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
                    case "zOrder": 
                        zOrder = Integer.parseInt(entry.value);
                    }

                    childObjects.add(obj);
                }
            }
            
            parsedLayers.add(childObjects);
        }
        
        level.setSpawnTime(root.getFloat("zombieSpawnTime"));
        level.setLayers(parsedLayers);
    }
}
