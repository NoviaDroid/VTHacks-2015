package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, bomb, road;
    public static TextureRegion[] buildings;
    public static TextureRegion[] skylines;
    public static AtlasRegion[] tankFrames;
    
    public static void loadSkins() {
        skinAtlas = new TextureAtlas(Gdx.files.internal("textures/SkinPack.pack"));
    }
    
    public static void loadGameTextures() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        
        buildings = new TextureRegion[5];
        
        skylines = new TextureRegion[4];
        
        tankFrames = new AtlasRegion[3];
        
        for(int i = 0; i < 5; i++) {
            buildings[i] = gameAtlas.findRegion("building" + (i + 1));
        }
        
        skylines[0] = gameAtlas.findRegion("skyline1");
        skylines[1] = gameAtlas.findRegion("skyline2");
        skylines[2] = gameAtlas.findRegion("skyline3");
        skylines[3] = gameAtlas.findRegion("skyline4");
        
        tankFrames[0] = gameAtlas.findRegion("tank1");
        tankFrames[1] = gameAtlas.findRegion("tank2");
        tankFrames[2] = gameAtlas.findRegion("tank3");
        
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
