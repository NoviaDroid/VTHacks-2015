package com.dpc.vthacks.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.dpc.vthacks.AndroidCamera;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Parser;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.input.GameToolbar;
import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.modes.Campaign;
import com.dpc.vthacks.modes.EndlessWaves;

public class GameScreen implements Screen {
    private final App context;
    private static Level gameMode;
    private static float joystickPercentX, joystickPercentY;
    private Player player;
    private FPSLogger logger;
    private GameToolbar toolbar;
    private String levelName;
    private static final float CAMERA_ZOOM = 0.45f;
    
    public GameScreen(App context, String levelName, String mode) {
        this.context = context;
        this.levelName = levelName;
        
        logger = new FPSLogger();

        if(mode.equals(LevelProperties.ENDLESS_MODE)) {
            gameMode = new EndlessWaves(this, levelName);
            toolbar = new GameToolbar(this, false);
        }
        else if(mode.equals(LevelProperties.CAMPAIGN_MODE)) {
            gameMode = new Campaign(this, Integer.parseInt(levelName));
            toolbar = new GameToolbar(this, true);
        }
    }
    
    @Override
    public void show() {
        Assets.allocateGameScreen();
        
        Parser.parseProperties();
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Factory.init();

        gameMode.setGameCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT, CAMERA_ZOOM));
       
        int w = AppData.width;
        int h = AppData.height;

        resize(1024, 768);

        player = Factory.createPlayer();

        gameMode.setPlayer(player);

        gameMode.getObjectDrawOrder().add(player);

        gameMode.loadLevels();
        
        toolbar.setAmmo(player.getCurrentWeapon().getAmmo());

        toolbar.setGunIcon(player.getCurrentWeapon());

        InputMultiplexer mplexer = new InputMultiplexer(); 
        
        mplexer.addProcessor(toolbar.getStage());
        
        mplexer.addProcessor(gameMode.getGestureDetector());
        
        mplexer.addProcessor(gameMode.getInputAdapter());
        
        
        Gdx.input.setInputProcessor(mplexer);
        
        gameMode.repositionCamera();
    }

    public void update(float delta) {
        gameMode.update(delta);
        toolbar.update(delta);
        
        joystickPercentX = toolbar.getJoystick().getKnobPercentX();
        joystickPercentY = toolbar.getJoystick().getKnobPercentY();
        
        if(gameMode.isActive()) {
            player.walk(joystickPercentX, joystickPercentY);
        }
        
        //level.scrollBackgrounds(joystickPercentX, joystickPercentY);
    }
    
    @Override
    public void render(float delta) {
        update(delta);

        gameMode.render();
        toolbar.draw();
    }

    @Override
    public void resize(int width, int height) {
        AppData.onResize(width, height);
        toolbar.onResize(width, height);
        
        gameMode.setGameCamera(new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT, CAMERA_ZOOM));
        
        if(gameMode.getPlayer() != null) {
            gameMode.repositionCamera();
        }
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
        Assets.deallocateGameScreen();
        toolbar.dispose();
        gameMode.dispose();
    }
    
    public static Level getLevel() {
        return gameMode;
    }
    
    public GameToolbar getToolbar() {
        return toolbar;
    }
    
    public static float getJoystickPercentX() {
        return joystickPercentX;
    }
    
    public static float getJoystickPercentY() {
        return joystickPercentY;
    }
    
    public App getContext() {
        return context;
    }
}
