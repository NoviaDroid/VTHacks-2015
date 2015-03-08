package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.PagedScrollPane;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Fonts;

public class LevelSelectionScreen implements Screen {
    private Table levelTable;
    private LabelStyle labelStyle;
    private Stage stage;
    private App context;
    
    public LevelSelectionScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        levelTable = new Table();
        
        labelStyle = new LabelStyle();
        labelStyle.font = Fonts.getZombie();
        
        float w = labelStyle.font.getBounds("label").width * 2;
        
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        levelTable.add(new Label("label ", labelStyle)).width(w);
        
        levelTable.left();
        levelTable.bottom();
        
        PagedScrollPane scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(25);

        scroll.addPage(levelTable);
        
        stage.addActor(scroll);
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
    
}
