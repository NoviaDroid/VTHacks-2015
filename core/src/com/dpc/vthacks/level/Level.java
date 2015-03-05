package com.dpc.vthacks.level;

import java.util.Iterator;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.MathUtil;
import com.dpc.vthacks.Player;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.objects.AmmoCrate;
import com.dpc.vthacks.objects.LayerManager;
import com.dpc.vthacks.properties.ZombieProperties;
import com.dpc.vthacks.properties.ZombieSegment;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.zombie.Zombie;

public class Level {
    private Player player;
    private Array<AmmoCrate> ammoCrates;
    private Array<Unit> playerArmy;
    private Array<Zombie> zombies;
    private LayerManager layers;
    private GameCamera gameCamera;
    private InputAdapter inputAdapter;
    private GestureDetector gestureDetector;
    private Vector3 input;
    private GameScreen context;
    private float spawnTime, spawnTimer;
    private float origCameraZoom;
    private float AMMO_CRATE_SPAWN_TIME = 0.001f;
    private static final float MAX_ZOOM = 0.35f; // Most that can be zoomed in
    private static final float ZOOM_STEP = 0.05f; // How much zoom to add
    
    public Level(final GameScreen context) {
        this.context = context;
        
        GameObject.setParentLevel(this);
        
        input = new Vector3();
        layers = new LayerManager(2);
        playerArmy = new Array<Unit>();
        zombies = new Array<Zombie>();
        ammoCrates = new Array<AmmoCrate>();
        
        initializeCamera();
        
        LayerManager.setCamera(gameCamera);
        
        GestureListener l = new GestureListener() {

            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                                gameCamera.unproject(input.set(x, y, 0));
                                // Fire a shot, it may not hit
                                player.blindFire();
                 
                                // Calculate damage on a zombie
                                for(Zombie z : zombies) {
                                    
                                    // Check if the player is in range of the zombie
                                    if(Math.abs(z.getX() - player.getX()) < 250) {
                                        if(Math.abs(z.getY() - player.getY()) < 100) {
                                            if(player.getX() <= z.getX() && !player.isMovingLeft() ||
                                               player.getX() >= z.getX() && player.isMovingLeft()) {
                                                // Assume
                                                float segDmg = ((ZombieProperties) z.getProperties()).getSegments()[0].damageFactor;
                                                
                                                // Grab the segments of the zombie
                                                ZombieSegment seg = null;
                                                int len = ((ZombieProperties)z.getProperties()).getSegments().length;
                                                
                                                for(int i = 0; i < len; i++) {
                                                    seg = ((ZombieProperties) z.getProperties()).getSegments()[i];
                                                    
                                                    // Find out if the current seg is the right one
                                                    if(seg.bounds.contains(seg.bounds.x + 1,player.getPrimary().getY())) {
                                                        segDmg = seg.damageFactor;
                                                    }
                                                }
                                                
                                                // Attack the right zombie with the apropriate damage
                                                player.attack(z, segDmg);
                                                
                                                return false;
                                            }
                                        }
                                    }
                                }
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {

                
                
                return true;
            }

            @Override
            public boolean longPress(float x, float y) {
                
                return true;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                
                return true;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                
                return true;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                
                return true;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
//                inputAdapter.scrolled((int) (distance/initialDistance));
                return true;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1,
                    Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                
                return true;
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
                gameCamera.position.set(player.getX() - (gameCamera.viewportWidth * 0f), 
                                       gameCamera.viewportHeight * gameCamera.zoom * 0.5f, 0);

                gameCamera.update();
                
                
                return false;
            }
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer,
                    int button) {
                
                return true;
            }
        };
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
         
         if(!wasClamped) {
             scrollBackgrounds(GameScreen.getJoystickPercentX(), GameScreen.getJoystickPercentY());
         }
         else {
             scrollBackgrounds(0, 0);
         }
         
