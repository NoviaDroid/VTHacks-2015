package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class WeaponSelectionScreen implements Screen {
    private Stage stage;
    private ImageButton[] selectedWeapons;
    private Skin weaponIcons;
    
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        weaponIcons = new Skin(Assets.weaponIconAtlas);
        
        selectedWeapons = new ImageButton[2];
        
        for(int i = 0; i < selectedWeapons.length; i++) {
      //      selectedWeapons[i] = new ImageButton(weaponIcons.getDrawable());
            
            selectedWeapons[i].addListener(new InputListener() {
                
            });
        }
        
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
        stage.getCamera().update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
    
}
