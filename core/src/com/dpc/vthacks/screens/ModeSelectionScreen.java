package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.level.Level;

public class ModeSelectionScreen implements Screen {
    private Stage stage;
    private Image campaign;
    private Image waves;
    private App context;
    
    public ModeSelectionScreen(App context) {
        this.context = context;
    }

    @Override
    public void show() {
        Assets.allocateModeSelectionScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        final Label next = new Label("Next", Assets.labelStyle);
        
        next.setX(AppData.width - next.getWidth());
   
        next.addListener(new InputListener() {
          
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new LevelSelectionScreen(context));
                
                return true;
            }
            
        });
        
        next.setColor(Assets.RED);
        
        final Label description = new Label("Select a mode", Assets.labelStyle);
        
        final Label back = new Label("Back", Assets.labelStyle);
        
        final ScrollPane sp = new ScrollPane(description);

        description.setWrap(true);
        
        back.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new WeaponSelectionScreen(context));
                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        back.setX(back.getWidth() * 0.2f);
        back.setY(AppData.height - back.getHeight());
        back.setColor(Assets.RED);
        
        campaign = new Image(Assets.campaignPreview);
        waves = new Image(Assets.endlessWavesPreview);
        
        campaign.setWidth(campaign.getWidth() * 0.5f);
        waves.setWidth(waves.getWidth() * 0.5f);
        
        campaign.setHeight(campaign.getHeight() * 0.5f);
        waves.setHeight(waves.getHeight() * 0.5f);
        
//        campaign.setWidth(AppData.width * 0.25f);
//        waves.setWidth(AppData.width * 0.25f);
//        campaign.setHeight(AppData.height * 0.25f);
//        waves.setHeight(AppData.height * 0.25f);
//        
        final ObjectMap<String, String> modes = Parser.parseGameModes();
        
        final float centerX = AppData.width * 0.5f;
        final float centerY = AppData.height * 0.5f;
        
        
        campaign.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                campaign.addAction(parallel(alpha(1, 0.25f)));
                waves.addAction(parallel(alpha(0.25f, 0.25f)));
                back.addAction(alpha(0.25f, 0.25f));
                next.addAction(alpha(0.25f, 0.25f));
                
                description.setText(modes.get(Level.CAMPAIGN_MODE));
                
                return true;
            }
            
        });
        
        waves.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                waves.addAction(parallel(alpha(1, 0.25f)));
                campaign.addAction(parallel(alpha(0.25f, 0.25f)));
                back.addAction(alpha(0.25f, 0.25f));
                next.addAction(alpha(0.25f, 0.25f));
                
                description.setText(modes.get(Level.WAVES_MODE));
                sp.setPosition(centerX - (sp.getWidth() * 0.5f), 0);
                
                return true;
            }
            
        });

        waves.setPosition(centerX - waves.getWidth(), 
                          centerY - (waves.getHeight() * 0.5f));
        
        campaign.setPosition(centerX,
                             centerY - (campaign.getHeight() * 0.5f));
        
        sp.setWidth(AppData.width);
        sp.setPosition(centerX - (sp.getWidth() * 0.5f), 0);
        
        stage.addActor(back);
        stage.addActor(waves);
        stage.addActor(campaign);
        stage.addActor(sp);
        stage.addActor(next);
        
        stage.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                // If a tab is pressed, don't refocus the stage
                if(((x > campaign.getX() && x < campaign.getX() + campaign.getWidth() &&
                   y > campaign.getY() && y < campaign.getY() + campaign.getHeight()) ||
                   (x > waves.getX() && x < waves.getX() + waves.getWidth() && 
                   y < waves.getY() + waves.getHeight()) || (x > sp.getX() &&
                   x < sp.getX() + sp.getWidth() && y > sp.getY() && y < sp.getY() + sp.getHeight()))) {
                    return false;
                }
                
                // "Refocus" the stage
                back.addAction(alpha(1, 0.25f));
                next.addAction(alpha(1, 0.25f));
                campaign.addAction(alpha(1, 0.25f));
                waves.addAction(alpha(1, 0.25f));
                description.setText("Select a mode");
                sp.setPosition(centerX - (sp.getWidth() * 0.5f), 0);
                
                return super.touchDown(event, x, y, pointer, button);
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
        stage.dispose();
        Assets.deallocateModeSelectionScreen();
    }
}
