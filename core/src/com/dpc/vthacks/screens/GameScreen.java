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
import com.dpc.vthacks.data.JSONManager;
import com.dpc.vthacks.data.OgmoParser;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.input.GameToolbar;
import com.dpc.vthacks.level.Level;

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
                
                s.setX(level.getPlayer().getX());
                s.setY(AppData.height + s.getHeight());
                
                s.setFallTargetX(MathUtils.random(level.getGameCamera().position.x - 
                                              (level.getGameCamera().viewportWidth * 0.5f),
                                              level.getGameCamera().position.x + 
                                              (level.getGameCamera().viewportWidth * 0.5f)));
                
                s.setFallTargetY(MathUtils.random(level.getPlayer().getGround().getY(),
                                              level.getPlayer().getGround().getY() +
                                              level.getPlayer().getGround().getHeight()));
                
                level.getPlayerArmy().add(s);
            }
            
            @Override
            public void soldierButtonTouchDown() {
                Soldier s = Factory.soldierPool.obtain();
                s.setX(level.getPlayer().getX());
                s.setY(AppData.height + s.getHeight());
                
                s.setFallTargetX(MathUtils.random(level.getGameCamera().position.x - 
                                              (level.getGameCamera().viewportWidth * 0.5f),
                                              level.getGameCamera().position.x + 
                                              (level.getGameCamera().viewportWidth * 0.5f)));
                
                s.setFallTargetY(MathUtils.random(level.getPlayer().getGround().getY(),
                                              level.getPlayer().getGround().getY() +
                                              level.getPlayer().getGround().getHeight()));
              
                s.setCurrentTarget(level.getPlayer().getX(),
                        level.getPlayer().getY());
                
                level.getPlayerArmy().add(s);
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
            
            toolbar.setAmmo(player.getCurrentWeapon().getAmmo());
            
            toolbar.setGunIcon(player.getCurrentWeapon());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputMultiplexer mplexer = new InputMultiplexer(); 
        
        mplexer.addProcessor(toolbar.getStage());
        
        mplexer.addProcessor(level.getGestureDetector());
        
        mplexer.addProcessor(level.getInputAdapter());
        
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
        level.update(delta);
        toolbar.update(delta);
        
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
