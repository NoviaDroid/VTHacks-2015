package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, bomb;
    public static TextureRegion[] buildings;
    
    public static void loadSkins() {
        skinAtlas = new TextureAtlas(Gdx.files.internal("textures/skinAtlas.pack"));
    }
    
    public static void loadGameTextures() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        
        buildings = new TextureRegion[4];
        
        for(int i = 0; i < 4; i++) {
            buildings[i] = gameAtlas.findRegion("building" + (i + 1));
        }
        
        plane = gameAtlas.findRegion("plane");
        bomb = gameAtlas.findRegion("bomb");
    }
    
    public static void unloadSkins() {
        skinAtlas.dispose();
    }
    
    public static void unloadGameTextures() {
        gameAtlas.dispose();
        plane.getTexture().dispose();
        
        for(TextureRegion b : buildings) {
            b.getTexture().dispose();
        }
    }
}
