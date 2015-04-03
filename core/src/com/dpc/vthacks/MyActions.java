package com.dpc.vthacks;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;
import com.dpc.vthacks.data.AppData;

public class MyActions {
    private static Label waveLabel;
    private static float fadeOutDelay;
    private static float fadeInDelay;
    
    private static Pool<Action> shakeActionPool = new Pool<Action>() {

        @Override
        protected Action newObject() {
            Action a = sequence(
                    moveBy(30, 0,0.02f),
                    moveBy(-30, 0,0.02f),
                    moveBy(0, 10,0.02f),
                    moveBy(0, -10,0.02f));
            
            a.setPool(shakeActionPool);
            
            return a;
        }
        
    };
    
    private static Pool<RepeatAction> linearActionPool = new Pool<RepeatAction>() {

        @Override
        protected RepeatAction newObject() {
            RepeatAction a = repeat(2, sequence(
                    moveBy(20, 0, 0.1f),
                    moveBy(-20, 0, 0.1f)));
            
            a.setPool(linearActionPool);
            
            return a;
        }
        
    };
    
    private static Pool<Action> waveLabelActionPool = new Pool<Action>() {

        @Override
        protected Action newObject() {
            float oldY = waveLabel.getY();
            
            Action a = Actions.parallel(sequence(moveTo(waveLabel.getX(), AppData.TARGET_HEIGHT + 0, 0.1f),
                                         moveTo(waveLabel.getX(), oldY, 0.1f),
                                         repeat(5, sequence(moveBy(5, 0, 0.05f), moveBy(-5, 0, 0.05f))),
                                repeat(5, sequence(alpha(0.5f, 0.1f), alpha(1, 0.1f)))));
            
            a.setPool(waveLabelActionPool);
            
            return a;
        }
        
    };
    
    private static final Pool<Action> fadeOutActionPool = new Pool<Action>() {

        @Override
        protected Action newObject() {
            Action a = fadeOut(fadeOutDelay);
            
            a.setPool(fadeOutActionPool);
            
            return a;
        }
        
    };
    
    private static final Pool<Action> fadeInActionPool = new Pool<Action>() {

        @Override
        protected Action newObject() {
            Action a = fadeIn(fadeInDelay);
            
            a.setPool(fadeInActionPool);
            
            return a;
        }
        
    };
    
    public static Action waveLabelAction() {
        return waveLabelActionPool.obtain();
    }
    
    public static RepeatAction backAndForth() {
        return linearActionPool.obtain();
    }
    
    public static Action shake() {
        return shakeActionPool.obtain();
    }
    
    public static void setWaveLabel(Label waveLabel) {
        MyActions.waveLabel = waveLabel;
    }
    
    public static Action fadeout(float time) {
        fadeOutDelay = time;
        
        Action a = fadeOutActionPool.obtain();
        
        fadeOutDelay = 0;
        
        return a;
    }
    
    public static Action fadein(float time) {
        fadeInDelay = time;
        
        Action a = fadeInActionPool.obtain();
        
        fadeInDelay = 0;
        
        return a;
    }
}