        gameCamera.update();
    }
    
    public void scrollBackgrounds(float amX, float amY) {
        for(LayerManager.Layer layer : layers.getLayers()) {
            if(layer.getName().equals("background")) {
                layer.setScrollX(amX);
                layer.setScrollY(amY);
                layer.setScrolling(true);
            }
        }
    }
    
    public void update(float delta) {
        updateObjects(delta);
        checkForCollisions();
        updateCamera(delta);
        
        spawnTimer += delta;
        
        if(spawnTimer >= spawnTime) {
            spawnTimer = 0;
            generateZombie();
        }
        
        // Possibly generate an ammo crate
        if(MathUtils.random() < AMMO_CRATE_SPAWN_TIME) {
            AmmoCrate c = Factory.ammoCratePool.obtain();
            
            c.setX(MathUtils.random(0, LevelProperties.WIDTH - c.getWidth()));
            c.setY(MathUtils.random(0, player.getGround().height));
            
            ammoCrates.add(c);
        }
    }
    
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
            z.setFlipped(true);
            z.setAnimation(Assets.zombieAnimations.get("walking-right"));
        } else {
            z.setAnimation(Assets.zombieAnimations.get("walking-left"));
        }

        zombies.add(z);
    }
    
    public void render() {
        App.batch.setProjectionMatrix(gameCamera.combined);
        App.batch.begin();

        layers.updateAndRender();
        
        if(player.isDrawingBehind()) {
            player.render();
        }
        
        for(Unit u : playerArmy) {
            if(gameCamera.frustum.pointInFrustum(u.getX(), u.getY(), 0)) {
                u.render();
            }
        }
        
        for(Unit zombie : zombies) {
            if(gameCamera.frustum.pointInFrustum(zombie.getX(), zombie.getY(), 0)) {
                zombie.render();
            }
        }

        for(AmmoCrate c : ammoCrates) {
            if(gameCamera.frustum.pointInFrustum(c.getX(), c.getY(), 0)) {
                c.render();
            }
        }
        
        if(!player.isDrawingBehind()) {
            player.render();
        }
        
        App.batch.end();
        
     //  debugRender();
    }
    
    public void debugRender() {
        App.debugRenderer.setProjectionMatrix(gameCamera.combined);
        App.debugRenderer.setColor(1, 0, 0, 1);
        App.debugRenderer.begin(ShapeType.Filled);

        App.debugRenderer.rect(player.getPrimary().getX(),
                               player.getPrimary().getY(),
                               150, 
                               150);
        
        App.debugRenderer.end();
        
        App.debugRenderer.setColor(1, 1, 1, 1);
        App.debugRenderer.begin(ShapeType.Line);
        
        for(Unit u : playerArmy) {
            App.debugRenderer.rect(u.getBoundingRectangle().x, 
                                   u.getBoundingRectangle().y,
                                   u.getBoundingRectangle().width,
                                   u.getBoundingRectangle().height);
        }
        
        for(Unit zombie : zombies) {
            App.debugRenderer.rect(zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getHeight());
            for(ZombieSegment s : ((ZombieProperties) zombie.getProperties()).getSegments()) {
                App.debugRenderer.rect(s.bounds.x, s.bounds.y, s.bounds.width, s.bounds.height);
            }
        }
        
        App.debugRenderer.end();
    }
    
    public void updateObjects(float delta) {
        for(Zombie zombie : zombies) {
            zombie.update(delta);
            
            for(Unit u : playerArmy) {
                u.update(delta);
                
                if(MathUtil.dst(zombie.getX(), zombie.getY(), u.getX(), u.getY()) <= 100) {
                    zombie.setCurrentTarget(u.getX(), u.getY());
                }
                else {
                    zombie.resetPath();
                }
            }
        }
        
        player.update(delta);
    }
    
    public void checkForCollisions() { 
        Iterator<AmmoCrate> iter = ammoCrates.iterator();
        AmmoCrate cur = null;
        
        while(iter.hasNext()) {
            cur = iter.next();
            
            // If the player obtains an ammo crate, refill ammo and remove it
            if(cur.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                player.refillAmmo();
                Factory.ammoCratePool.free(cur);
                Assets.outOfAmmo.play();
                iter.remove();
            }
        }
        
        for(Unit unit : playerArmy) {
            for(Zombie zombie : zombies) {
                if(zombie.getBoundingRectangle().overlaps(unit.getBoundingRectangle()) &&
                   !zombie.isAttacking()) {
                    zombie.setAttacking(true, unit);
                    zombie.attack();

                    if(unit.getProperties().getHealth() <= 0) {
                //        zombie.setAttacking(false, null);
                  //      zombie.resetPath();
                    }
                }
            }
        }
    }
    
    public GameCamera getGameCamera() {
        return gameCamera;
    }
    
    public GameScreen getContext() {
        return context;
    }
    
    public void initializeCamera() {
        gameCamera = new GameCamera();
        
        gameCamera.zoom = 0.55f;
        
        origCameraZoom = gameCamera.zoom;

        gameCamera.position.set(gameCamera.viewportWidth * gameCamera.zoom * 0.5f, 
                                gameCamera.viewportHeight * gameCamera.zoom * 0.5f, 0);
        
        gameCamera.update();
    }
    
    public void addLayer(LayerManager.Layer layer) {
        layers.addLayer(layer);
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
    @Override
    protected Object clone() throws CloneNotSupportedException {
        
        return super.clone();
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
}
