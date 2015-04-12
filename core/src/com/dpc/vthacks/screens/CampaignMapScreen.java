package com.dpc.vthacks.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.dpc.vthacks.AndroidCamera;
import com.dpc.vthacks.App;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.CampaignData;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.level.LevelManager;

public class CampaignMapScreen implements Screen {
    private Stage stage;
    private App context;
    private Table t;
    private Image background;
    private int selectedLevel;
    private boolean loading;
    
    public CampaignMapScreen(App context) {
        this.context = context;
    }
    
    @Override
    public void show() {
        Assets.allocateCampaignMapScreen();
        CampaignData.initialize();
        
        Array<Image> points = null;
        
        try {
            points = Parser.parseCampaignMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        stage = new Stage(new ScalingViewport(Scaling.stretch, 
                AppData.width,
                AppData.height,
                new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT)),
                App.batch);
        
        Label back = new Label("Back", Assets.aerialLabelStyle);
        
        back.setX(back.getWidth() * 0.2f);
        back.setY(AppData.TARGET_HEIGHT - back.getHeight());
        
        back.setColor(Assets.RED);
        
        back.addListener(new InputListener() {
           
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.setScreen(new ModeSelectionScreen(context));

                return true;
            }
            
        });
        
        Label go = new Label("Go!", Assets.aerialLabelStyle);
        
        go.setColor(Assets.RED);
        
        go.addListener(new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                Assets.loadLoadingScreenTextures();
                loading = true;
                
                return true;
            }
            
        });
        
        Image map = new Image(Assets.campaignMap);

        t = new Table();
        t.setLayoutEnabled(false);
        
        t.add(map);
        
        for(final Image point : points) {
            
            point.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(CampaignData.getCurrentLevel() >= (Integer) point.getUserObject()) {
                        System.out.println("you can do this level");
                        selectedLevel = (Integer) point.getUserObject();
                        Assets.loadLoadingScreenTextures();
                        loading = true;
                    }
                    else {
                        point.addAction(repeat(2, sequence(
                                moveBy(20, 0, 0.1f),
                                moveBy(-20, 0, 0.1f))));
                    }
                    
                    return true;
                }
            });
            
            t.add(point);
        }
        
        t.pack();
        
        final ScrollPane bgScroll = new ScrollPane(t);
        
        bgScroll.setWidth(AppData.TARGET_WIDTH * 1f);
        bgScroll.setHeight(AppData.TARGET_HEIGHT * 1f);
        
        float centerX = AppData.TARGET_WIDTH * 0.5f;
        float centerY = AppData.TARGET_HEIGHT * 0.5f;
                
        bgScroll.setX(centerX - (bgScroll.getWidth() * 0.5f));
        bgScroll.setY(centerY - (bgScroll.getHeight() * 0.5f));

        bgScroll.setScrollingDisabled(true, true);

        background = new Image(Assets.menuBackground);
        background.setWidth(AppData.TARGET_WIDTH);
        background.setHeight(AppData.TARGET_HEIGHT);
        
        stage.addActor(background);
        stage.addActor(bgScroll);
        
        
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
                                             selectedLevel + "", 
                                             LevelManager.CAMPAIGN_MODE));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT));
        t.invalidateHierarchy();
        t.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT); 
        background.setSize(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);
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
        Assets.deallocateCampaignMapScreen();
        stage.dispose();
    }
    
}
