package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.AssetLoader;
import com.dpc.vthacks.data.Assets;

public class MenuScreen implements Screen {
    private Sprite menu;
    private App context;
    private GameCamera camera;
    private AssetLoader loader;
    
    public MenuScreen(App app) {
        context = app;
    }
    
    @Override
    public void show() {
        Assets.loadMenu();
        
        camera = new GameCamera();
        
        menu = new Sprite(Assets.menuBackground);
        menu.setPosition(0, 0);
        menu.setSize(AppData.width, AppData.height);
    }

    @Override
    public void render(float delta) {
        App.batch.setProjectionMatrix(camera.combined);
        
        App.batch.begin();
        
        if(loader == null) {
            menu.draw(App.batch);
        }
        else {
            if(loader.update()) {
                context.setScreen(new GameScreen());
            }
            
            loader.render();
        }
        
        App.batch.end();
        
        
        if(Gdx.input.isTouched()) {
            loader = new AssetLoader();
            
            context.setScreen(new GameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {
        Assets.loadMenu();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Assets.unloadSkins();
    }

}
