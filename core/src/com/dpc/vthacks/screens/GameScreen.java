package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.Road;
import com.dpc.vthacks.army.Army;
import com.dpc.vthacks.army.Base;
import com.dpc.vthacks.army.Battle;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.data.Fonts;
import com.dpc.vthacks.data.JSONManager;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.infantry.Soldier;
import com.dpc.vthacks.infantry.Tank;
import com.dpc.vthacks.input.GameToolbar;
import com.dpc.vthacks.plane.Bomb;
import com.dpc.vthacks.plane.Plane;

public class GameScreen implements Screen {
    private static final float START_GEN_RAND_THRESH = 0.010f;
    private static final float xGrav = 11;
    public static final Vector2 gravity = new Vector2(xGrav, -5.5f);
    
    private static boolean gameOver, isLoadingAssets;
    private static int levelWidth;
    private float generationRandThresh;
    
    private App context;
    private Road road;
    public static Battle battle;
    private GameCamera gameCamera;
    private Sprite background;
    private Sprite[] skyline;
    private Array<Sprite> backgroundElements;
    private FPSLogger logger;
    private GameToolbar toolbar;
    
    public GameScreen(App context) {
        levelWidth = MathUtils.random(3200, 3400);
        logger = new FPSLogger();
    }
    
    @Override
    public void show() {
        JSONManager.parseProperties();

        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Fonts.load();
        
        generationRandThresh = START_GEN_RAND_THRESH;

        gameCamera = new GameCamera();
        
        backgroundElements = new Array<Sprite>();
        
        background = new Sprite(Assets.background);
        background.setSize(levelWidth, AppData.height);
        
        int rh = Assets.road.getRegionHeight();
        
        road = new Road(0, 0, levelWidth, rh);
        
        road.setTexWidth(levelWidth);
        road.setTexHeight(rh * 2);
        
        skyline = new Sprite[Assets.getSkylines().length];
        
        skyline[0] = new Sprite(Assets.getSkylines()[0]);
        skyline[0].setX(0);
        skyline[0].setY(road.getY() + road.getHeight());
        skyline[0].setSize(levelWidth, AppData.height * 1f);

        skyline[1] = new Sprite(Assets.getSkylines()[1]);
        skyline[1].setX(0);
        skyline[1].setY(road.getY() + road.getHeight());
        skyline[1].setSize(levelWidth, skyline[0].getHeight() * 0.9f);
        
        for(Sprite s : skyline) {
            backgroundElements.add(s);
        }
       
        InputMultiplexer mplexer = new InputMultiplexer(); 
               
        gameCamera.position.y = road.getY();
        
        Base enemyBase = new Base(Assets.enemyBase);
        enemyBase.setPosition(levelWidth - (Assets.playerBase.getRegionWidth() * 3), 0);
        
        Base playerBase = new Base(Assets.playerBase);
        playerBase.setPosition(0, 0);
        
        battle = new Battle(new Army(playerBase), new Army(enemyBase));
        
        Factory.init();
        
        battle.setPlayer(Factory.createPlayer());

        Factory.init();
        toolbar = new GameToolbar() {

            @Override
            public void towerButtonTouchDown() {
            }

            @Override
            public void bombButtonTouchDown() {
                battle.getPlayer().releaseBomb();
            }
            
            @Override
            public void strafeButtonTouchUp() {
                battle.getPlayer().setStrafing(false);
                
                if(Assets.getStrafe().isPlaying()) {
                    Assets.playStrafeEnd();
                }
                
                Assets.stopStrafe();
            }
            
            @Override
            public void strafeButtonTouchDown() {
                battle.getPlayer().setStrafing(true);
                Assets.playStrafe();
            }
            
            @Override
            public void tankButtonTouchDown() {
                Tank t = Factory.tankPool.obtain();
                
                if(battle.getPlayer().getMoney() >= t.getCost()) {
                    
                    battle.getMyArmy().add(t);
    
                    battle.getPlayer().takeMoney(t.getCost());
                }
            }
            
            @Override
            public void soldierButtonTouchDown() {
                battle.getMyArmy().add(Factory.soldierPool.obtain());

            }
            
            @Override
            public void tankUpgradeButtonTouchDown() {

            }
        };
        
        road.setY(toolbar.getTop());
        
        float lastBuildingEnd = 0;
        TextureRegion building;
        
        for(int i = 0; i < 15; i++) {
            building = Assets.getBuildings()[MathUtils.random(Assets.getBuildings().length - 1)];
           
            Sprite s = new Sprite(building);
            s.setSize(levelWidth / 10,
                      building.getRegionHeight() + AppData.height * 0.3f);
            
            s.setX(lastBuildingEnd);
            s.setY(road.getY() + road.getTexHeight());
       
            backgroundElements.add(s);
            
            lastBuildingEnd = s.getX() + s.getWidth();
        }
        
        playerBase.setY(road.getY());
        enemyBase.setY(road.getY());
        
        Factory.init();
        
        mplexer.addProcessor(toolbar.getStage());
        
        mplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.B) {
                    battle.getPlayer().releaseBomb();
                    toolbar.bombButtonTouchDown();
                }
                else if(keycode == Keys.UP) {
                    battle.getPlayer().increaseElevation();
                }
                else if(keycode == Keys.S) {
                    toolbar.strafeButtonTouchDown();
                }
                
