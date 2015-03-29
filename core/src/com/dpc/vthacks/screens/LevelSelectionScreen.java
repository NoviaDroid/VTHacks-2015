package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.PagedScrollPane;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.level.LevelProperties;

public class LevelSelectionScreen implements Screen {
    private Stage stage;
    private App context;
    private TextButton go;
    private PagedScrollPane scroll;
    private boolean loading;
    
    public LevelSelectionScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        Assets.allocateLevelSelectionScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));

        go = new TextButton("Go!", Assets.uiSkin);
        
        go.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               
                Assets.loadLoadingScreenTextures();
                loading = true;
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });

        Table wrapper = new Table();
        
        scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(AppData.width * 0.5f);

        // Blank
        scroll.addPage(new Label("", Assets.labelStyle));
        
        for(Entry<String, String> levels : LevelProperties.getLevels()) {
            Label label = new Label(levels.key, Assets.labelStyle);
            label.setUserObject(levels.value);
            scroll.addPage(label);
        }
        
        scroll.setCurrentActor(scroll.getContent().getChildren().get(1));
        
        // Blank
        scroll.addPage(new Label("", Assets.labelStyle));

        wrapper.center();
        
        wrapper.add(scroll);
        wrapper.row();
        
        wrapper.add(go);
        
        wrapper.setFillParent(true);
        
        stage.addActor(wrapper);
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        if(loading && Assets.lsUpdateRender(context)) {
            loading = false; 
            
            Assets.getGameResources();
            
            Assets.barBackground.getTexture().dispose();
            Assets.progressBar.getTexture().dispose();
            
            context.setScreen(new GameScreen(context, 
                                            ((Label) scroll.getCurrentActor()).getUserObject().toString(), 
                                            LevelProperties.ENDLESS_MODE));
        }
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
        Assets.deallocateLevelSelectionScreen();
        stage.dispose();
    }
    
}
