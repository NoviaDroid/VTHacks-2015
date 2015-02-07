package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.JSONManager;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.input.InputSystem;
import com.dpc.vthacks.plane.Plane;

public class GameScreen implements Screen {
    private static final float xGrav = 4;
    public static final Vector2 gravity = new Vector2(xGrav, -7);
    private static int levelWidth;
    
    private Array<Sprite> backgroundElements;
    private GameCamera gameCamera;
    private Plane player;
    private Sprite background;
    private Sprite[] skyline;
    private Soldier testSoldier;
    private FPSLogger logger;
    
    public GameScreen() {
        levelWidth = MathUtils.random(3400, 4200);
        logger = new FPSLogger();
    }
    
    @Override
    public void show() {
        JSONManager.parseProperties();
        
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Assets.loadGameTextures(null);
        
        gameCamera = new GameCamera();
        
        player = Factory.createPlayer((AppData.width * 0.5f) - (Assets.plane.getRegionWidth() * 0.5f), 
                                      (AppData.height * 0.5f) - (Assets.plane.getRegionHeight() * 0.5f));
        
        backgroundElements = new Array<Sprite>();
        
        background = new Sprite(Assets.background);
        background.setSize(levelWidth, AppData.height);
        
        Sprite road = new Sprite(Assets.road);
        road.setSize(levelWidth, road.getHeight() * 2);
        road.setX(0);
        road.setY(0);
        
        skyline = new Sprite[Assets.skylines.length];
        
        skyline[0] = new Sprite(Assets.skylines[0]);
        skyline[0].setX(0);
        skyline[0].setY(road.getY() + road.getHeight());
        skyline[0].setSize(levelWidth, AppData.height);

        skyline[1] = new Sprite(Assets.skylines[1]);
        skyline[1].setX(0);
        skyline[1].setY(road.getY() + road.getHeight());
        skyline[1].setSize(levelWidth, AppData.height);
        
        for(Sprite s : skyline) {
            backgroundElements.add(s);
        }
        
        float lastBuildingEnd = 0;
        
        for(int i = 0; i < 30; i++) {
            Sprite s = new Sprite(Assets.buildings[MathUtils.random(Assets.buildings.length - 1)]);
            s.setX(lastBuildingEnd);
            s.setY(road.getY() + road.getHeight());
            s.setSize(Assets.buildings[i % Assets.buildings.length].getRegionWidth() * 3,
                      Assets.buildings[i % Assets.buildings.length].getRegionHeight() * 3);
            
            backgroundElements.add(s);
            
            lastBuildingEnd = s.getX() + s.getWidth();
        }
        
        
        backgroundElements.add(road);

        InputSystem.initialize();
        InputSystem.register(player);
        
        InputMultiplexer mplexer = new InputMultiplexer();
        
        mplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.B) {
                    InputSystem.dispatchEvent(InputSystem.B);
                }

                return false;
            }
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                InputSystem.dispatchEvent(InputSystem.TOUCH_UP);
                
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                InputSystem.dispatchEvent(InputSystem.TOUCH_DRAGGED);
                
                return false;
            }
        });
        
        mplexer.addProcessor(new GestureDetector(20, 0.5f, 0.1f, 0.15f, new GestureListener() {

            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {

                
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                InputSystem.dispatchEvent(InputSystem.TOUCH_DOWN);
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1,
                    Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                
                return false;
            }
            
        }));
        
        Gdx.input.setInputProcessor(mplexer);
        
        gameCamera.position.y = road.getY();
        
        testSoldier = Factory.createSoldier(25, 25);
    }

    private void updatePlayer(float delta) {
     // If at the edge of the screen, turn around
        if(player.getX() + player.getWidth() >= levelWidth) {
            player.setX(levelWidth - player.getWidth() - 1);
            
            // Flip the plane
            Assets.plane.flip(true, false);
            player.setRegion(Assets.plane);
            
            // Now set the gravity on the x axis
            gravity.x = -xGrav;
        }
        else if(player.getX() <= 0) {
            player.setX(1);
            
            // Flip the plane
            Assets.plane.flip(true, false);
            player.setRegion(Assets.plane);
            
            // Now set the gravity on the x axis
            gravity.x = xGrav;
        }
        
        player.applyVel(gravity); // Apply gravity to the player
        player.update(delta);
    }
    
    private void updateCamera() {
        gameCamera.position.set(player.getX(), gameCamera.position.y, 0);
        
        boolean wasClamped = false;
        
        // Clamp the camera's position
        if(gameCamera.position.x - (gameCamera.viewportWidth * 0.5f) < 0) {
            gameCamera.position.x = (gameCamera.viewportWidth * 0.5f);
            wasClamped = true;
        }
        else if(gameCamera.position.x + (gameCamera.viewportWidth * 0.5f) > levelWidth) {
            gameCamera.position.x = levelWidth - (gameCamera.viewportWidth * 0.5f);
            wasClamped = true;
        }
        
        gameCamera.update();
        
        if(!wasClamped) {
            if(gravity.x <= 0) {
                skyline[0].setX(skyline[0].getX() + 0.25f);
                skyline[1].setX(skyline[1].getX() + 0.1f);
            }
            else {
                skyline[0].setX(skyline[0].getX() - 0.25f);
                skyline[1].setX(skyline[1].getX() - 0.1f);
            }
        }
    }
    
    public void update(float delta) {
        updatePlayer(delta);
        updateCamera();
        testSoldier.update(delta);
        
        logger.log();
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        
        App.batch.setProjectionMatrix(gameCamera.combined);
        App.batch.begin();
        
        App.batch.disableBlending();
        
        background.draw(App.batch);
        
        App.batch.enableBlending();
        
        for(Sprite s : backgroundElements) {
            s.draw(App.batch);
        }
        
        player.render();
        
        testSoldier.render();

        App.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        AppData.onResize(width, height);
        gameCamera.resize(width, height);
    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {
        Assets.loadGameTextures(null);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Assets.unloadGameTextures();
        player.dispose();
        
        for(Sprite s : backgroundElements) {
            s.getTexture().dispose();
        }
    }

}
