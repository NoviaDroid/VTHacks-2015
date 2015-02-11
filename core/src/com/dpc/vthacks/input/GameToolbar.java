package com.dpc.vthacks.input;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class GameToolbar {
    private Stage stage;
    private Button bombButton, strafeButton;
    
    public GameToolbar() {
        Assets.loadSkins();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
    }
}
