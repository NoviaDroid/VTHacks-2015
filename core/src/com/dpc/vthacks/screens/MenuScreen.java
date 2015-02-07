package com.dpc.vthacks.screens;

import com.badlogic.gdx.Screen;
import com.dpc.vthacks.data.Assets;

public class MenuScreen implements Screen {

    @Override
    public void show() {
        Assets.loadSkins();
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {
        Assets.loadSkins();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Assets.unloadSkins();
    }

}
