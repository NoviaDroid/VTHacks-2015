package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.SpriteAnimation;

public class Assets {
    public static TextureAtlas skinAtlas, gameAtlas, zombieAtlas;
    public static ObjectMap<String, AdvancedSpriteAnimation> playerAnimationData;
    public static ObjectMap<String, SpriteAnimation> tankAnimations;
    public static ObjectMap<String, SpriteAnimation> zombieAnimations;
    
    public static TextureRegion ammoCrate;
    public static TextureRegion shotgun;
    public static TextureRegion plane, playerIcon, zombie, emptyPlane, bomb, road, background, tankShell, menuBackground, enemyBase, playerBase, healthbar, bullet;
    public static TextureRegion[] buildings, skylines;
    public static AtlasRegion[] tankFrames, playerStandingStillFrames, soldierFrames, enemySoldierFrames, enemyTankFrames, explosionFrames, planeFiringFrames, playerWalkFrames;
    public static TextureRegion barBackground, progressBar;
    public static TextureRegion playerStationary;
    public static AssetManager manager = new AssetManager();
    public static Sound explosion;
    public static Sound[] playerSounds;
    public static Music pressDown, pressUp, shot, strafe;
    public static Sound strafeEnd;
    public static float shotTimer;
    public static int loaded;
    public static TextureRegion healthBarBackground;
    public static Sound outOfAmmo;
        
    public static void loadSkins() {
        skinAtlas = new TextureAtlas("skinPack.pack");
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
        manager.load("Zombie1.pack", TextureAtlas.class);
        manager.load("gamePack.pack", TextureAtlas.class);
        manager.load("sounds/pressDown.wav", Music.class); 
        manager.load("sounds/pressUp.wav", Music.class);
        manager.load("sounds/explosion.wav", Sound.class);
        manager.load("sounds/shot.wav", Music.class);
        manager.load("sounds/strafe.wav", Music.class);
        manager.load("sounds/strafeEnd.wav", Sound.class);
        manager.load("sounds/shot1.wav", Sound.class);
        manager.load("sounds/shot2.wav", Sound.class);
        manager.load("sounds/shot3.wav", Sound.class);
        manager.load("sounds/outofammo.wav", Sound.class);
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
        zombieAtlas = manager.get("Zombie1.pack", TextureAtlas.class);
        playerSounds = new Sound[1];
        pressDown = manager.get("sounds/pressDown.wav", Music.class); 
        pressUp = manager.get("sounds/pressUp.wav", Music.class); 
        strafeEnd = manager.get("sounds/strafeEnd.wav", Sound.class);
        explosion = manager.get("sounds/explosion.wav", Sound.class); 
        shot = manager.get("sounds/shot.wav", Music.class);
        strafe = manager.get("sounds/strafe.wav", Music.class);
        outOfAmmo = manager.get("sounds/outofammo.wav", Sound.class);
        
        playerSounds[0] = manager.get("sounds/shot1.wav", Sound.class);
        //playerSounds[1] = manager.get("sounds/shot2.wav", Sound.class);
        ///playerSounds[2] = manager.get("sounds/shot3.wav", Sound.class);
        
        buildings = new AtlasRegion[6];
        skylines = new AtlasRegion[2];
        tankFrames = new AtlasRegion[3];
        soldierFrames = new AtlasRegion[3];
        enemySoldierFrames = new AtlasRegion[4];
        enemyTankFrames = new AtlasRegion[3];
        explosionFrames = new AtlasRegion[5];
        planeFiringFrames = new AtlasRegion[2];
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
            enemySoldierFrames[i] = gameAtlas.findRegion("ETank" + (i + 1));
        }

        for(int i = 0; i < 2; i++) {
            planeFiringFrames[i] = gameAtlas.findRegion("PlaneFiring" + (i + 1));
            planeFiringFrames[i].flip(true, false);
        }
        
        tankAnimations = new ObjectMap<String, SpriteAnimation>();
        
            
        TextureRegion[] tfFrames = new TextureRegion[5];
        for(int i = 0; i < 5; i++) {
            tfFrames[i] = gameAtlas.findRegion("Tankfire" + (i + 1));
        }

        tankAnimations.put("fire", new SpriteAnimation(tfFrames, 0.15f));
        tankAnimations.put("moving", new SpriteAnimation(tankFrames, 0.15f));
        
        shotgun = gameAtlas.findRegion("shotgun");
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
        ammoCrate = gameAtlas.findRegion("Ammo Crate");
        
        plane.flip(true, false);
      //  playerStationary.flip(true, false);
        
        zombieAnimations = new ObjectMap<String, SpriteAnimation>();
        
        TextureRegion[] zwf = new TextureRegion[zombieAtlas.getRegions().size];
        
        for(int i = 0; i < zwf.length; i++) {
           zwf[i] = zombieAtlas.getRegions().get(i);
        }
        
        TextureRegion[] zwf2 = new TextureRegion[zwf.length];
        
        for(int i = 0; i < zwf2.length; i++) {
            zwf2[i] = new TextureRegion(zwf[i]);
            zwf2[i].flip(true, false);
        }
        
        TextureRegion[] zwf3 = new TextureRegion[1];
        
        zwf3[0] = zombieAtlas.getRegions().get(0);
        
        zombieAnimations.put("walking-right", new SpriteAnimation(zwf, 0.25f));
        zombieAnimations.put("walking-left", new SpriteAnimation(zwf2, 0.25f));
        zombieAnimations.put("attacking", new SpriteAnimation(zwf3, 15));
    }
    
    public static void unloadSkins() {
       // skinAtlas.dispose();
        menuBackground.getTexture().dispose();
    }

    public static void unloadGameTextures() {
        manager.clear();
        manager.dispose();
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

    public static void playPressDown() {
        if(!pressDown.isPlaying()) {
            pressDown.play();
        }
    }
    
    public static void dispose() {
        explosion.dispose();
        strafe.dispose();
        shot.dispose();
        pressUp.dispose();
        pressDown.dispose();
        outOfAmmo.dispose();
        
        for(Sound s : playerSounds) {
            s.dispose();
        }
    }

    public static TextureRegion[] getBuildings() {
        return buildings;
    }

    public static void setBuildings(TextureRegion[] buildings) {
        Assets.buildings = buildings;
    }
}
