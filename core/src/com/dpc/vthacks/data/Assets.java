package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.dpc.vthacks.App;
import com.dpc.vthacks.animation.AdvancedSpriteAnimation;
import com.dpc.vthacks.animation.FrameData;
import com.dpc.vthacks.animation.SpriteAnimation;

public class Assets {
    // Menu screen assets
    public static TextureRegion menuBackground;
    
    // Store screen assets
    
    // Weapon selection screen assets
    
    public static TextureRegion gameBackground;
    
    // Level selection screen assets
    public static final Color GREEN = new Color(0, 0.4f, 0, 1);
    public static final Color RED = new Color(0.85f, 0.05f, 0.24f, 1);
    public static TextureRegion campaignPreview;
    public static TextureRegion endlessWavesPreview;
    public static TextureRegion touchOnceScreenBackground;
    public static TextureRegion campaignMap;
    public static BitmapFont aerialStore;
    public static LabelStyle storeLabelStyle;
    
    // Game screen assets
    public static final String SHOT1 = "shot1";
    public static final String SHOT2 = "shot2";
    public static final String SHOT3 = "shot3";
    public static final String WAVE_UP = "wave up";
    public static final String OUT_OF_AMMO = "outofammo";
    public static ObjectMap<String, Sound> sounds;
    public static ObjectMap<String, AdvancedSpriteAnimation> playerAnimations;
    public static ObjectMap<String, SpriteAnimation> zombieAnimations;
    public static AssetManager manager = new AssetManager();
    public static TextureAtlas skinAtlas, storeAtlas, weaponIconAtlas, gameAtlas, zombieAtlas;
    public static TextureRegion[] buildings, skylines;
    public static TextureRegion  playerIcon, barBackground, road, background, 
                                 healthbar, ammoCrate, healthBarBackground;
    public static TextureRegion splashLogo;
    public static TextureRegion campaignMapPoint;
    
    // Loading screen textures
    public static TextureRegion progressBar;
    
    // Shared assets
    public static Skin uiSkin;
    public static BitmapFont leviBrush;
    public static BitmapFont roboto;
    public static BitmapFont aerial;
    public static TextButtonStyle textButtonStyle;
    public static LabelStyle leviBrushLabelStyle;
    public static LabelStyle aerialLabelStyle;
    public static LabelStyle robotoLabelStyle;
    private static boolean fontsLoaded;
    
    public static TextureRegion[] shallowCopy(TextureRegion[] input) {
        TextureRegion[] output = new TextureRegion[input.length];
        
        for(int i = 0; i < input.length; i++) {
            output[i] = new TextureRegion(input[i]);
        }
        
        return output;
    }

    public static void flip(TextureRegion[] input, boolean x, boolean y) {
        for(int i = 0; i < input.length; i++) {
            input[i].flip(x, y);
        }
    }
    
    public static TextureRegion[] copyFlip(TextureRegion[] input, boolean x, boolean y) {
        input = shallowCopy(input);
        flip(input, x, y);
        
        return input;
    }
    
    public static void allocateMenuScreen() {
        System.out.println("allocateMenuScreen()");

        
        if(!manager.isLoaded("uiskin.json")) {
            manager.load("uiskin.json", Skin.class);
        }
        
        if(!manager.isLoaded("menuBackground.png")) {
            manager.load("menuBackground.png", Texture.class);
        }

        manager.finishLoading();
        
        menuBackground = new TextureRegion(manager.get("menuBackground.png", Texture.class));
        
        uiSkin = manager.get("uiskin.json", Skin.class);    
    }
    
    public static void deallocateMenuScreen() {
        System.out.println("deallocateMenuScreen()");
    }
    
    public static void allocateStoreScreen() {
        System.out.println("allocateStoreScreen()");
        manager.load("weaponIconPack.pack", TextureAtlas.class);
        
        manager.finishLoading();
        
        weaponIconAtlas = manager.get("weaponIconPack.pack", TextureAtlas.class);
    }
    
