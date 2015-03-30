package com.dpc.vthacks.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {
    private Image splash;
    private Stage stage;
    private App context;
    
    public SplashScreen(App app) {
        this.context = app;
    }

    @Override
    public void show() {
        Assets.allocateSplashScreen();
        
        splash = new Image(Assets.menuBackground);
        splash.setSize(AppData.width, AppData.height);
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        stage.addActor(splash);
        
        splash.addAction(sequence(fadeOut(0), fadeIn(1), delay(1), fadeOut(1), new Action() {

            @Override
            public boolean act(float delta) {
                context.setScreen(new MenuScreen(context));
                
                return true;
            }
            
        }));
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        Assets.deallocateSplashScreen();
        stage.dispose();
    }
    
}
