package com.dpc.vthacks.modes;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
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
    private int wave;
   
    public EndlessWaves(GameScreen context) {
        super(context);
    }
    
    @Override
    public void onGameOver() {
        super.onGameOver();
        
        // Disable the controls
        getContext().getToolbar().setActive(false);
        setActive(false);
    }
    
    @Override
    public void openGameOverDialog() {
        dialogStage = new Stage(new StretchViewport(AppData.width,
                AppData.height));
        
        isDialogOpen = true;

        final Dialog dialog = getGameOverDialog();
        
        TextButton tb1 = new TextButton("menu", Assets.uiSkin);
        
        tb1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                getContext().getContext().setScreen(new MenuScreen(getContext().getContext()));
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        dialog.getButtonTable().add(tb1).width(100).height(25);
        
        tb1 = new TextButton("play again", Assets.uiSkin);
        
        tb1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                Action callback = new Action() {
                    @Override
                    public boolean act(float delta) {
                        EndlessWaves.this.reset();
                        
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
        final float centerY = (AppData.height * 0.5f) - (dialog.getHeight() * 0.5f);
        
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
    
    private void onWaveEnd() {
        wave++;
        getContext().getToolbar().setWave(wave);
    }
    
    public void dispose() {

    }
}
