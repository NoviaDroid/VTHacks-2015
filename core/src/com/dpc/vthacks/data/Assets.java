package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, bomb, road, background, tankShell, enemyBase, playerBase;
    public static TextureRegion[] buildings;
    public static TextureRegion[] skylines;
    public static AtlasRegion[] tankFrames;
    public static AtlasRegion[] soldierFrames;
    public static AtlasRegion[] enemySoldierFrames;
    public static AtlasRegion[] enemyTankFrames;
    public static AtlasRegion[] explosionFrames;
    
    public static void loadSkins() {
        skinAtlas = new TextureAtlas(Gdx.files.internal("textures/SkinPack.pack"));
    }

    public static void loadGameTextures(AssetManager manager) {
        gameAtlas = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        
        buildings = new TextureRegion[6];
        
        skylines = new TextureRegion[2];
        
        tankFrames = new AtlasRegion[3];
        
        soldierFrames = new AtlasRegion[3];
        
        enemySoldierFrames = new AtlasRegion[4];
        
        enemyTankFrames = new AtlasRegion[3];
        
        explosionFrames = new AtlasRegion[5];
        
        for(int i = 0; i < 5; i++) {
            explosionFrames[i] = gameAtlas.findRegion("Explosion" + (i + 1));
        }
        
        for(int i = 0; i < 5; i++) {
            buildings[i] = gameAtlas.findRegion("building" + (i + 1));
        }
        
        buildings[5] = gameAtlas.findRegion("Apartment");
        
        for(int i = 0; i < 2; i++) {
            skylines[i] = gameAtlas.findRegion("skyline" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            tankFrames[i] = gameAtlas.findRegion("tank" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            soldierFrames[i] = gameAtlas.findRegion("troopRunning" + (i + 1));
        }
        
        for(int i = 0; i < 4; i++) {
            enemySoldierFrames[i] = gameAtlas.findRegion("ETroop Running" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            enemyTankFrames[i] = gameAtlas.findRegion("ETank" + (i + 1));
        }

        playerBase = gameAtlas.findRegion("barrack");
        enemyBase = gameAtlas.findRegion("Enemy Barrack");      
        tankShell = gameAtlas.findRegion("ETank Shell");
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
        tankShell.getTexture().dispose();
        
        playerBase.getTexture().dispose();
        enemyBase.getTexture().dispose();
        
        for(TextureRegion t : explosionFrames) {
            t.getTexture().dispose();
        }
        
        for(TextureRegion b : enemyTankFrames) {
            b.getTexture().dispose();
        }
        
        for(TextureRegion b : soldierFrames) {
            b.getTexture().dispose();
        }
        
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
