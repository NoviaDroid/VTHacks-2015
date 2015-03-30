package com.dpc.vthacks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.screens.SplashScreen;
import com.dpc.vthacks.weapons.WeaponManager;

public class App extends Game {
    public static SpriteBatch batch;
    
    public static float rand(float min, float max) {
        return min + (float)(Math.random() * (max - min + 1));
    }
    
    public static float dst(float x, float y, float x1, float y1) {
        return (float) Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
    }
    
	@Override
	public void create () {
        Parser.parseLevels();
        WeaponManager.load();
        Bank.load();
        
	    AppData.width = Gdx.graphics.getWidth();
	    AppData.height = Gdx.graphics.getHeight();
	    
	    batch = new SpriteBatch();

	    setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
	    if(getScreen() instanceof GameScreen) {
	        Gdx.gl.glClearColor(0, 0.53f, 0.57f, 1);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    }
	    else {
	        Gdx.gl.glClearColor(0, 0, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    }
	    
		super.render();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        System.out.println("pause");
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
    
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
