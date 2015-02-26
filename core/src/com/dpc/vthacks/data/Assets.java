package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;

public class Assets {
    private static TextureAtlas skinAtlas, gameAtlas;
    public static TextureRegion plane, playerIcon, zombie, emptyPlane, bomb, road, background, tankShell, menuBackground, enemyBase, playerBase, healthbar, bullet;
    private static TextureRegion[] buildings, skylines;
    private static AtlasRegion[] tankFrames, playerStandingStillFrames, soldierFrames, enemySoldierFrames, enemyTankFrames, explosionFrames, planeFiringFrames, playerWalkFrames;
    private static TextureRegion barBackground, progressBar;
    private static TextureRegion playerStationary;
    private static AssetManager manager = new AssetManager();
    private static Sound explosion;
    private static Music pressDown, pressUp, shot, strafe;
    private static Sound strafeEnd;
    private static float shotTimer;
    private static int loaded;
    public static TextureRegion healthBarBackground;
        
    public static void loadSkins() {
        skinAtlas = new TextureAtlas("skinPack.pack");
    }
    
    public static TextureAtlas getSkins() {
        return skinAtlas;
    }
    
    public static void loadMenu() {
        menuBackground = new TextureRegion(new Texture(Gdx.files.internal("MenuScreen.png")));
    }

    public static void loadLoadingScreenTextures() {
        manager.load("barBackground.png", Texture.class);
        manager.load("barForeground.png", Texture.class);
        
        manager.finishLoading();
        
        barBackground = new TextureRegion(manager.get("barBackground.png", Texture.class));
        progressBar = new TextureRegion(manager.get("barForeground.png", Texture.class));
        
        loadGameTextures();
    }
    
    public static void loadGameTextures() {
        manager = new AssetManager();
        manager.load("gamePack.pack", TextureAtlas.class);
        manager.load("sounds/pressDown.wav", Music.class); 
        manager.load("sounds/pressUp.wav", Music.class);
        manager.load("sounds/explosion.wav", Sound.class);
        manager.load("sounds/shot.wav", Music.class);
        manager.load("sounds/strafe.wav", Music.class);
        manager.load("sounds/strafeEnd.wav", Sound.class);
    }
    
    public static AssetManager getManager() {
        return manager;
    }
    
    public static boolean lsUpdateRender(App app) {
        if(manager != null) {
            if(manager.update()) {
                return true;
            }
            else {
                App.batch.begin();
                
                float x = (AppData.width * 0.5f) - (progressBar.getRegionWidth() * 0.5f);
                float y = (AppData.height * 0.5f) - (progressBar.getRegionHeight() * 0.5f);
                
                App.batch.draw(barBackground, 
                              x,
                              y);
                
                App.batch.draw(progressBar,
                              6 + x,
                              6 + y,
                              progressBar.getRegionWidth() * manager.getProgress(), progressBar.getRegionHeight());
                App.batch.end();
            }
        }
        
        return false;
    }
    
    public static void getGameTextures() {
        gameAtlas = manager.get("gamePack.pack", TextureAtlas.class);
        
        pressDown = manager.get("sounds/pressDown.wav", Music.class); 
        pressUp = manager.get("sounds/pressUp.wav", Music.class); 
        strafeEnd = manager.get("sounds/strafeEnd.wav", Sound.class);
        explosion = manager.get("sounds/explosion.wav", Sound.class); 
        shot = manager.get("sounds/shot.wav", Music.class);
        strafe = manager.get("sounds/strafe.wav", Music.class);
        
        setBuildings(new TextureRegion[6]);
        
        setSkylines(new TextureRegion[2]);
        
        setTankFrames(new AtlasRegion[3]);
        
        setSoldierFrames(new AtlasRegion[3]);
        
        setEnemySoldierFrames(new AtlasRegion[4]);
        
        setEnemyTankFrames(new AtlasRegion[3]);
        
        setExplosionFrames(new AtlasRegion[5]);
        
        setPlaneFiringFrames(new AtlasRegion[2]);
        
        playerStandingStillFrames = new AtlasRegion[3];
        
        playerWalkFrames = new AtlasRegion[3];
        
        for(int i = 0; i < 3; i++) {
            playerStandingStillFrames[i] = gameAtlas.findRegion("ETroopTier2Idle" + (i + 1));
            playerStandingStillFrames[i].flip(true, false);
        }
        
        for(int i = 0; i < 3; i++) {
            playerWalkFrames[i] = gameAtlas.findRegion("ETroop2Running" + (i + 1));
           playerWalkFrames[i].flip(true, false);
        }
        
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

        for(int i = 0; i < 2; i++) {
            planeFiringFrames[i] = gameAtlas.findRegion("PlaneFiring" + (i + 1));
            planeFiringFrames[i].flip(true, false);
        }
        
        healthBarBackground = gameAtlas.findRegion("barBackground");
        playerIcon = gameAtlas.findRegion("playerIcon1");
        zombie = gameAtlas.findRegion("zombie");
        playerStationary = gameAtlas.findRegion("ETroopTier2Idle1");
        emptyPlane = gameAtlas.findRegion("planeLatchOpen");
        plane = gameAtlas.findRegion("PlaneFiring");
        healthbar = gameAtlas.findRegion("healthbar");
        bullet = gameAtlas.findRegion("bullet");
        playerBase = gameAtlas.findRegion("Enemy Barrack");
        enemyBase = gameAtlas.findRegion("Enemy Barrack");      
        tankShell = gameAtlas.findRegion("ETank Shell");
        background = gameAtlas.findRegion("background");
        bomb = gameAtlas.findRegion("bomb");
        road = gameAtlas.findRegion("road");
        
        plane.flip(true, false);
      //  playerStationary.flip(true, false);
    }
    
