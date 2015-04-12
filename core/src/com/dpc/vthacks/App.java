package com.dpc.vthacks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Bank;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.data.Settings;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.screens.SplashScreen;
import com.dpc.vthacks.weapons.WeaponManager;

public class App extends Game {
    public static SpriteBatch batch;
    public static ShaderProgram defaultShader; // Normal shader
    public static ShaderProgram crtShader; // CRT scanline shader
    public static Settings settings;
    
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
        EventSystem.initialize();
        Bank.load();

        settings = new Settings();
        
	    AppData.width = Gdx.graphics.getWidth();
	    AppData.height = Gdx.graphics.getHeight();
	    
	    batch = new SpriteBatch();
	    
	    crtShader = new ShaderProgram(Gdx.files.internal("shaders/scanlines.vert"), 
	                                  Gdx.files.internal("shaders/scanlines.frag"));
	    
        System.out.println(crtShader.isCompiled() ? "crt shader compiled" : crtShader.getLog());

        
        defaultShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"),
                                          Gdx.files.internal("shaders/fragment.frag"));

        System.err.println(defaultShader.isCompiled() ? "default shader compiled" : defaultShader.getLog());
        
        batch.setShader(settings.isScanLineShaderEnabled() ? crtShader : defaultShader);

	    setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
	    Gdx.gl.glClearColor(0, 0, 0, 1);
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
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        defaultShader.dispose();
        crtShader.dispose();
        Assets.deallocateFonts();
    }
    
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
