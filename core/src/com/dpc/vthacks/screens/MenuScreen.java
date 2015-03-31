package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class MenuScreen implements Screen {
    private Sprite menu;
    private App context;
    private GameCamera camera;
    private boolean loading;
    private Stage stage;
    private Label play, shop;
    private Label footer;
    private Image background;
    private Image initialBackground;
    private Label initialLabel;
    private static boolean tappedOnce;
    
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

        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        if(!tappedOnce) {
            initialLabel = new Label("touch anywhere to continue", Assets.labelStyle);
            initialLabel.addAction(Actions.forever(sequence(alpha(0, 1), alpha(1, 1))));

            initialBackground = new Image(Assets.menuBackground);
            initialBackground.setSize(AppData.width, AppData.height);
            initialBackground.addAction(sequence(alpha(0.5f), alpha(1, 0.5f)));
            
            stage.addActor(initialBackground);
        }
        
        
        background = new Image(Assets.menuBackground);
        background.setSize(AppData.width, AppData.height);
        background.addAction(sequence(alpha(0.5f), alpha(1, 0.5f)));
        
        footer = new Label("Made with love at VT Hacks", Assets.labelStyle);
        footer.setColor(0.5f, 0, 0, 1);
        
        play = new Label("Play", Assets.labelStyle);
        
        play.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new WeaponSelectionScreen(context));
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        play.setColor(Assets.RED);
        
        shop = new Label("Store", Assets.labelStyle);
        shop.setColor(Assets.RED);
        
        shop.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new StoreScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        Table t = new Table();

        if(tappedOnce) {
            t.add(play);
            t.row();
        
            t.add(shop);
            
            t.setFillParent(true);
            
            t.center();
            t.right();
        }
        else {
            t.add(initialLabel);
            t.setFillParent(true);
            t.center();
            t.bottom();
        }
        
        //t.add(footer);

        
        
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
        
        if(!tappedOnce) {
            if(Gdx.input.isTouched()) {
                tappedOnce = true;
                initialLabel.addAction(alpha(0));
                stage.dispose();
                show();
            }
        }
        else {
            if(loading && Assets.lsUpdateRender(context)) {
                loading = false;
                Assets.getGameResources();
                //context.setScreen(new GameScreen(context));
            }
        }
        
        stage.act(delta);
        stage.draw();
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
