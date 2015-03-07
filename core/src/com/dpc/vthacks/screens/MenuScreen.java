package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.properties.WeaponManager;

public class MenuScreen implements Screen {
    private Sprite menu;
    private App context;
    private GameCamera camera;
    private boolean loading;
    
    public MenuScreen(App app) {
        context = app;
        
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                
                Assets.loadLoadingScreenTextures();
                loading = true;
                
                return super.touchDown(screenX, screenY, pointer, button);
            }
            
            @Override
            public boolean keyDown(int keycode) {
                context.setScreen(new StoreScreen(context));
                
                return super.keyDown(keycode);
            }
        });
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

        menu.draw(App.batch);

        App.batch.end();

        if(loading && Assets.lsUpdateRender(context)) {
            Assets.getGameTextures();
            context.setScreen(new GameScreen(context));
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
