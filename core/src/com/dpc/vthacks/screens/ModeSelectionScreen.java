package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.level.LevelManager;

public class ModeSelectionScreen implements Screen {
    private Stage stage;
    private Image campaign;
    private Image waves;
    private App context;
    private boolean modeSelected;
    private boolean wavesSelected;
    
    public ModeSelectionScreen(App context) {
        this.context = context;
    }

    @Override
    public void show() {
        Assets.allocateModeSelectionScreen();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height));
        
        
        final Label description = new Label("Select a mode", Assets.aerialLabelStyle);
        
        final Label back = new Label("Back", Assets.aerialLabelStyle);
        
        final ScrollPane sp = new ScrollPane(description);
        
        final Label next = new Label("Next", Assets.aerialLabelStyle);
        
        next.setX(AppData.width - next.getWidth());
   
        next.addListener(new InputListener() {
          
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!modeSelected) { 
                    sp.addAction(repeat(3, sequence(moveBy(5, 0, 0.05f), moveBy(-5, 0, 0.05f))));
                    
                    return true; 
                }
                
                if(wavesSelected) {
                    context.setScreen(new LevelSelectionScreen(context));
                }
                else {
                    context.setScreen(new CampaignMapScreen(context));
                }
                
                return true;
            }
            
        });
        
        next.setColor(Assets.RED);
        
        Image background = new Image(Assets.menuBackground);
        
        background.setWidth(AppData.width);
        background.setHeight(AppData.height);

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
        
        campaign.setWidth(AppData.width * 0.5f);
        waves.setWidth(AppData.width * 0.5f);
        
        campaign.setHeight(AppData.height * 0.5f);
        waves.setHeight(AppData.height * 0.5f);
        
        
        final ObjectMap<Integer, String> modes = Parser.parseGameModes();
        
        final float centerX = AppData.width * 0.5f;
        final float centerY = AppData.height * 0.5f;
        
        
        campaign.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                campaign.addAction(parallel(alpha(1, 0.25f)));
                waves.addAction(parallel(alpha(0.25f, 0.25f)));
                back.addAction(alpha(0.25f, 0.25f));
                next.addAction(alpha(1, 0.25f));
                
                description.setText(modes.get(LevelManager.CAMPAIGN_MODE));
                sp.setHeight(description.getHeight());
                
                modeSelected = true;
                wavesSelected = false;
                
                return true;
            }
            
        });
        
        waves.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                waves.addAction(parallel(alpha(1, 0.25f)));
                campaign.addAction(parallel(alpha(0.25f, 0.25f)));
                back.addAction(alpha(0.25f, 0.25f));
                next.addAction(alpha(1, 0.25f));
                
                description.setText(modes.get(LevelManager.ENDLESS_WAVES_MODE));
                sp.setHeight(description.getHeight());
                sp.setPosition(centerX - (sp.getWidth() * 0.5f), 0);
                
                modeSelected = true;
                wavesSelected = true;
                
                return true;
            }
            
        });

        waves.setPosition(centerX - waves.getWidth(), 
                          centerY - (waves.getHeight() * 0.5f));
        
        campaign.setPosition(centerX,
                             centerY - (campaign.getHeight() * 0.5f));
        
        next.addAction(alpha(0.5f, 0.25f));
        
        sp.setWidth(AppData.width);
        sp.setPosition(centerX - (sp.getWidth() * 0.5f), 0);
        sp.setHeight(description.getHeight());
        
        stage.addActor(background);
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
                   y < waves.getY() + waves.getHeight()))) {
                    return false;
                }
                
                // "Refocus" the stage
                wavesSelected = false;
                modeSelected = false;
                next.addAction(alpha(0.5f, 0.25f));
                
                back.addAction(alpha(1, 0.25f));
                campaign.addAction(alpha(1, 0.25f));
                waves.addAction(alpha(1, 0.25f));
                description.setText("Select a mode");
                sp.setHeight(description.getHeight());
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
