package com.dpc.vthacks.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class CampaignMapScreen implements Screen {
    private Stage stage;
    
    @Override
    public void show() {
        Assets.allocateCampaignMapScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
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
        Assets.deallocateCampaignMapScreen();
    }
    
}