    public static void unloadSkins() {
       // skinAtlas.dispose();
        menuBackground.getTexture().dispose();
    }
    
    public static AtlasRegion[] getPlaneFiringFrames() {
        return planeFiringFrames;
    }
    
    public static void setPlaneFiringFrames(AtlasRegion[] planeFiringFrames) {
        Assets.planeFiringFrames = planeFiringFrames;
    }
    
    public static void unloadGameTextures() {
        manager.clear();
        manager.dispose();
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

    public static TextureRegion getEmptyPlane() {
        return emptyPlane;
    }
    public static void setEmptyPlane(TextureRegion emptyPlane) {
        Assets.emptyPlane = emptyPlane;
    }
    public static AtlasRegion[] getTankFrames() {
        return tankFrames;
    }

    public static TextureRegion getPlayerIcon() {
        return playerIcon;
    }
    
    public static void setPlayerIcon(TextureRegion playerIcon) {
        Assets.playerIcon = playerIcon;
    }
    
    public static TextureRegion getPlane() {
        return plane;
    }
    
    public static void setPlane(TextureRegion plane) {
        Assets.plane = plane;
    }
    
    public static void setTankFrames(AtlasRegion[] tankFrames) {
        Assets.tankFrames = tankFrames;
    }

    public static TextureRegion[] getSkylines() {
        return skylines;
    }

    public static TextureRegion getPlayerStationary() {
        return playerStationary;
    }
    
    public static AtlasRegion[] getPlayerWalkFrames() {
        return playerWalkFrames;
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
    
    public static void playExplosion() {
        explosion.play();
    }
    
    public static void playPressDown() {
        if(!pressDown.isPlaying()) {
            pressDown.play();
        }
    }
    
    public static void playStrafe() {
        if(!strafe.isPlaying()) {
            //strafe.setOnCompletionListener(GameScreen.getLevel().getPlayer());
            strafe.play();
        }
    }
    
    public static void stopStrafe() {
        if(strafe.isPlaying()) {
            strafe.stop();
        }
    }
    
    public static void playPressUp() {
        if(!pressUp.isPlaying()) {
            pressUp.play();
        }
    }
    
    public static Music getStrafe() {
        return strafe;
    }
    
    public static TextureRegion getZombie() {
        return zombie;
    }
    
    public static void setZombie(TextureRegion zombie) {
        Assets.zombie = zombie;
    }
    
    public static void playStrafeEnd() {
        strafeEnd.play();
    }
    
    public static void stopStrafeEnd() {
        strafeEnd.stop();
    }
    
    public static void playShot() {
        shot.setVolume((float) Math.random() + 0.15f);
        
        if(!shot.isPlaying()) {
            shot.play();
        }
        
        shotTimer += Gdx.graphics.getDeltaTime();
        
        if(shotTimer >= 0.75f) {
            shot.stop();
            shotTimer = 0;
        }
    }

    public static AtlasRegion[] getPlayerStandingStillFrames() {
        return playerStandingStillFrames;
    }
    
    public static void dispose() {
        explosion.dispose();
        strafe.dispose();
        shot.dispose();
        pressUp.dispose();
        pressDown.dispose();
    }
}
