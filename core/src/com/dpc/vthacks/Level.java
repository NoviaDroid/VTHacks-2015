package com.dpc.vthacks;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.objects.LayerManager;
import com.dpc.vthacks.objects.LayerManager.Layer;
import com.dpc.vthacks.screens.GameScreen;
import com.dpc.vthacks.zombie.Zombie;

public class Level {
    private Player player;
    private Array<Unit> playerArmy;
    private Array<Zombie> zombies;
    private LayerManager layers;
    private GameCamera gameCamera;
    private InputAdapter inputAdapter;
    private Vector3 input;
    private GameScreen context;
    private float spawnTime, spawnTimer;
    
    public Level(GameScreen context) {
        this.context = context;
        
        GameObject.setParentLevel(this);
        
        input = new Vector3();
        layers = new LayerManager(2);
        playerArmy = new Array<Unit>();
        zombies = new Array<Zombie>();
        
        initializeCamera();

        inputAdapter = new InputAdapter() {
            
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gameCamera.unproject(input.set(screenX, screenY, 0));
                
                if(player.isMovingLeft()) {
                    Assets.playExplosion();
                    player.setX(player.getX() + 1);
                    player.setY(player.getY() + 1);    
                }
                else {
                    Assets.playExplosion();
                    player.setX(player.getX() - 1);
                    player.setY(player.getY() + 1);
                }
                
                for(Zombie z : zombies) {
                    if(Math.abs(z.getX() - player.getX()) < 250) {
                        if(Math.abs(z.getY() - player.getY()) < 25) {
                            if(player.getX() <= z.getX() && !player.isMovingLeft()) {
                                player.attack(z);
                                System.out.println("attack right");   
                            }
                            else if(player.getX() >= z.getX() && player.isMovingLeft()) {
                                player.attack(z);
                                System.out.println("attack left");
                            }
                        }
                    }
                }
                
                return false;
            }
            
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.LEFT) {
                    gameCamera.position.x -= 25;
                }
                else if(keycode == Keys.RIGHT) {
                    gameCamera.position.x += 25;
                }
                
                gameCamera.update();
                
                return false;
            }
        };
    }
    
    private void updateCamera(float delta) {
        float y = gameCamera.position.y;
        
        if(player.getY() > gameCamera.position.y + (gameCamera.viewportHeight * 0.5f)) {
            y = player.getY();
        }
        
        gameCamera.lerp(player.getX(), y, delta);

        boolean wasClamped = false;
        
        // Clamp the camera's position
        if(gameCamera.position.x - (gameCamera.viewportWidth * 0.385f) < 0) {
            gameCamera.position.x = (gameCamera.viewportWidth * 0.385f);
            wasClamped = true;
        }
         if(gameCamera.position.x + (gameCamera.viewportWidth * 0.385f) > LevelProperties.WIDTH) {
            gameCamera.position.x = LevelProperties.WIDTH - (gameCamera.viewportWidth * 0.385f);
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
    }
    
    public void generateZombie() {
        Zombie z = Factory.zombiePool.obtain();
        
        if(Math.random() < 1f) {
            float x = Math.random() < 0.5f ? gameCamera.position.x - (gameCamera.viewportWidth * 0.5f):
                                             gameCamera.position.x + gameCamera.viewportWidth;
            
            float y = MathUtils.random(0, player.getGround().getY() + player.getGround().getHeight());
            
            z.setX(x);
            z.setY(y);
            
            if(player.getX() < x) {
                z.setVelX(z.getVelX());
            }

            zombies.add(z);
        }
    }
    
    public void render() {
        App.batch.setProjectionMatrix(gameCamera.combined);
        App.batch.begin();

        layers.updateAndRender();
        
        for(Unit u : playerArmy) {
            u.render();
        }
        
        for(Unit zombie : zombies) {
            zombie.render();
        }
        
        player.render();
        
        App.batch.end();
    }
    
    public void updateObjects(float delta) {
        for(Unit u : playerArmy) {
            u.update(delta);
        }
        
        for(Unit zombie : zombies) {
            zombie.update(delta);
            
            if(MathUtil.dst(zombie.getX(), zombie.getY(), player.getX(), player.getY()) <= 200) {
                ((Zombie) zombie).setCurrentTarget(player.getX(),
                                                   player.getY());
            }
            else {
                ((Zombie) zombie).resetPath();
            }
        }
        
        player.update(delta);
    }
    
    public void checkForCollisions() {
        for(Unit unit : playerArmy) {
            for(Unit zombie : zombies) {
                if(zombie.getBoundingRectangle().overlaps(unit.getBoundingRectangle())) {
                    unit.onCollision(zombie);
                    zombie.onCollision(unit);
                }
            }
        }
    }
    
    public void initializeCamera() {
        gameCamera = new GameCamera();
                
        float smallestY = 0;
        float smallestX = 0;
        
        for(Layer layer : layers.getLayers()) {
            for(GameObject o : layer.getObjects()) {
                if(o.getY() < smallestY) {
                    smallestY = o.getY();
                }
                
                if(o.getX() < smallestX) {
                    smallestX = o.getX();
                }
            }
        }
        
        gameCamera.zoom = 0.75f;
        
        gameCamera.position.set(smallestX + (gameCamera.viewportWidth * 0.385f), 
                                smallestY + (gameCamera.viewportHeight * 0.385f), 0);
        
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
    
    public float getSpawnTimer() {
        return spawnTimer;
    }
}
