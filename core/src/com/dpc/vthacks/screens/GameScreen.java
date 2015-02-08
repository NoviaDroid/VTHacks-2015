package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.Road;
import com.dpc.vthacks.army.Army;
import com.dpc.vthacks.army.Base;
import com.dpc.vthacks.army.Battle;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.JSONManager;
import com.dpc.vthacks.data.Sounds;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.input.InputSystem;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;

public class GameScreen implements Screen {
    private static final float xGrav = 4;
    public static final Vector2 gravity = new Vector2(xGrav, -7);
    private static int levelWidth;

    private Road road;
    private Array<Sprite> backgroundElements;
    private GameCamera gameCamera;
    private static Plane player;
    private Sprite background;
    private Sprite[] skyline;
    private FPSLogger logger;
    private Stage stage;
    public static Battle battle;
    
    public GameScreen() {
        levelWidth = MathUtils.random(3400, 4200);
        logger = new FPSLogger();
    }
    
    @Override
    public void show() {
        JSONManager.parseProperties();
        
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        Assets.loadGameTextures(null);
        Sounds.load();
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height), App.batch);
        
        gameCamera = new GameCamera();
        
        backgroundElements = new Array<Sprite>();
        
        background = new Sprite(Assets.background);
        background.setSize(levelWidth, AppData.height);
        
        int rh = Assets.road.getRegionHeight();
        
        road = new Road(0, rh, levelWidth, rh);
        
        road.setTexWidth(levelWidth);
        road.setTexHeight(rh * 5);
        
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
       
        InputSystem.initialize();
        
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
            public boolean keyUp(int keycode) {
                if(keycode == Keys.B) {
                    InputSystem.dispatchEvent(InputSystem.B_UP);
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
               
        gameCamera.position.y = road.getY();
        
        Base enemyBase = new Base(Assets.enemyBase);
        enemyBase.setPosition(levelWidth - (Assets.playerBase.getRegionWidth() * 3), 10);
        
        Base playerBase = new Base(Assets.playerBase);
        playerBase.setPosition(0, 10);
        
        battle = new Battle(new Army(playerBase), new Army(enemyBase));
        
        
        player = Factory.createPlayer((AppData.width * 0.5f) - (Assets.plane.getRegionWidth() * 0.5f), 
                                      (AppData.height * 0.5f) - (Assets.plane.getRegionHeight() * 0.5f));
        
        InputSystem.register(player);
        
        TextButtonStyle prop = new TextButtonStyle();
        prop.font = new BitmapFont();
        
        TextButton tankButton = new TextButton("Tank", prop);
  
        tankButton.setPosition(0, 0);
        
        tankButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                battle.myArmy.add(Factory.tankPool.obtain());
                return false;
            }
        });
        
        TextButton soldierButton = new TextButton("Soldier", prop);
        
        soldierButton.setPosition(tankButton.getX() + tankButton.getWidth() + 5, 0);
        
        soldierButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                battle.myArmy.add(Factory.soldierPool.obtain());
                return false;
            }
        });
        
        stage.addActor(tankButton);
        stage.addActor(soldierButton);
        
        mplexer.addProcessor(stage);
        
        Gdx.input.setInputProcessor(mplexer);
        
        Soldier s = Factory.soldierPool.obtain();
        s.setX(15000);
        s.setY(15000);
        
        battle.myArmy.add(s);
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
    
    public void checkForCollisions() {
        if(player.getBoundingRectangle().overlaps(road)) {
            player.setY(road.getY() + road.getHeight());
        }
        
        for(Bomb b : player.getBombs()) {
            if(road.overlaps(b.getBoundingRectangle())) {
                b.setY(road.getY() + road.getHeight());
                b.triggerExplosion();
                
                int rh = Assets.road.getRegionHeight();
                int padding = 8;
                
                float x = 0;
                float y = rh * 0.5f;
                int w = levelWidth;
                float h = MathUtils.random(rh * 0.5f, rh);
                
                road.set(x, y, w, h);
            }
        }
    }
    
    public void update(float delta) {
        updatePlayer(delta);
        checkForCollisions();
        updateCamera();
        battle.update(delta);
        
        if(Math.random() < 0.05f) {
            battle.enemyArmy.add(Factory.enemyTankPool.obtain());
        }
        
        if(Math.random() < 0.005) {
            Soldier s = (Factory.enemySoldierPool.obtain());
            s.parentArmy = battle.enemyArmy;
        }
        
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
        
        road.render();
        
        player.render();

        battle.render();
        
        App.batch.end();

        stage.draw();
        
        
//        App.debugRenderer.setProjectionMatrix(gameCamera.combined);
//        App.debugRenderer.setColor(Color.RED);
//        App.debugRenderer.begin(ShapeType.Line);
//        
//        App.debugRenderer.rect(player.getBoundingRectangle().x,
//                               player.getBoundingRectangle().y,
//                               player.getBoundingRectangle().width, 
//                               player.getBoundingRectangle().height);
//        
//        App.debugRenderer.rect(road.x,
//                               road.y,
//                               road.width,
//                               road.height);
//        
//        for(Bomb r : player.getBombs()) {
//            App.debugRenderer.rect(r.getBoundingRectangle().x,
//                                   r.getBoundingRectangle().y,
//                                   r.getBoundingRectangle().width,
//                                   r.getBoundingRectangle().height);
//        }
//        
//        for(Unit r : battle.enemyArmy.getUnits()) {
//            App.debugRenderer.rect(r.getBoundingRectangle().x,
//                    r.getBoundingRectangle().y,
//                    r.getBoundingRectangle().width,
//                    r.getBoundingRectangle().height);
//        }
//        
//        for(Unit r : battle.myArmy.getUnits()) {
//            App.debugRenderer.rect(r.getBoundingRectangle().x,
//                    r.getBoundingRectangle().y,
//                    r.getBoundingRectangle().width,
//                    r.getBoundingRectangle().height);
//        }
//        
//        App.debugRenderer.end();
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
        stage.dispose();
        battle.dispose();
        Sounds.dispose();
        
        for(Sprite s : backgroundElements) {
            s.getTexture().dispose();
        }
    }

    public static Plane getPlayer() {
        return player;
    }
}
