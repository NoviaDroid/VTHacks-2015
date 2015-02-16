package com.dpc.vthacks.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpc.vthacks.App;

public class AssetLoader {
    private AssetManager manager;
    private TextureRegion barBackground, progressBar;
    private int loaded;
    
    public AssetLoader() {
        manager = new AssetManager();
        
        manager.load("textures/loading/barBackground.png", TextureRegion.class);
        manager.load("textures/loading/barForeground.png", TextureRegion.class);
        
        manager.finishLoading();
        
        barBackground = manager.get("textures/loading/barBackground.png");
        progressBar =  manager.get("textures/loading/barForeground.png");
//        
//        Assets.loadGameTextures(manager);
    }
    
    public boolean update() {
        return manager.update();
    }
    
    public void render() {
        App.batch.draw(barBackground, 
                      (AppData.width * 0.5f) - (progressBar.getRegionWidth() * 0.5f),
                      (AppData.height * 0.5f) - (progressBar.getRegionHeight() * 0.5f));
        
        App.batch.draw(progressBar,
                      (AppData.width * 0.5f) - (progressBar.getRegionWidth() * 0.5f),
                      (AppData.height * 0.5f) - (progressBar.getRegionHeight() * 0.5f),
                      progressBar.getRegionWidth() * manager.getProgress(), progressBar.getRegionHeight());
    }
}
