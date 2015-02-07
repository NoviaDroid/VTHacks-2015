package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, bomb, road, background;
    public static TextureRegion[] buildings;
    public static TextureRegion[] skylines;
    public static AtlasRegion[] tankFrames;
    public static AtlasRegion[] soldierFrames;
    
    public static void loadSkins() {
        skinAtlas = new TextureAtlas(Gdx.files.internal("textures/SkinPack.pack"));
    }
    
    public static void loadGameTextures() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        
        buildings = new TextureRegion[5];
        
        skylines = new TextureRegion[2];
        
        tankFrames = new AtlasRegion[3];
        
        soldierFrames = new AtlasRegion[3];
        
        for(int i = 0; i < 5; i++) {
            buildings[i] = gameAtlas.findRegion("building" + (i + 1));
        }
        
        for(int i = 0; i < 2; i++) {
            skylines[i] = gameAtlas.findRegion("skyline" + (i + 1));
          //  skylines[i].getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        
        for(int i = 0; i < 3; i++) {
            tankFrames[i] = gameAtlas.findRegion("tank" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            soldierFrames[i] = gameAtlas.findRegion("troopRunning" + (i + 1));
        }
        
        background = gameAtlas.findRegion("background");
        plane = gameAtlas.findRegion("plane");
        bomb = gameAtlas.findRegion("bomb");
        road = gameAtlas.findRegion("road");
    }
    
    public static void unloadSkins() {
        skinAtlas.dispose();
    }
    
    public static void unloadGameTextures() {
        gameAtlas.dispose();
        plane.getTexture().dispose();
        road.getTexture().dispose();
        
        background.getTexture().dispose();
        
        for(TextureRegion b : buildings) {
            b.getTexture().dispose();
        }
        
        for(TextureRegion s : skylines) {
            s.getTexture().dispose();
        }
        
        for(TextureRegion t : tankFrames) {
            t.getTexture().dispose();
        }
    }
}
