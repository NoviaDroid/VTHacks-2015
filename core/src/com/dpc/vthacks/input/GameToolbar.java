package com.dpc.vthacks.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class GameToolbar {
    private Stage stage;
    private Button bombButton, strafeButton, soldierButton, tankButton;
    
    public GameToolbar() {
        Assets.loadSkins();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        Skin skin = new Skin();
        skin.addRegions(Assets.getSkins());
        
        bombButton = new ImageButton(skin.getDrawable("BombButton"));
        strafeButton = new ImageButton(skin.getDrawable("StrafeButton"));
        soldierButton = new ImageButton(skin.getDrawable("BombButton"));
        tankButton = new ImageButton(skin.getDrawable("StrafeButton"));
        
        bombButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombButtonTouchUp();
            }
        });
        strafeButton.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchDown();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                strafeButtonTouchUp();
            }
        });

        
        Table t = new Table();
        
        t.setBounds(5, 5, bombButton.getWidth() + strafeButton.getWidth(), bombButton.getHeight());

        t.add(bombButton).pad(5);
        t.add(strafeButton).pad(5);
        
        stage.addActor(t);
    }
    
    public void draw() {
        stage.draw();
    }
    
    public void dispose() {
        stage.dispose();
    }
    
    public void soldierButtonTouchDown() {
        
    }
    
    public void tankButtonTouchDown() {
        
    }
    
    public void bombButtonTouchUp() {
        
    }
    
    public void bombButtonTouchDown() {
        
    }
    
    public void strafeButtonTouchUp() {
        
    }
    
    public void strafeButtonTouchDown() {
        
    }
    
    public Stage getStage() {
        return stage;
    }
}
