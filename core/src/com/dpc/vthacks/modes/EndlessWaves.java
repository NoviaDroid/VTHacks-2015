package com.dpc.vthacks.modes;

import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.screens.GameScreen;

/**
 * Game mode for endless wave play
 * @author Daniel Christopher
 * @version 3/14/15
 *
 */
public class EndlessWaves extends Level {
    
    public EndlessWaves(GameScreen context) {
        super(context);
    }
    
    @Override
    public void onGameOver() {
        System.err.println("SDFASDFASDFASDFASFDLASDFKALSDFKALSDKFLASDKFSDKFLAK@!#$");
    }
    
    public void update(float delta) {
        super.update(delta);
    }
    
    public void render() {
        super.render();
    }
}
