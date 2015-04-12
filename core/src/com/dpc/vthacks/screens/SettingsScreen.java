package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class SettingsScreen implements Screen {
    private App context;
    private Stage stage;
    private Table table;
    
    public SettingsScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(AppData.width, AppData.height), App.batch);
        
        Label back = new Label("Back", Assets.uiSkin);
        
        back.setPosition(back.getWidth() + 5, AppData.height - back.getHeight() - 5);
       
        back.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new MenuScreen(context));
                
                return true;
            }
            
        });
        
        final CheckBox useSound = new CheckBox("Enable Sound Effects", Assets.uiSkin);
        final CheckBox useRetroShader = new CheckBox("Enable Retro Shaders", Assets.uiSkin);
        final CheckBox useDynamicLights = new CheckBox("Enable Dynamic Lighting", Assets.uiSkin);
        
        useDynamicLights.setChecked(App.settings.isDynamicLightingEnabled());
        useRetroShader.setChecked(App.settings.isScanLineShaderEnabled());
        useSound.setChecked(App.settings.isAudioEnabled());
        
        useDynamicLights.addListener(new ChangeListener() {
            
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.settings.setDynamicLightingEnabled(useDynamicLights.isChecked());
            }
            
        });
        
        useSound.addListener(new ChangeListener() {
            
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.settings.setAudioEnabled(useSound.isChecked());
            }
            
        });
        
        
        useRetroShader.addListener(new ChangeListener() {
            
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.settings.setScanLineShaderEnabled(useRetroShader.isChecked());
            }
            
        });
        
        table = new Table();
        
        table.add(useSound).row();
        table.add(useRetroShader).row();
        table.add(useDynamicLights).row();
        
        table.setFillParent(true);
        
        stage.addActor(table);
        stage.addActor(back);
        
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }
    
}
