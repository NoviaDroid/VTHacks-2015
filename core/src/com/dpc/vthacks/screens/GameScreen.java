package com.dpc.vthacks.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.MathUtils;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.data.JSONParser;
import com.dpc.vthacks.data.OgmoParser;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.input.GameToolbar;
import com.dpc.vthacks.level.Level;
import com.dpc.vthacks.level.LevelProperties;
import com.dpc.vthacks.modes.EndlessWaves;

public class GameScreen implements Screen {
    private final App context;
    private static Level gameMode;
    private static float joystickPercentX, joystickPercentY;
    private Player player;
    private FPSLogger logger;
    private GameToolbar toolbar;
    private String levelName;
    
    public GameScreen(App context, String levelName, String mode) {
        this.context = context;
        this.levelName = levelName;
        
        logger = new FPSLogger();
        
        gameMode = new EndlessWaves(this);
        
        if(mode.equals(LevelProperties.ENDLESS_MODE)) {
            gameMode = new EndlessWaves(this);
        }
        else if(mode.equals(LevelProperties.CAMPAIGN_MODE)) {
            
            
        }
    }
    
    @Override
    public void show() {
        JSONParser.parseProperties();
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Factory.init();

        toolbar = new GameToolbar(this) {

            @Override
            public void towerButtonTouchDown() {
            }

            @Override
            public void bombButtonTouchDown() {
               
            }
            
            @Override
            public void strafeButtonTouchUp() {
                
                if(Assets.strafe.isPlaying()) {
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
                Tank s = Factory.tankPool.obtain();
                
                s.setX(gameMode.getPlayer().getX());
                s.setY(AppData.height + s.getHeight());
                
                s.setFallTargetX(MathUtils.random(gameMode.getGameCamera().position.x - 
                                              (gameMode.getGameCamera().viewportWidth * 0.5f),
                                              gameMode.getGameCamera().position.x + 
                                              (gameMode.getGameCamera().viewportWidth * 0.5f)));
                
                s.setFallTargetY(MathUtils.random(gameMode.getPlayer().getGround().getY(),
                                              gameMode.getPlayer().getGround().getY() +
                                              gameMode.getPlayer().getGround().getHeight()));
                
                gameMode.getPlayerArmy().add(s);
            }
            
            @Override
            public void soldierButtonTouchDown() {
                Soldier s = Factory.soldierPool.obtain();
                s.setX(gameMode.getPlayer().getX());
                s.setY(AppData.height + s.getHeight());
                
                s.setFallTargetX(MathUtils.random(gameMode.getGameCamera().position.x - 
                                              (gameMode.getGameCamera().viewportWidth * 0.5f),
                                              gameMode.getGameCamera().position.x + 
                                              (gameMode.getGameCamera().viewportWidth * 0.5f)));
                
                s.setFallTargetY(MathUtils.random(gameMode.getPlayer().getGround().getY(),
                                              gameMode.getPlayer().getGround().getY() +
                                              gameMode.getPlayer().getGround().getHeight()));
              
                s.setCurrentTarget(gameMode.getPlayer().getX(),
                        gameMode.getPlayer().getY());
                
                gameMode.getPlayerArmy().add(s);
            }
            
            @Override
            public void tankUpgradeButtonTouchDown() {

            }
        };
        
        try {
            int w = AppData.width;
            int h = AppData.height;

            context.resize(1200, 800);
            
            player = Factory.createPlayer();
            
            gameMode.setPlayer(player);
            
            gameMode.getObjectDrawOrder().add(player);
            
            OgmoParser.parse(levelName, gameMode);  
          
            context.resize(w, h);
            
            player.centerInViewport();
            
            toolbar.setAmmo(player.getCurrentWeapon().getAmmo());
            
            toolbar.setGunIcon(player.getCurrentWeapon());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputMultiplexer mplexer = new InputMultiplexer(); 
        
        mplexer.addProcessor(toolbar.getStage());
        
        mplexer.addProcessor(gameMode.getGestureDetector());
        
        mplexer.addProcessor(gameMode.getInputAdapter());
        
        mplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.B) {
                    toolbar.bombButtonTouchDown();
                }
                else if(keycode == Keys.S) {
                    toolbar.strafeButtonTouchDown();
                }

                return false;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Keys.S) {
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
        logger.log();
        
        gameMode.render();
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
