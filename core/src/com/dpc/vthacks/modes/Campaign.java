package com.dpc.vthacks.modes;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.CampaignData;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.screens.MenuScreen;

/**
 * Game mode for endless wave play
 * @author Daniel Christopher
 * @version 3/14/15
 *
 */
public class Campaign extends Level {
    private static final String PATH = "campaign levels/campaign";
    private static final String EXTENSION = ".oel";
    private Stage dialogStage; // Game over dialog stage
    private InputProcessor mplex; // Saved off and put back as the input processor after play again touched
    private boolean isDialogOpen;
    private int levelID;
    private float survivalTime;
    private float elapsed;
    
    public Campaign(GameScreen context, int levelID) {
        super(context);
        
        this.levelID = levelID;
    }
    
    @Override
    public void loadLevels() {
        try {
            Parser.parseOgmoLevels(PATH + levelID + EXTENSION, this);
        } catch (IOException e) {   
            e.printStackTrace();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        dialogStage.dispose();
    }

    @Override
    public void onZombieKilled() {
        
    }
    
    @Override
    public void reset() {
        super.reset();
    }
    
    @Override
    public void openGameOverDialog() {
        dialogStage = new Stage(new StretchViewport(AppData.width,
                AppData.height));
        
        isDialogOpen = true;

        final Dialog dialog = getGameOverDialog();
        
        Label tb1 = new Label("menu", Assets.aerialLabelStyle);
        
        tb1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                getContext().getContext().setScreen(new MenuScreen(getContext().getContext()));
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        dialog.getButtonTable().add(tb1).width(100).height(25);
        
        tb1 = new Label("play again", Assets.aerialLabelStyle);
        
        tb1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                Action callback = new Action() {
                    @Override
                    public boolean act(float delta) {
                        Campaign.this.reset();
                        
                        getContext().getToolbar().setActive(true);
               
                        isDialogOpen = false;
                        
                        dialogStage.clear();
                        dialogStage.dispose();
                        dialogStage = null;
                   
                        getPlayer().setVisible(true);
                       
                        // Set the input processor back to the game
                        Gdx.input.setInputProcessor(mplex);

                        return true;
                    }
                };
                
                dialogStage.addAction(Actions.sequence(
                                      Actions.fadeOut(1),
                                      callback));
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        dialog.getButtonTable().add(tb1).width(100).height(25);
        
        final float centerX = (AppData.width * 0.5f) - (dialog.getWidth() * 0.5f);
        
        dialog.setX(centerX);
        
        dialog.setY(AppData.height);
        
        dialog.show(dialogStage,
                    sequence(moveTo(dialog.getX(), 
                            (AppData.height * 0.5f) - (dialog.getHeight()), 0.25f)));
        
        
        // Save off the last input processor
        mplex = Gdx.input.getInputProcessor();
        
        // Draw no unit
        setUnitsVisible(false);
        
        Gdx.input.setInputProcessor(dialogStage);
    }
    
    public void update(float delta) {
        if(isEnabled()) {
            elapsed += delta;
            
            getContext().getToolbar().setRemainingTime((int)(survivalTime - elapsed));
            
            if(elapsed >= survivalTime) {
                onLevelEnded();
            }
        }
        
        if(!isDialogOpen) {
            super.update(delta);
        }
        else {
            dialogStage.act(delta);
        }
    }
    
    private void onLevelEnded() {
        setEnabled(false);
        
        if(levelID == CampaignData.getCurrentLevel()) {
            CampaignData.setCurrentLevel(CampaignData.getCurrentLevel() + 1);
        }
        
        isDialogOpen = true;
        
        elapsed = 0;
        survivalTime = 0;
    }

    public void render() {
        super.render();
        
        if(isDialogOpen) {
            dialogStage.draw();
        }
    }
    
    public void setNumber(int number) {
        this.levelID = number;
    }
    
    public int getNumber() {
        return levelID;
    }
    
    public void setSurvivalTime(float survivalTime) {
        this.survivalTime = survivalTime;
    }
    
    public float getSurvivalTime() {
        return survivalTime;
    }
}
