package com.dpc.vthacks.data;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.dpc.vthacks.LevelProperties;
import com.dpc.vthacks.Road;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.objects.GameSprite;

public class OgmoParser {
    
    public static Array<Array<GameObject>> parse(String level) throws IOException {
        
        XmlReader reader = new XmlReader();
        
        Element root = reader.parse(Gdx.files.internal(level));
        
        Element child = null;
        Element child2 = null;
        
        GameObject obj = null;
        
        int rootChildCount = root.getChildCount();
        
        Array<Array<GameObject>> objects = new Array<Array<GameObject>>(rootChildCount);
        
        // Not recursive because this XML file isn't complex..
        for(int i = 0; i < rootChildCount; i++) {
            child = root.getChild(i);
           
            int len = child.getChildCount();
            Array<GameObject> parsed = new Array<GameObject>(len);

            for(int j = 0; j < len; j++) {
                child2 = child.getChild(j);
                
                String name = child2.getName();

                if(name.startsWith("skyline")) {
                    TextureRegion tex = Assets.getSkylines()[Integer.parseInt(name.substring(name.indexOf("e") + 1, name.length())) - 1];
                    obj = new GameSprite(tex, 0, 0);
                    obj.setSize(LevelProperties.WIDTH, tex.getRegionHeight() * 3);
                }    
                else if(name.startsWith("Building")) {
                    obj = Factory.createBuilding(Assets.getBuildings()[Integer
                            .parseInt(name.substring(name.indexOf("g") + 1,
                                    name.length())) - 1], 0, 0);
                }
                else if(name.equals("Road")) {
                    obj = new Road(0, 0, 0, 0, Assets.road.getRegionWidth(), Assets.road.getRegionHeight());
                }
                
                for(Entry<String, String> entry : child2.getAttributes()) {
                    switch(entry.key) {
                    case "x":
                        obj.setX(Float.parseFloat(entry.value));
                        break;
                    case "y":
                        obj.setY(AppData.height - Float.parseFloat(entry.value));
                        break;
                    }
                }
                
                parsed.add(obj);
            }
            
            objects.add(parsed);
        }
        
        return objects;
    }
}
