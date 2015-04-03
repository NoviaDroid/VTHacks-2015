package com.dpc.vthacks.level;

import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.AndroidCamera;
import com.dpc.vthacks.App;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.objects.AmmoCrate;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.weapons.Gun;
import com.dpc.vthacks.zombie.Zombie;
import com.dpc.vthacks.zombie.ZombieSegment;

public abstract class Level {
    private Player player;
    private Array<AmmoCrate> ammoCrates;
    private Array<Unit> playerArmy;
    private Array<Zombie> zombies;
    private Array<Array<GameObject>> layers;
    private Array<GameObject> objectDrawOrder;
    private AndroidCamera gameCamera;
    private InputAdapter inputAdapter;
    private GestureDetector gestureDetector;
    private Vector3 input;
    private GameScreen context;
    private boolean active = true;
    private boolean enabled = false;
    private float spawnTime, spawnTimer;
    private float origCameraZoom;
    private float AMMO_CRATE_SPAWN_TIME = 0.001f;
    private static final float MAX_ZOOM = 0.35f; // Most that can be zoomed in
    private static final float ZOOM_STEP = 0.05f; // How much zoom to add
    private boolean fingerDown;
    
    public Level(final GameScreen context) {
        this.context = context;
        
        input = new Vector3();
        layers = new Array<Array<GameObject>>();
        playerArmy = new Array<Unit>();
        zombies = new Array<Zombie>();
        ammoCrates = new Array<AmmoCrate>();
        gameCamera = new AndroidCamera(AppData.TARGET_WIDTH, AppData.TARGET_HEIGHT);
        objectDrawOrder = new Array<GameObject>();
        
        initializeCamera();

        GestureListener l = new GestureListener() {
            
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                fingerDown = true;
                
                gameCamera.unproject(input.set(x, y, 0));
                
                firePlayerWeapon();
                
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
               
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                
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
            
        };
        
        gestureDetector = new GestureDetector(l);
        
        inputAdapter = new InputAdapter() {

            @Override
            public boolean scrolled(int amount) {
                // Make sure the amount of zoom is valid
                if(gameCamera.zoom + ZOOM_STEP > origCameraZoom && amount > 0) {
                    return false;
                }
  
                // Actual zooming
                if(amount > 0) {
                    gameCamera.zoom += ZOOM_STEP;
                }
                else if(gameCamera.zoom - ZOOM_STEP > MAX_ZOOM){
                    gameCamera.zoom -= ZOOM_STEP;
                }
                
                // Reposition the camera
                repositionCamera();

                gameCamera.update();
                
                
                return false;
            }
            
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                fingerDown = false;
                
                return super.touchUp(screenX, screenY, pointer, button);
            }
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
  
                return true;
            }
        };
    }
    
    public void repositionCamera() {
        gameCamera.position.set(player.getX() - (gameCamera.viewportWidth * 0f), 
                               gameCamera.viewportHeight * gameCamera.zoom * 0.5f, 0);
    }
    
    /**
     * Calculates the damage factor of a zombie segment
     * based on where the player's gun is in relation to other zombies
     * and fires the player's gun
     */
    private void firePlayerWeapon() {
        // Mock
        player.fireWeapon(null, 0);
        
        // Calculate damage on a zombie
        for (Zombie z : zombies) {

            // Check if the player is in range of the zombie
            if (Math.abs(z.getX() - player.getX()) < 250) {
                if (Math.abs(z.getY() - player.getY()) < 100) {
                    if (player.getX() <= z.getX()
                            && !player.isMovingLeft()
                            || player.getX() >= z.getX()
                            && player.isMovingLeft()) {
                        
                        // Assume
                        float segDmg = ((ZombieProperties) z.getProperties())
                                                            .getSegments()[0]
                                                            .damageFactor;

                        // Grab the segments of the zombie
                        ZombieSegment seg = null;
                        int len = ((ZombieProperties) z.getProperties())
                                                       .getSegments().length;

                        
                        for (int i = 0; i < len; i++) {
                            seg = ((ZombieProperties) z.getProperties())
                                                       .getSegments()[i];

                            // Find out if the current segment is the right one
                            if (seg.bounds.contains(seg.bounds.x + 1,
                                                    player.getY() + player.getCurrentFrame()
                                                                          .getAnchorOffsetY())) {
                                segDmg = seg.damageFactor;
                            }
                        }
                        
                        player.attack(z, segDmg);
                        
                        return;
                    }
                }
            }
        }
    }
    
    public abstract void loadLevels();
    
    public void setGameCamera(AndroidCamera gameCamera) {
        this.gameCamera = gameCamera;
    }
    
    public void setUnitsVisible(boolean b) {
        if(!b) {
            zombies.clear();
            ammoCrates.clear();
            playerArmy.clear();
            player.setVisible(false);
        }
    }
    
    /**
     * Resets the level to default state
     */
    public void reset() {
        active = true;
        getContext().getToolbar().setMoney(0);
        getContext().getToolbar().getStage().cancelTouchFocus();
        player.reset();
        spawnTimer = 0;
        ammoCrates.clear();
        zombies.clear();
        playerArmy.clear();
        initializeCamera();
        
        playerArmy = new Array<Unit>();
        ammoCrates = new Array<AmmoCrate>();
        zombies = new Array<Zombie>();
        objectDrawOrder = new Array<GameObject>();
        
        objectDrawOrder.add(player);
    }
   
    public void onGameOver() {
        Factory.zombiePool.clear();
        Factory.ammoCratePool.clear();
        
        zombies.clear();
        objectDrawOrder.clear();
    }
    
    public abstract void openGameOverDialog();
    
    /**
     * Called from game toolbar as soon as it's stage is done with it's actions
     */
    public Dialog getGameOverDialog() {
        Dialog dialog = new Dialog("game over!", Assets.uiSkin);
        
        return dialog;
    }
    
    private void updateCamera(float delta) {
        float y = gameCamera.position.y;
        
        // Calculate the camera Y
        if(player.getY() > gameCamera.position.y + (gameCamera.viewportHeight * gameCamera.zoom * 0.5f)) {
            y = player.getY();
        }

        gameCamera.lerp(player.getX(), y, delta);

        boolean wasClamped = false;
        
        // Clamp the camera's position
        if(gameCamera.position.x - (gameCamera.viewportWidth * gameCamera.zoom * 0.5f) < 0) {
            gameCamera.position.x = (gameCamera.viewportWidth * gameCamera.zoom * 0.5f);
            wasClamped = true;
        }
         if(gameCamera.position.x + (gameCamera.viewportWidth * gameCamera.zoom * 0.5f) > LevelProperties.WIDTH) {
            gameCamera.position.x = LevelProperties.WIDTH - (gameCamera.viewportWidth * gameCamera.zoom * 0.5f);
            wasClamped = true;
        }
         
         if(!wasClamped && active) {
             scrollBackgrounds(GameScreen.getJoystickPercentX() * 0.5f, 
                               GameScreen.getJoystickPercentY() * 0.5f);
         }

        gameCamera.update();
    }
    
    private void scrollBackgrounds(float amX, float amY) {
        for(Array<GameObject> sub : layers) {
            for(GameObject layer : sub) {
                if(layer.isScrollable()) {
                    layer.scroll(amX, amY);
                }
            }
        }
    }
    
    public void update(float delta) {
        updateObjects(delta);
        checkForCollisions();
        updateCamera(delta);
        
        if(enabled) {
            generateAmmoCrate();
            zombieGenerator(delta);
      
            objectDrawOrder.sort(new Comparator<GameObject>() {

                @Override
                public int compare(GameObject o1, GameObject o2) {
                    if (o1.getY() > o2.getY()) {
                        return -1;
                    }

                    if (o1.getY() == o2.getY()) {
                        return 0;
                    }

                    if (o1.getY() < o2.getY()) {
                        return 1;
                    }

                    return 0;
                }

            });
        }
        
        if(player.getCurrentWeapon() instanceof Gun) {
            if(fingerDown && ((Gun) player.getCurrentWeapon()).isFullAuto() 
               && player.getCurrentWeapon().getAmmo() > 0) {
                firePlayerWeapon();
            }
        }
    }
    
    private void zombieGenerator(float delta) {  
        spawnTimer += delta;
  
        if(spawnTimer >= spawnTime) {
            spawnTimer = 0;
            generateZombie();
        }
    }
    
    private void generateAmmoCrate() {
        // Possibly generate an ammo crate
        if(MathUtils.random() < AMMO_CRATE_SPAWN_TIME) {
            AmmoCrate c = Factory.ammoCratePool.obtain();
            
            c.setX(MathUtils.random(0, LevelProperties.WIDTH - c.getWidth()));
            c.setY(MathUtils.random(0, player.getGround().height));
            
            ammoCrates.add(c);
            objectDrawOrder.add(c);
        }
    }
    
    /**
     * Called by zombie instances when they die
     */
    public abstract void onZombieKilled();
    
    public void generateZombie() {
        Zombie z = Factory.zombiePool.obtain();

        // Calculate zombie x
        float x = Math.random() < 0.5f ? gameCamera.position.x
                - (gameCamera.viewportWidth * 0.5f) : gameCamera.position.x
                + gameCamera.viewportWidth;

        // Calculate zombie y 
        float y = MathUtils.random(0, player.getGround().getY()
                + player.getGround().getHeight());

        z.setX(x);
        z.setY(y);

        // Determine the destination and animation
        if (player.getX() >= x) {
            z.setFinalDestination(new Vector2(LevelProperties.WIDTH, 0));
            z.setState("walking-right");
        } else {
            z.setState("walking-left");
        }

        zombies.add(z);
        objectDrawOrder.add(z);
    }
    
    public void render() {
        App.batch.setProjectionMatrix(gameCamera.combined);
        App.batch.begin();

        for(Array<GameObject> sub : layers) {
            for(GameObject o : sub) {
                o.render();
            }
        }
        
        for(GameObject o : objectDrawOrder) {
            if(gameCamera.frustum.boundsInFrustum(o.getX(), 
                                                  o.getY(), 
                                                  0, 
                                                  o.getWidth() * 0.5f, 
                                                  o.getHeight() * 0.5f, 
                                                  0)) {
                o.render();
            }
        }

        App.batch.end();
    }

    public void remove(Zombie z) {
        objectDrawOrder.removeValue(z, false);
        zombies.removeValue(z, false);
    }
    
    public void remove(AmmoCrate c) {
        objectDrawOrder.removeValue(c, false);
        ammoCrates.removeValue(c, false);
    }
    
    private void updateObjects(float delta) {
        boolean noZombiesAttackingPlayer = true;
        
        for(Zombie zombie : zombies) {
            zombie.update(delta);
           
            if(zombie.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                noZombiesAttackingPlayer = false;
            }
        }
        
        if(noZombiesAttackingPlayer) {
            player.setSlowed(false);    
        }
        
        player.update(delta);
    }
    
    private void checkForCollisions() { 
        Iterator<AmmoCrate> iter = ammoCrates.iterator();
        AmmoCrate current = null;
        
        while(iter.hasNext()) {
            current = iter.next();
            
            // If the player obtains an ammo crate, refill ammo and remove it
            if(current.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                current.onPickedUp(player);
                iter.remove();
                objectDrawOrder.removeValue(current, false);
            }
        }
    }
    
    private void initializeCamera() {
        gameCamera.zoom = 0.45f;
        
        origCameraZoom = gameCamera.zoom;

        gameCamera.position.set(gameCamera.viewportWidth * gameCamera.zoom * 0.5f, 
                                gameCamera.viewportHeight * gameCamera.zoom * 0.5f, 0);
        
        gameCamera.update();
    }
    
    public abstract void dispose();
    
    public void setLayers(Array<Array<GameObject>> layers) {
        this.layers = layers;
    }
    
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }
    
    public Array<Zombie> getZombies() {
        return zombies;
    }
    
    public Array<Unit> getPlayerArmy() {
        return playerArmy;
    }

    public Player getPlayer() {
        return player;
    }
    
    public InputAdapter getInputAdapter() {
        return inputAdapter;
    }
    
    public void setPlayerArmy(Array<Unit> playerArmy) {
        this.playerArmy = playerArmy;
    }

    public void setFingerDown(boolean fingerDown) {
        this.fingerDown = fingerDown;
    }
    
    public boolean  isFingerDown () {
        return fingerDown;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void setZombies(Array<Zombie> zombies) {
        this.zombies = zombies;
    }
    
    public void addUnit(Unit u) {
        playerArmy.add(u);
    }
    
    public void removeUnit(Unit u) {
        playerArmy.removeValue(u, false);
    }
    
    public Array<GameObject> getObjectDrawOrder() {
        return objectDrawOrder;
    }
    
    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }
    
    public float getSpawnTime() {
        return spawnTime;
    }
    
    public void setSpawnTimer(float spawnTimer) {
        this.spawnTimer = spawnTimer;
    }
    
    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }
    
    public float getSpawnTimer() {
        return spawnTimer;
    }

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
    
    public Array<Array<GameObject>> getLayers() {
        return layers;
    }

    public AndroidCamera getGameCamera() {
        return gameCamera;
    }
    
    public GameScreen getContext() {
        return context;
    }
}
