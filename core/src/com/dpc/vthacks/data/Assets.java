package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.animation.SpriteAnimation;

public class Assets {
    public static AssetManager manager = new AssetManager();
    public static TextureAtlas skinAtlas, storeAtlas, weaponIconAtlas, gameAtlas, zombieAtlas;
    
    public static ObjectMap<String, AdvancedSpriteAnimation> playerAnimations;
    public static ObjectMap<String, SpriteAnimation> tankAnimations;
    public static ObjectMap<String, SpriteAnimation> zombieAnimations;
    public static ObjectMap<String, SpriteAnimation> soldierAnimations;
    public static ObjectMap<String, SpriteAnimation> planeAnimations;
    public static ObjectMap<String, SpriteAnimation> explosionAnimations;
    
    public static TextureRegion[] buildings, skylines;

    public static TextureRegion  playerIcon, barBackground, progressBar, 
                                 zombie, bomb, road, background, 
                                 menuBackground, healthbar, ammoCrate;

    public static Sound[] playerSounds;
    public static Music pressDown, pressUp, shot, strafe;
    public static Sound strafeEnd, explosion;
    public static TextureRegion healthBarBackground;
    public static Sound outOfAmmo;
    public static float shotTimer;
    public static int loaded;
    
    public static void loadSkins() {
        skinAtlas = new TextureAtlas("skinPack.pack");
    }

    public static void loadMenu() {
        menuBackground = new TextureRegion(new Texture(Gdx.files.internal("MenuScreen.png")));
        storeAtlas = new TextureAtlas("storePack.pack");
        weaponIconAtlas = new TextureAtlas("weaponIconPack.pack");
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
        pressDown = manager.get("sounds/pressDown.wav", Music.class); 
        pressUp = manager.get("sounds/pressUp.wav", Music.class); 
        strafeEnd = manager.get("sounds/strafeEnd.wav", Sound.class);
        explosion = manager.get("sounds/explosion.wav", Sound.class); 
        shot = manager.get("sounds/shot.wav", Music.class);
        strafe = manager.get("sounds/strafe.wav", Music.class);
        outOfAmmo = manager.get("sounds/outofammo.wav", Sound.class);
        
        tankAnimations = new ObjectMap<String, SpriteAnimation>();
        playerAnimations = new ObjectMap<String, AdvancedSpriteAnimation>();
        zombieAnimations = new ObjectMap<String, SpriteAnimation>();
        soldierAnimations = new ObjectMap<String, SpriteAnimation>();
        planeAnimations = new ObjectMap<String, SpriteAnimation>();
        explosionAnimations = new ObjectMap<String, SpriteAnimation>();
        
        playerSounds = new Sound[1];
        buildings = new AtlasRegion[6];
        skylines = new AtlasRegion[2];
        
        buildings = getFrames("building", 6, false);
        skylines = getFrames("skyline", 2, false);
        explosionAnimations.put("explosion", getAnimation("Explosion", 5, 0.15f, false));
        soldierAnimations.put("running", getAnimation("troopRunning", 3, 0.15f, false));
        planeAnimations.put("firing", getAnimation("PlaneFiring", 2, 0.15f, true));     
        tankAnimations.put("moving", getAnimation("tank", 3, 0.15f, false));
        tankAnimations.put("firing", getAnimation("Tankfire", 5, 0.15f, false));
        zombieAnimations.put("walking-right", getAnimation(zombieAtlas, 0.15f, false));
        zombieAnimations.put("walking-left", getAnimation(zombieAtlas, 0.15f, true));
     //   zombieAnimations.put("attacking", )
        
        for(SpriteAnimation a : zombieAnimations.values()) {
            a.getAnimation().setPlayMode(PlayMode.LOOP);
        }
        
        healthBarBackground = gameAtlas.findRegion("barBackground");
        playerIcon = gameAtlas.findRegion("playerIcon1");
        zombie = gameAtlas.findRegion("zombie");
        healthbar = gameAtlas.findRegion("healthbar");  
        background = gameAtlas.findRegion("background");
        bomb = gameAtlas.findRegion("bomb");
        road = gameAtlas.findRegion("road");
        ammoCrate = gameAtlas.findRegion("Ammo Crate");
        
        playerSounds[0] = manager.get("sounds/shot1.wav", Sound.class);
    }
    
    public static AdvancedSpriteAnimation getAdvancedAnimation(JsonValue root, 
                                                               String child, 
                                                               int len, 
                                                               boolean flipX,
                                                               boolean flipY) {
        JsonValue w = root.getChild(child);

        FrameData[] frames = new FrameData[len];
        
        for(int i = 0; i < frames.length; i++) {
            JsonValue c = w.get(i).child;
    
            frames[i] = new FrameData(Assets.gameAtlas.findRegion(c.getString("img")),
                    c.getFloat("time"), c.getFloat("handOffsetX"),
                    c.getFloat("handOffsetY"));
            
            frames[i].getRegion().flip(flipX, flipY);
        
        }
        
        return new AdvancedSpriteAnimation(frames);
    }
    
    private static SpriteAnimation getAnimation(TextureAtlas atlas, float stateTime, boolean flip) {
        TextureRegion[] regions = new TextureRegion[atlas.getRegions().size];
        
        for(int i = 0; i < atlas.getRegions().size; i++) {
            regions[i] = new TextureRegion(atlas.getRegions().get(i));
            regions[i].flip(flip, false);
        }
        
        
        return new SpriteAnimation(regions, stateTime);
    }
    
    private static TextureRegion[] getFrames(String name, int iterations, boolean flip) {
        TextureRegion[] regions = new TextureRegion[iterations];
        
        for(int i = 0; i < iterations; i++) {
            regions[i] = gameAtlas.findRegion(name + (i + 1));
            regions[i].flip(flip, false);
        }
        
        return regions;
    }
    
    private static SpriteAnimation getAnimation(String name, int iterations, float stateTime, boolean flip) {
        TextureRegion[] regions = new TextureRegion[iterations];
        
        for(int i = 0; i < iterations; i++) {
            regions[i] = gameAtlas.findRegion(name + (i + 1));
            regions[i].flip(flip, false);
        }
        
        return new SpriteAnimation(regions, stateTime);
    }
    
    public static void unloadSkins() {
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
        storeAtlas.dispose();
        weaponIconAtlas.dispose();
        
        for(Sound s : playerSounds) {
            s.dispose();
        }
    }
}
