package com.dpc.vthacks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.screens.MenuScreen;

public class App extends Game {
    public static SpriteBatch batch;
    public static ShapeRenderer debugRenderer;
    
	@Override
	public void create () {
	    AppData.width = Gdx.graphics.getWidth();
	    AppData.height = Gdx.graphics.getHeight();
	    
	    batch = new SpriteBatch();
	    debugRenderer = new ShapeRenderer();
	    
	    setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.53f, 0.57f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
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
        debugRenderer.dispose();
    }
    
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
