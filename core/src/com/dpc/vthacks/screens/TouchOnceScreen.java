package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;

public class TouchOnceScreen implements Screen {
    private Stage stage;
    private App context;
    
    public TouchOnceScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        Assets.allocateTouchOnceScreen();
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        Image background = new Image(Assets.touchOnceScreenBackground);
        background.setWidth(AppData.width);
        background.setHeight(AppData.height);
        
        Label label = new Label("touch anywhere to start", Assets.aerialLabelStyle);
        label.addAction(Actions.forever(sequence(alpha(0, 1), alpha(1, 1))));
        
        Table t = new Table();
        
        t.add(label);
        t.bottom();
        t.setFillParent(true);

        stage.addActor(background);
        stage.addActor(t);
        
        stage.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                stage.addAction(sequence(fadeOut(0.25f), new Action() {

                    @Override
                    public boolean act(float delta) {
                        context.setScreen(new MenuScreen(context));
                        
                        return true;
                    }
                    
                }));
                
                return true;
            }
            
        });
 
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
        Assets.deallocateTouchOnceScreen();
    }

}
