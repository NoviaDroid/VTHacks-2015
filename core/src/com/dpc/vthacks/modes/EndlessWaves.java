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
import com.dpc.vthacks.EventSystem;
import com.dpc.vthacks.GameEvent;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
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
public class EndlessWaves extends Level {
    private Stage dialogStage; // Game over dialog stage
    private InputProcessor mplex; // Saved off and put back as the input processor after play again touched
    private boolean isDialogOpen;
    private String levelName;
    private static final float TIME_DEC = 0.08f;
    private int wave = 1;
    private int zombiesInWave = 10;
    private int zombiesGenerated; 
    private int zombiesKilled;
    
    public EndlessWaves(GameScreen context, String levelName) {
        super(context);  
        this.levelName = levelName;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        
        dialogStage.dispose();
    }
    
    @Override
    public void loadLevels() {
        try {
            Parser.parseOgmoLevels(levelName, this);
        } catch (IOException e) {   
            e.printStackTrace();
        }
    }
    
    @Override
    public void reset() {
        super.reset();
        
        zombiesGenerated = 0;
        zombiesKilled = 0;
        zombiesInWave = 4;
        wave = 1;
        getContext().getToolbar().setWave(1);
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
                        GameEvent e = new GameEvent(EventSystem.GAME_STARTED);
                        
                        EventSystem.dispatch(e);

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
        final float centerY = (AppData.height * 0.5f) - (dialog.getHeight() * 0.5f);
        
        dialog.setX(centerX);
        
        dialog.setY(AppData.height);
        
        dialog.show(dialogStage,
                    sequence(moveTo(dialog.getX(), 
                            (AppData.height * 0.5f) - (dialog.getHeight()), 0.25f)));
        
        
        // Save off the last input processor
        mplex = Gdx.input.getInputProcessor();
 
        
        Gdx.input.setInputProcessor(dialogStage);
    }
    
    @Override
    public void generateZombie() {
        if(zombiesGenerated < zombiesInWave) {
            super.generateZombie();
            
            zombiesGenerated--;
        }
    }
   
    @Override
    public void onZombieKilled() {
        zombiesKilled++;
        
        if(zombiesKilled >= zombiesInWave) {
            EventSystem.dispatch(new GameEvent(EventSystem.WAVE_ENDED, wave + 1));
        }
    }
    
    public void update(float delta) {
        if(!isDialogOpen) {
            super.update(delta);
        }
        else {
            dialogStage.act(delta);
        }
    }
    
    public void render() {
        super.render();
        
        if(isDialogOpen) {
            dialogStage.draw();
        }
    }
    
    @Override
    public void onEvent(GameEvent e) {
        super.onEvent(e);
        
        switch(e.getEvent()) {
        case EventSystem.WAVE_ENDED:
            onWaveEnd();
            break;
        }
    }
    
    private void onWaveEnd() {
        Assets.sounds.get(Assets.WAVE_UP).play(1);

        wave++;
        zombiesKilled = 0;
        zombiesGenerated = 0;
        zombiesInWave += wave * 1.5f;
        
        getZombies().clear();
        getObjectDrawOrder().clear();
        getObjectDrawOrder().add(getPlayer());
        
        if(getSpawnTime() - TIME_DEC > 0.1) {
            setSpawnTime(getSpawnTime() - TIME_DEC);
        }
    }
}
