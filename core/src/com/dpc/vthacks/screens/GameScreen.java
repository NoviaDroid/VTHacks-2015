package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.input.InputSystem;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;

public class GameScreen implements Screen {
    private static boolean gameOver;
  
    private static final float xGrav = 12;
    public static final Vector2 gravity = new Vector2(xGrav, -9.807f);
    private static int levelWidth;
    private float generationRandThresh;
    private static final float START_GEN_RAND_THRESH = 0.010f;
    private Road road;
    private Array<Sprite> backgroundElements;
    private GameCamera gameCamera;
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
        
        generationRandThresh = START_GEN_RAND_THRESH;
        
        stage = new Stage(new StretchViewport(AppData.width, AppData.height), App.batch);
        
        gameCamera = new GameCamera();
        
        backgroundElements = new Array<Sprite>();
        
        background = new Sprite(Assets.background);
        background.setSize(levelWidth, AppData.height);
        
        int rh = Assets.road.getRegionHeight();
        
        road = new Road(0, rh, levelWidth, rh);
        
        road.setTexWidth(levelWidth);
        road.setTexHeight(rh * 5);
        
        skyline = new Sprite[Assets.getSkylines().length];
        
        skyline[0] = new Sprite(Assets.getSkylines()[0]);
        skyline[0].setX(0);
        skyline[0].setY(road.getY() + road.getHeight());
        skyline[0].setSize(levelWidth, AppData.height);

        skyline[1] = new Sprite(Assets.getSkylines()[1]);
        skyline[1].setX(0);
        skyline[1].setY(road.getY() + road.getHeight());
        skyline[1].setSize(levelWidth, AppData.height);
        
        for(Sprite s : skyline) {
            backgroundElements.add(s);
        }
        
        float lastBuildingEnd = 0;
        
        for(int i = 0; i < 30; i++) {
            Sprite s = new Sprite(Assets.getBuildings()[MathUtils.random(Assets.getBuildings().length - 1)]);
            s.setX(lastBuildingEnd);
            s.setY(road.getY() + road.getHeight());
            s.setSize(Assets.getBuildings()[i % Assets.getBuildings().length].getRegionWidth() * 3,
                      Assets.getBuildings()[i % Assets.getBuildings().length].getRegionHeight() * 3);
            
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
                else if(keycode == Keys.A) {
                    gameCamera.position.x -= 100;
                }
                else if(keycode == Keys.D) {
                    gameCamera.position.x += 100;
                }
                else if(keycode == Keys.UP) {
                    InputSystem.dispatchEvent(InputSystem.TOUCH_DOWN);
                }
                
                return false;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Keys.B) {
                    InputSystem.dispatchEvent(InputSystem.B_UP);
                }
                else if(keycode == Keys.UP) {
                    InputSystem.dispatchEvent(InputSystem.TOUCH_UP);    
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
        
        mplexer.addProcessor(new GestureDetector(20, 0.5f, 0.03f, 0.15f, new GestureListener() {

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
        enemyBase.setPosition(levelWidth - (Assets.playerBase.getRegionWidth() * 3), 25);
        
        Base playerBase = new Base(Assets.playerBase);
        playerBase.setPosition(0, 25);
        
        battle = new Battle(new Army(playerBase), new Army(enemyBase));
        
        
        battle.setPlayer(Factory.createPlayer((AppData.width * 0.5f) - (Assets.plane.getRegionWidth() * 0.5f), 
                                      (AppData.height * 0.5f) - (Assets.plane.getRegionHeight() * 0.5f)));
        
        InputSystem.register(battle.getPlayer());
        
        TextButtonStyle prop = new TextButtonStyle();
        prop.font = new BitmapFont();
        
        TextButton tankButton = new TextButton("Tank", prop);
  
        tankButton.setPosition(0, 0);
        
        tankButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Tank t = Factory.tankPool.obtain();

                
                battle.getMyArmy().add(t);
                return false;
            }
        });
        
        TextButton soldierButton = new TextButton("Soldier", prop);
        
        soldierButton.setPosition(tankButton.getX() + tankButton.getWidth() + 5, 0);
        
        soldierButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                battle.getMyArmy().add(Factory.soldierPool.obtain());
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
        
        battle.getMyArmy().add(s);
    }

    private void updatePlayer(float delta) {
     // If at the edge of the screen, turn around
        if(battle.getPlayer().getX() + battle.getPlayer().getWidth() >= levelWidth) {
            battle.getPlayer().setX(levelWidth - battle.getPlayer().getWidth() - 1);
            
            // Flip the plane
            Assets.plane.flip(true, false);
            battle.getPlayer().setRegion(Assets.plane);
            
            // Now set the gravity on the x axis
            gravity.x = -xGrav;
        }
        else if(battle.getPlayer().getX() <= 0) {
            battle.getPlayer().setX(1);
            
            // Flip the plane
            Assets.plane.flip(true, false);
            battle.getPlayer().setRegion(Assets.plane);
            
            // Now set the gravity on the x axis
            gravity.x = xGrav;
        }
        
        battle.getPlayer().applyVel(gravity); // Apply gravity to the player
        battle.getPlayer().update(delta);
    }
    
