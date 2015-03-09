package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.data.JSONManager;

public class MenuScreen implements Screen {
    private Sprite menu;
    private App context;
    private GameCamera camera;
    private boolean loading;
    private Stage stage;
    private TextButton play, shop;
    private Label title;
    private Label footer;
    
    public MenuScreen(App app) {
        context = app;
        
        JSONManager.parseLevels();
        
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                
                Assets.loadLoadingScreenTextures();
                loading = true;
                
                return super.touchDown(screenX, screenY, pointer, button);
            }
            
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.S) {
                    context.setScreen(new StoreScreen(context));
                }
                else {
                    context.setScreen(new WeaponSelectionScreen(context));
                }
                
                return super.keyDown(keycode);
            }
        });
    }
    
    @Override
    public void show() {
        Assets.loadMenu();
        
        TextButtonStyle style = new TextButtonStyle();
        style.font = Fonts.getZombieSmall();
        
        LabelStyle lst = new LabelStyle();
        lst.font = Fonts.getZombieXSmall();
        
        footer = new Label("Made with love at VT Hacks", lst);
        footer.setColor(0.5f, 0, 0, 1);
        
        lst.font = Fonts.getZombie();
        
        title = new Label("Strafer", lst);
        
        play = new TextButton("Play", style);
        
        play.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new WeaponSelectionScreen(context));
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        shop = new TextButton("Store", style);
        
        shop.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new StoreScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        Table t = new Table();
        
        t.add(title);
        
        t.row();
        t.row();
        
        t.add(play);
        t.row();
        
        t.add(shop);
        
        t.row();
        t.row();
        t.add(footer);
        
        t.setFillParent(true);
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        stage.addActor(t);
        
        camera = new GameCamera();
        
        menu = new Sprite(Assets.menuBackground);
        menu.setPosition(0, 0);
        menu.setSize(AppData.width, AppData.height);
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
//        App.batch.setProjectionMatrix(camera.combined);
//        
//        App.batch.begin();
//
//        menu.draw(App.batch);
//
//        App.batch.end();

        stage.act(delta);
        stage.draw();
        
        if(loading && Assets.lsUpdateRender(context)) {
            Assets.getGameTextures();
            //context.setScreen(new GameScreen(context));
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
        stage.dispose();
    }

}