    public static void allocateTouchOnceScreen() {
        if(!manager.isLoaded("Metro Z new.jpg")) {
            manager.load("Metro Z new.jpg", Texture.class);
        }
        
        manager.finishLoading();
        
        touchOnceScreenBackground = new TextureRegion(manager.get("Metro Z new.jpg", Texture.class));
        
        if(!fontsLoaded) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("fonts/LEVIBRUSH.TTF"));
            
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    
            parameter.size = Gdx.graphics.getWidth() * 30 / AppData.TARGET_HEIGHT;
    
            leviBrush = generator.generateFont(parameter);
    
            
            generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));
            
            parameter.size = Gdx.graphics.getWidth() * 45 / AppData.TARGET_HEIGHT;
            
            roboto = generator.generateFont(parameter);
    
            generator = new FreeTypeFontGenerator(
                    Gdx.files.internal("fonts/aerial.ttf"));
            
            parameter.size = Gdx.graphics.getWidth() * 32 / AppData.TARGET_HEIGHT;
            
            aerial = generator.generateFont(parameter);
    
            aerial.setFixedWidthGlyphs(":");
            
            parameter.size = Gdx.graphics.getWidth() * 24 / AppData.TARGET_HEIGHT;
            
            aerialStore = generator.generateFont(parameter);
            
            aerialStore.setFixedWidthGlyphs(":");
            
            generator.dispose();
    
            aerialLabelStyle = new LabelStyle();
            aerialLabelStyle.font = Assets.aerial;
            aerialLabelStyle.fontColor = Color.WHITE;
    
            storeLabelStyle = new LabelStyle();
            storeLabelStyle.font = Assets.aerialStore;
            storeLabelStyle.fontColor = Color.WHITE;
            
            robotoLabelStyle = new LabelStyle();
            robotoLabelStyle.font = Assets.roboto;
            robotoLabelStyle.fontColor = Color.WHITE;
            
            leviBrushLabelStyle = new LabelStyle();
            leviBrushLabelStyle.font = Assets.leviBrush;
            leviBrushLabelStyle.fontColor = Color.WHITE;
            
            textButtonStyle = new TextButtonStyle();
            textButtonStyle.font = Assets.leviBrush;
            
            fontsLoaded = true;
        }
    }
    
    public int fsize(){
        int a = AppData.TARGET_WIDTH * 4;
        int b = a/AppData.width;
        return b;
    }
    
    public static void deallocateFonts() {
        aerial.dispose();
        roboto.dispose();
        leviBrush.dispose();
        aerialStore.dispose();
    }
    
    public static void deallocateTouchOnceScreen() {
    }
    
    public static void deallocateStoreScreen() {
        System.out.println("deallocateStoreScreen()");
        manager.clear();
    }
    
    public static void allocateWeaponSelectionScreen() {
        System.out.println("allocateWeaponSelectionScreen()");
        
        if(!manager.isLoaded("weaponIconPack.pack")) {
            manager.load("weaponIconPack.pack", TextureAtlas.class);
        }
        
        if(!manager.isLoaded("playerIcon.png")) {
            manager.load("playerIcon.png", Texture.class);
        }
        
        manager.finishLoading();
        
        weaponIconAtlas = manager.get("weaponIconPack.pack", TextureAtlas.class);
        playerIcon = new TextureRegion(manager.get("playerIcon.png", Texture.class));
    }
    
    public static void deallocateWeaponSelectionScreen() {
        System.out.println("deallocateWeaponSelectionScreen()");
        weaponIconAtlas.dispose();
        manager.unload("weaponIconPack.pack");    
    }
    
    public static void allocateLevelSelectionScreen() {
        System.out.println("allocateLevelSelectionScreen()");
    }
    
    public static void deallocateLevelSelectionScreen() {
        System.out.println("deallocateLevelSelectionScreen()");
        uiSkin.dispose();
        menuBackground.getTexture().dispose();
        manager.unload("Metro Z new.jpg");
        manager.unload("uiskin.json");
        touchOnceScreenBackground.getTexture().dispose();
    }
    
    public static void allocateGameScreen() {
        System.out.println("allocateGameScreen()");

        manager.load("weaponIconPack.pack", TextureAtlas.class);
        
        gameBackground = new TextureRegion(new Texture(Gdx.files.internal("gameBackground.png")));
        
        manager.finishLoading();
        
        weaponIconAtlas = manager.get("weaponIconPack.pack", TextureAtlas.class);
        
        loadGameTextures();
    }
    
    public static void deallocateGameScreen() {
        System.out.println("deallocateGameScreen()");
        
        weaponIconAtlas.dispose();
        manager.unload("Zombie1.pack");
        manager.unload("gamePack.pack");
        manager.unload("sounds/shot1.wav");
        manager.unload("sounds/shot2.wav");
        manager.unload("sounds/shot3.wav");
        manager.unload("sounds/wave up.wav");
        manager.unload("sounds/outofammo.wav"); 
        manager.unload("skinPack.pack");

        gameBackground.getTexture().dispose();
        
        playerAnimations = new ObjectMap<String, AdvancedSpriteAnimation>();
        zombieAnimations = new ObjectMap<String, SpriteAnimation>();
      
        for(TextureRegion t : buildings) {
            t.getTexture().dispose();
        }
        
        for(TextureRegion t : skylines) {
            t.getTexture().dispose();
        }
        
        for(SpriteAnimation a : zombieAnimations.values()) {
            a.dispose();
        }
        
        for(AdvancedSpriteAnimation a : playerAnimations.values()) {
            a.dispose();
        }
        
        
        playerAnimations = null;
        zombieAnimations = null;
        sounds = null;
        
        gameAtlas.dispose();
        zombieAtlas.dispose();
        skinAtlas.dispose();
        
        healthBarBackground.getTexture().dispose();
        playerIcon.getTexture().dispose();
        healthbar.getTexture().dispose(); 
        background.getTexture().dispose();
        road.getTexture().dispose();
        ammoCrate.getTexture().dispose();
        manager.clear();
        manager.dispose();
        manager = new AssetManager();
    }

    public static void allocateSplashScreen() {
        System.out.println("allocateSplashScreen()");
        
        if(!manager.isLoaded("Metro Z new.jpg")) {
            manager.load("Metro Z new.jpg", Texture.class);
        }
        
        if(!manager.isLoaded("Pixel Prism.png")) {
            manager.load("Pixel Prism.png", Texture.class);
        }
 
        manager.finishLoading();
        
        splashLogo = new TextureRegion(manager.get("Pixel Prism.png", Texture.class));
        
        menuBackground = new TextureRegion(manager.get("Metro Z new.jpg", Texture.class));
    }
    
    public static void deallocateSplashScreen() {
        System.out.println("deallocateSplashScreen()");
        
        splashLogo.getTexture().dispose();
        splashLogo.getTexture().dispose();
        manager.unload("Metro Z new.jpg");
        manager.unload("Pixel Prism.png");
    }
    
    public static void allocateModeSelectionScreen() {
        if(!manager.isLoaded("endlessWavesPreview.png")) {
            manager.load("endlessWavesPreview.png", Texture.class);
        }
        
        if(!manager.isLoaded("campaignPreview.png")) {
            manager.load("campaignPreview.png", Texture.class);
        }
        
        manager.finishLoading();
        endlessWavesPreview = new TextureRegion(manager.get("endlessWavesPreview.png", Texture.class));
        campaignPreview = new TextureRegion(manager.get("campaignPreview.png", Texture.class));
    }
    
    public static void deallocateModeSelectionScreen() {
        endlessWavesPreview.getTexture().dispose();
        campaignPreview.getTexture().dispose();
        manager.unload("endlessWavesPreview.png");
        manager.unload("campaignPreview.png");
    }
    
    public static void loadLoadingScreenTextures() {
        manager.load("barBackground.png", Texture.class);
        manager.load("healthBar.png", Texture.class);
        
        manager.finishLoading();
        
        barBackground = new TextureRegion(manager.get("barBackground.png", Texture.class));
        progressBar = new TextureRegion(manager.get("healthBar.png", Texture.class));
        
        loadGameTextures();
    }
    
    public static void allocateCampaignMapScreen() {
        if(!manager.isLoaded("campaignMapPoint.png")) {
            manager.load("campaignMapPoint.png", Texture.class);
        }
        
        if(!manager.isLoaded("campaignMap.jpg")) {
            manager.load("campaignMap.jpg", Texture.class);
        }
        
        manager.finishLoading();
        
        campaignMapPoint = new TextureRegion(manager.get("campaignMapPoint.png", Texture.class));
        campaignMap = new TextureRegion(manager.get("campaignMap.jpg", Texture.class));
    }
    
    public static void deallocateCampaignMapScreen() {
        campaignMapPoint.getTexture().dispose();
       // manager.unload("campaignMapPoint.png");
    }
    
    public static void  allocateEndlessWavesLSScreen() {
        
    }
    
    public static void  deallocateEndlessWavesLSScreen() {
        
    }
    
    public static void loadGameTextures() {
        manager = new AssetManager();
        manager.load("Zombie1.pack", TextureAtlas.class);
        manager.load("gamePack.pack", TextureAtlas.class);
        manager.load("sounds/shot1.wav", Sound.class);
        manager.load("sounds/shot2.wav", Sound.class);
        manager.load("sounds/shot3.wav", Sound.class);
        manager.load("sounds/outofammo.wav", Sound.class); 
        manager.load("sounds/wave up.wav", Sound.class);
        manager.load("skinPack.pack", TextureAtlas.class);
    }
    
    public static AssetManager getManager() {
        return manager;
    }
   
    
    public static boolean lsUpdateRender(App app) {
        if(manager != null) {                App.batch.begin();
        
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
            if(manager.update()) {
                return true;
            }
            else {
                
            }
        }
        
        return false;
    }
    
    public static void deallocateAll() {
    }
    
    public static void getGameResources() {
        
        manager.finishLoading();
       
        
        sounds = new ObjectMap<String, Sound>();
        
        gameAtlas = manager.get("gamePack.pack", TextureAtlas.class);
        zombieAtlas = manager.get("Zombie1.pack", TextureAtlas.class);
        skinAtlas = manager.get("skinPack.pack", TextureAtlas.class);
        
        sounds.put(OUT_OF_AMMO, manager.get("sounds/outofammo.wav", Sound.class));
        sounds.put(SHOT1, manager.get("sounds/shot1.wav", Sound.class));
        sounds.put(SHOT2, manager.get("sounds/shot2.wav", Sound.class));
        sounds.put(SHOT3, manager.get("sounds/shot3.wav", Sound.class));
        sounds.put(WAVE_UP, manager.get("sounds/wave up.wav", Sound.class));
        
        int pri = 0;
        
        for(Sound s : sounds.values()) {
            s.setPriority(s.play(0), pri);
            
            pri++;
        }
        
        sounds.get(WAVE_UP).setPriority(sounds.get(WAVE_UP).play(0), sounds.size + 1);
        
        playerAnimations = new ObjectMap<String, AdvancedSpriteAnimation>();
        zombieAnimations = new ObjectMap<String, SpriteAnimation>();

        buildings = new AtlasRegion[6];
        skylines = new AtlasRegion[2];
        
        buildings = getFrames("building", 6, false);
        skylines = getFrames("skyline", 2, false);
        zombieAnimations.put("walking-right", getAnimation(zombieAtlas, 0.15f, false));
        zombieAnimations.put("walking-left", getAnimation(zombieAtlas, 0.15f, true));
        
        for(SpriteAnimation a : zombieAnimations.values()) {
            a.getAnimation().setPlayMode(PlayMode.LOOP);
        }
        
        healthBarBackground = gameAtlas.findRegion("barBackground");
        healthbar = gameAtlas.findRegion("healthbar");  
        background = gameAtlas.findRegion("background");
        road = gameAtlas.findRegion("road");
        ammoCrate = gameAtlas.findRegion("Ammo Crate");
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
}