                return false;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Keys.UP) {
                    battle.getPlayer().decreaseElevation();
                }
                else if(keycode == Keys.S) {
                    toolbar.strafeButtonTouchUp();
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                battle.getPlayer().increaseElevation();
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                battle.getPlayer().decreaseElevation();
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                return false;
            }
        });
        
        Gdx.input.setInputProcessor(mplexer);
        
        Soldier s = Factory.soldierPool.obtain();
        s.setX(15000);
        s.setY(15000);
        
        battle.getMyArmy().add(s);

        //GfxHelper.oldX = AppData.width;
        //GfxHelper.oldY = AppData.height;
    }

    private void updatePlayer(float delta) {
     // If at the edge of the screen, turn around
        if(battle.getPlayer().getX() + battle.getPlayer().getWidth() >= levelWidth) {
            battle.getPlayer().setX(levelWidth - battle.getPlayer().getWidth() - 1);
            
            // Flip the plane
            Assets.flipPlayerRegions();
            battle.getPlayer().setHeadingEast(false);
            
            // Now set the gravity on the x axis
            gravity.x = -xGrav;
        }
        else if(battle.getPlayer().getX() <= 0) {
            battle.getPlayer().setX(1);
            
            // Flip the plane
            Assets.flipPlayerRegions();
            battle.getPlayer().setHeadingEast(true);
            
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
                skyline[0].setX(skyline[0].getX() + 15f);
                skyline[1].setX(skyline[1].getX() + 0.3f);
            }
            else {
                skyline[0].setX(skyline[0].getX() - 0.75f);
                skyline[1].setX(skyline[1].getX() - 0.3f);
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
                
                int rh = (int) road.getHeight();
                int padding = 8;
                
                float x = 0;
                float y = road.getY();
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
        if(!isLoadingAssets) {
            updatePlayer(delta);
            checkForCollisions();
            updateCamera();
            
            battle.update(delta);
            
            generationRandThresh += 0.000001f;
            
//            if(Math.random() < generationRandThresh) {
//                battle.getEnemyArmy().add(Factory.enemyTankPool.obtain());
//                battle.getMyArmy().add(Factory.tankPool.obtain());
//            }
//            
//            if(Math.random() < generationRandThresh) {
//                Soldier s = (Factory.enemySoldierPool.obtain());
//                s.setParentArmy(battle.getEnemyArmy());
//                battle.getEnemyArmy().add(s);
//                
//                Soldier sa = (Factory.soldierPool.obtain());
//                sa.setParentArmy(battle.getMyArmy());
//                battle.getMyArmy().add(sa);
//            }
        }
        else {
            if(Assets.lsUpdateRender(context)) {
                Assets.getGameTextures();
                isLoadingAssets = false;
            }
        }
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        
        logger.log();
        
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
        
        toolbar.draw();
    }
    
    public void debugRender() {
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
//                battle.getPlayer().getBoundingRectangle().y, battle.getPlayer()
//                        .getBoundingRectangle().width, battle.getPlayer()
//                        .getBoundingRectangle().height);
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
//        App.debugRenderer.rect(battle.getMyArmy().getBase()
//                .getBoundingRectangle().x, battle.getMyArmy().getBase()
//                .getBoundingRectangle().y, battle.getMyArmy().getBase()
//                .getBoundingRectangle().width, battle.getMyArmy().getBase()
//                .getBoundingRectangle().height);
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
        background.setSize(levelWidth, height);
        toolbar.onResize(width, height);

        skyline[0] = new Sprite(Assets.getSkylines()[0]);
        skyline[0].setX(0);
        skyline[0].setY(road.getY() + road.getHeight());
        skyline[0].setSize(levelWidth, AppData.height * 1f);

        skyline[1] = new Sprite(Assets.getSkylines()[1]);
        skyline[1].setX(0);
        skyline[1].setY(road.getY() + road.getHeight());
        skyline[1].setSize(levelWidth, skyline[0].getHeight() * 0.9f);
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

    public static Plane getPlayer() {
        return battle.getPlayer();
    }
}
