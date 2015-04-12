package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.dpc.vthacks.AndroidCamera;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class MenuScreen implements Screen {
    private Sprite menu;
    private App context;
    private AndroidCamera camera;
    private boolean loading;
    private Stage stage;
    private Label play, shop;
    private Label footer;
    private Image background;
    private static boolean tappedOnce;
    private Table t;
    
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

        stage = new Stage(new ScalingViewport(Scaling.stretch, 
                AppData.width,
                AppData.height,
                new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT)),
                App.batch);
        
        background = new Image(Assets.menuBackground);
        background.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);
        background.addAction(sequence(alpha(0.5f), alpha(1, 0.5f)));
        
        footer = new Label("Made with love at VT Hacks", Assets.storeLabelStyle);
        footer.setColor(0.5f, 0, 0, 1);
        
        play = new Label("Play", Assets.storeLabelStyle);
        
        play.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new WeaponSelectionScreen(context));
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        play.setColor(Assets.RED);
        
        shop = new Label("Store", Assets.storeLabelStyle);
        shop.setColor(Assets.RED);
        
        shop.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new StoreScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        Label settings = new Label("Settings", Assets.storeLabelStyle);
        settings.setColor(Assets.RED);
        
        settings.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new SettingsScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        t = new Table();

        t.add(play);
        t.row();

        t.add(shop);
        
        t.row();
        
        t.add(settings);
        
        t.center();
        t.right();

        stage.addActor(background);

        stage.addActor(background);
        
        
        stage.getViewport().setCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT));
        t.invalidateHierarchy();
        t.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT); 
        
        stage.addActor(t);
        
        
        menu = new Sprite(Assets.menuBackground);
        menu.setPosition(0, 0);
        menu.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (loading && Assets.lsUpdateRender(context)) {
            loading = false;
            Assets.getGameResources();
            // context.setScreen(new GameScreen(context));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT));
        t.invalidateHierarchy();
        t.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT); 
        background.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);
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
