package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, bomb;
    
    public static void loadSkins() {
        skinAtlas = new TextureAtlas(Gdx.files.internal("textures/skinAtlas.pack"));
    }
    
    public static void loadGameTextures() {
        gameAtlas = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        
        plane = gameAtlas.findRegion("plane");
        bomb = gameAtlas.findRegion("bomb");
    }
    
    public static void unloadSkins() {
        skinAtlas.dispose();
    }
    
    public static void unloadGameTextures() {
        gameAtlas.dispose();
        plane.getTexture().dispose();
    }
}
