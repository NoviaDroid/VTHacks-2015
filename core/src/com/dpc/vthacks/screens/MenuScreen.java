package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MenuScreen implements Screen {
    private Sprite menu;
    private App context;
    private GameCamera camera;
    private boolean loading;
    private Stage stage;
    private TextButton play, shop;
    private Label footer;
    private Image background;
    
    public MenuScreen(App app) {
        context = app;
        
        Gdx.input.setInputProcessor(new InputAdapter() {
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                
                Assets.loadLoadingScreenTextures();
                loading = true;
                
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }
    
    @Override
    public void show() {
        Assets.allocateMenuScreen();
        
        background = new Image(Assets.menuBackground);
        background.setSize(AppData.width, AppData.height);
        background.addAction(sequence(alpha(0.5f), alpha(1, 0.5f)));
        
        footer = new Label("Made with love at VT Hacks", Assets.labelStyle);
        footer.setColor(0.5f, 0, 0, 1);
        
        play = new TextButton("Play", Assets.uiSkin);
        
        play.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new WeaponSelectionScreen(context));
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        shop = new TextButton("Store", Assets.uiSkin);
        
        shop.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new StoreScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        Table t = new Table();

        t.add(play);
        t.row();
        
        t.add(shop);

        t.setFillParent(true);
        t.center();
        t.right();
        
        //t.add(footer);

        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        stage.addActor(background);
        
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
            loading = false;
            
            Assets.getGameResources();
            //context.setScreen(new GameScreen(context));
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Assets.deallocateMenuScreen();
        stage.dispose();
    }

}
