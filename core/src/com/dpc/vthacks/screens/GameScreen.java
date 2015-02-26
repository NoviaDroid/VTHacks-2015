package com.dpc.vthacks.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Level;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.data.JSONManager;
import com.dpc.vthacks.data.OgmoParser;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.input.GameToolbar;

public class GameScreen implements Screen {
    private final App context;
    private static Level level;
    private static float joystickPercentX, joystickPercentY;
    private Player player;
    private FPSLogger logger;
    private GameToolbar toolbar;
    
    public GameScreen(App context) {
        this.context = context;
        logger = new FPSLogger();
    }
    
    @Override
    public void show() {
        JSONManager.parseProperties();
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Fonts.load();
        Factory.init();
        
        toolbar = new GameToolbar() {

            @Override
            public void towerButtonTouchDown() {
            }

            @Override
            public void bombButtonTouchDown() {
               
            }
            
            @Override
            public void strafeButtonTouchUp() {
                
                if(Assets.getStrafe().isPlaying()) {
                    Assets.playStrafeEnd();
                }
                
                Assets.stopStrafe();
            }
            
            @Override
            public void strafeButtonTouchDown() {
                Assets.playStrafe();
            }
            
            @Override
            public void tankButtonTouchDown() {
                Tank t = Factory.tankPool.obtain();
            }
            
            @Override
            public void soldierButtonTouchDown() {
                
            }
            
            @Override
            public void tankUpgradeButtonTouchDown() {

            }
        };

        
        try {
            int w = AppData.width;
            int h = AppData.height;

            context.resize(1200, 800);
            
            level = new Level(this);

            player = Factory.createPlayer();
            
            level.setPlayer(player);
            
            OgmoParser.parse("mylevel.oel", level);  
          
            context.resize(w, h);
            
            player.setPosition((AppData.width * 0.5f) - (player.getWidth() * 0.5f), 
                    (player.getGround().getY() + (player.getGround().getHeight() * 0.5f)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputMultiplexer mplexer = new InputMultiplexer(); 

        mplexer.addProcessor(level.getInputAdapter());
        
        mplexer.addProcessor(toolbar.getStage());
        
        mplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.B) {
                    toolbar.bombButtonTouchDown();
                }
                else if(keycode == Keys.UP) {
                    player.increaseElevation();
                }
                else if(keycode == Keys.S) {
                    toolbar.strafeButtonTouchDown();
                }
                
                return false;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Keys.UP) {
                    player.decreaseElevation();
                }
                else if(keycode == Keys.S) {
                    toolbar.strafeButtonTouchUp();
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                return false;
            }
        });
        
        Gdx.input.setInputProcessor(mplexer);
    }

    public void update(float delta) {
        level.update(delta);
        
        joystickPercentX = toolbar.getJoystick().getKnobPercentX();
        joystickPercentY = toolbar.getJoystick().getKnobPercentY();
        
        player.walk(joystickPercentX, joystickPercentY);
        
        //level.scrollBackgrounds(joystickPercentX, joystickPercentY);
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        logger.log();
        
        level.render();
        toolbar.draw();
    }

    @Override
    public void resize(int width, int height) {
        AppData.onResize(width, height);
        toolbar.onResize(width, height);
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
        Assets.unloadGameTextures();
        Assets.dispose();
    }
    
    public static Level getLevel() {
        return level;
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
}
