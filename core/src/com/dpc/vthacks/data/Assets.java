package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, bomb, road, background, tankShell, menuBackground, enemyBase, playerBase, healthbar, bullet;
    private static TextureRegion[] buildings;
    private static TextureRegion[] skylines;
    private static AtlasRegion[] tankFrames;
    private static AtlasRegion[] soldierFrames;
    private static AtlasRegion[] enemySoldierFrames;
    private static AtlasRegion[] enemyTankFrames;
    private static AtlasRegion[] explosionFrames;
    
    public static void loadSkins() {
       // skinAtlas = new TextureAtlas(Gdx.files.internal("textures/SkinPack.pack"));
        menuBackground = new TextureRegion(new Texture(Gdx.files.internal("textures/MenuScreen.png")));
    }

    public static void loadGameTextures(AssetManager manager) {
        gameAtlas = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        
        setBuildings(new TextureRegion[6]);
        
        setSkylines(new TextureRegion[2]);
        
        setTankFrames(new AtlasRegion[3]);
        
        setSoldierFrames(new AtlasRegion[3]);
        
        setEnemySoldierFrames(new AtlasRegion[4]);
        
        setEnemyTankFrames(new AtlasRegion[3]);
        
        setExplosionFrames(new AtlasRegion[5]);
        
        for(int i = 0; i < 5; i++) {
            getExplosionFrames()[i] = gameAtlas.findRegion("Explosion" + (i + 1));
        }
        
        for(int i = 0; i < 5; i++) {
            getBuildings()[i] = gameAtlas.findRegion("building" + (i + 1));
        }
        
        getBuildings()[5] = gameAtlas.findRegion("Apartment");
        
        for(int i = 0; i < 2; i++) {
            getSkylines()[i] = gameAtlas.findRegion("skyline" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            getTankFrames()[i] = gameAtlas.findRegion("tank" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            getSoldierFrames()[i] = gameAtlas.findRegion("troopRunning" + (i + 1));
        }
        
        for(int i = 0; i < 4; i++) {
            getEnemySoldierFrames()[i] = gameAtlas.findRegion("ETroop Running" + (i + 1));
        }
        
        for(int i = 0; i < 3; i++) {
            getEnemyTankFrames()[i] = gameAtlas.findRegion("ETank" + (i + 1));
        }

        healthbar = gameAtlas.findRegion("healthbar");
        bullet = gameAtlas.findRegion("Bullet");
        playerBase = gameAtlas.findRegion("barrack");
        enemyBase = gameAtlas.findRegion("Enemy Barrack");      
        tankShell = gameAtlas.findRegion("ETank Shell");
        background = gameAtlas.findRegion("background");
        plane = gameAtlas.findRegion("plane");
        bomb = gameAtlas.findRegion("bomb");
        road = gameAtlas.findRegion("road");
    }
    
    public static void unloadSkins() {
       // skinAtlas.dispose();
        menuBackground.getTexture().dispose();
    }
    
    public static void unloadGameTextures() {
        gameAtlas.dispose();
        plane.getTexture().dispose();
        road.getTexture().dispose();
        background.getTexture().dispose();
        tankShell.getTexture().dispose();
        
        playerBase.getTexture().dispose();
        enemyBase.getTexture().dispose();
        
        bullet.getTexture().dispose();
        healthbar.getTexture().dispose();
        
        for(TextureRegion t : getExplosionFrames()) {
            t.getTexture().dispose();
        }
        
        for(TextureRegion b : getEnemyTankFrames()) {
            b.getTexture().dispose();
        }
        
        for(TextureRegion b : getSoldierFrames()) {
            b.getTexture().dispose();
        }
        
        for(TextureRegion b : getBuildings()) {
            b.getTexture().dispose();
        }
        
        for(TextureRegion s : getSkylines()) {
            s.getTexture().dispose();
        }
        
        for(TextureRegion t : getTankFrames()) {
            t.getTexture().dispose();
        }
    }

    public static AtlasRegion[] getExplosionFrames() {
        return explosionFrames;
    }

    public static void setExplosionFrames(AtlasRegion[] explosionFrames) {
        Assets.explosionFrames = explosionFrames;
    }

    public static AtlasRegion[] getEnemyTankFrames() {
        return enemyTankFrames;
    }

    public static void setEnemyTankFrames(AtlasRegion[] enemyTankFrames) {
        Assets.enemyTankFrames = enemyTankFrames;
    }

    public static AtlasRegion[] getEnemySoldierFrames() {
        return enemySoldierFrames;
    }

    public static void setEnemySoldierFrames(AtlasRegion[] enemySoldierFrames) {
        Assets.enemySoldierFrames = enemySoldierFrames;
    }

    public static AtlasRegion[] getSoldierFrames() {
        return soldierFrames;
    }

    public static void setSoldierFrames(AtlasRegion[] soldierFrames) {
        Assets.soldierFrames = soldierFrames;
    }

    public static AtlasRegion[] getTankFrames() {
        return tankFrames;
    }

    public static void setTankFrames(AtlasRegion[] tankFrames) {
        Assets.tankFrames = tankFrames;
    }

    public static TextureRegion[] getSkylines() {
        return skylines;
    }

    public static void setSkylines(TextureRegion[] skylines) {
        Assets.skylines = skylines;
    }

    public static TextureRegion[] getBuildings() {
        return buildings;
    }

    public static void setBuildings(TextureRegion[] buildings) {
        Assets.buildings = buildings;
    }
}