    private void updateCamera() {
        gameCamera.position.set(battle.getPlayer().getX(), gameCamera.position.y, 0);
        
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
        if(battle.getPlayer().getBoundingRectangle().overlaps(road)) {
            battle.getPlayer().setY(road.getY() + road.getHeight());
        }
        
        for(Bomb b : battle.getPlayer().getBombs()) {
            if(road.overlaps(b.getBoundingRectangle())) {
                b.setY(road.getY() + road.getHeight());
                b.triggerExplosion();
                
                int rh = Assets.road.getRegionHeight();
                int padding = 8;
                
                float x = 0;
                float y = 0;
                int w = levelWidth;
                float h = MathUtils.random(rh * 0.5f, rh);
                
                road.set(x, y, w, h);
            }
        }
        
        if(battle.getPlayer().getBoundingRectangle().overlaps(road)) {
            Bomb b = Factory.bombPool.obtain();
            b.setX(battle.getPlayer().getX());
            b.setY(battle.getPlayer().getY());
        }
    }
    
    public void update(float delta) {
        updatePlayer(delta);
        checkForCollisions();
        updateCamera();
        
        battle.update(delta);
        
        generationRandThresh += 0.000001f;
        
        if(Math.random() < generationRandThresh) {
            battle.getEnemyArmy().add(Factory.enemyTankPool.obtain());
            battle.getMyArmy().add(Factory.tankPool.obtain());
        }
        
        if(Math.random() < generationRandThresh) {
            Soldier s = (Factory.enemySoldierPool.obtain());
            s.setParentArmy(battle.getEnemyArmy());
            battle.getEnemyArmy().add(s);
            
            Soldier sa = (Factory.soldierPool.obtain());
            sa.setParentArmy(battle.getMyArmy());
            battle.getMyArmy().add(sa);
        }
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
        
        battle.getPlayer().render();

        battle.render();
        
        App.batch.end();

        stage.draw();
        
//        App.debugRenderer.setProjectionMatrix(gameCamera.combined);
//        App.debugRenderer.setColor(Color.GREEN);
//        App.debugRenderer.begin(ShapeType.Line);
//
//        for (Unit u : battle.getMyArmy().getUnits()) {
//            App.debugRenderer.rect(u.getX(), u.getY(), u.getRange(),
//                    u.getRange());
//        }
//      
//        App.debugRenderer.setColor(Color.RED);
//        
//        for (Unit u : battle.getEnemyArmy().getUnits()) {
//            App.debugRenderer.rect(u.getX(), u.getY(), u.getRange(),
//                    u.getRange());
//        }
//        
//        App.debugRenderer.end();
//
//        App.debugRenderer.setProjectionMatrix(gameCamera.combined);
//        App.debugRenderer.setColor(Color.GREEN);
//        App.debugRenderer.begin(ShapeType.Line);
//
//        App.debugRenderer.rect(battle.getPlayer().getBoundingRectangle().x,
//                battle.getPlayer().getBoundingRectangle().y,
//                battle.getPlayer().getBoundingRectangle().width,
//                battle.getPlayer().getBoundingRectangle().height);
//
//        App.debugRenderer.setColor(Color.PURPLE);
//        
//        App.debugRenderer.rect(road.x, road.y, road.width, road.height);
//        
//        App.debugRenderer.setColor(Color.GREEN);
//        
//        for (Bomb r : battle.getPlayer().getBombs()) {
//            App.debugRenderer.rect(r.getBoundingRectangle().x,
//                    r.getBoundingRectangle().y, r.getBoundingRectangle().width,
//                    r.getBoundingRectangle().height);
//        }
//
//        for (Unit r : battle.getMyArmy().getUnits()) {
//            App.debugRenderer.rect(r.getBoundingRectangle().x,
//                    r.getBoundingRectangle().y, r.getBoundingRectangle().width,
//                    r.getBoundingRectangle().height);
//        }
//
//        App.debugRenderer.setColor(Color.RED);
//        
//        App.debugRenderer.rect(
//                battle.getMyArmy().getBase().getBoundingRectangle().x, battle.getMyArmy()
//                        .getBase().getBoundingRectangle().y, battle.getMyArmy()
//                        .getBase().getBoundingRectangle().width, battle.getMyArmy()
//                        .getBase().getBoundingRectangle().height);
//        
//        for (Unit r : battle.getEnemyArmy().getUnits()) {
//            App.debugRenderer.rect(r.getBoundingRectangle().x,
//                    r.getBoundingRectangle().y, r.getBoundingRectangle().width,
//                    r.getBoundingRectangle().height);
//        }
//        
//        App.debugRenderer.rect(battle.getEnemyArmy().getBase()
//                .getBoundingRectangle().x, battle.getEnemyArmy().getBase()
//                .getBoundingRectangle().y, battle.getEnemyArmy().getBase()
//                .getBoundingRectangle().width, battle.getEnemyArmy().getBase()
//                .getBoundingRectangle().height);
//
//        App.debugRenderer.end();
    }

    public static void triggerGameOver() {
        gameOver = true;
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
        battle.getPlayer().dispose();
        stage.dispose();
        battle.dispose();
        Sounds.dispose();
        
        for(Sprite s : backgroundElements) {
            s.getTexture().dispose();
        }
    }

    public static Plane getPlayer() {
        return battle.getPlayer();
    }
}
