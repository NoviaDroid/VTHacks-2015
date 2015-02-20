package com.dpc.vthacks;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.factories.Factory;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.infantry.Unit;
import com.dpc.vthacks.objects.LayerManager;
import com.dpc.vthacks.objects.LayerManager.Layer;
import com.dpc.vthacks.plane.Player;
import com.dpc.vthacks.zombie.Zombie;

public class Level {
    private Player player;
    private Array<Unit> playerArmy;
    private Array<Zombie> zombies;
    private LayerManager layers;
    private GameCamera gameCamera;
    private InputAdapter inputAdapter;
    
    public Level() {
        layers = new LayerManager(2);
        playerArmy = new Array<Unit>();
        zombies = new Array<Zombie>();
        player = Factory.createPlayer();
        
        initializeCamera();
        
        inputAdapter = new InputAdapter() {
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
    
    private void updateCamera() {
        gameCamera.position.set(player.getX(), gameCamera.position.y, 0);
        
        boolean wasClamped = false;
        
        // Clamp the camera's position
        if(gameCamera.position.x - (gameCamera.viewportWidth * 0.5f) < 0) {
            gameCamera.position.x = (gameCamera.viewportWidth * 0.5f);
            wasClamped = true;
        }
        else if(gameCamera.position.x + (gameCamera.viewportWidth * 0.5f) > LevelProperties.WIDTH) {
            gameCamera.position.x = LevelProperties.WIDTH - (gameCamera.viewportWidth * 0.5f);
            wasClamped = true;
        }

        gameCamera.update();
    }
    
    public void update(float delta) {
        updateObjects(delta);
        checkForCollisions();
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
        
        App.batch.end();
    }
    
    public void updateObjects(float delta) {
        for(Unit u : playerArmy) {
            u.update(delta);
        }
        
        for(Unit zombie : zombies) {
            zombie.update(delta);
        }
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
    
    public void onResize() {

    }
    
    public void initializeCamera() {
        int tmpW = AppData.width; 
        int tmpH = AppData.height;
        
        AppData.width = 800;
        AppData.height = 480;
        
        gameCamera = new GameCamera();
        
        AppData.width =  tmpW;
        AppData.height = tmpH;
        
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
        
        gameCamera.position.set(smallestX + (gameCamera.viewportWidth * 0.5f), 
                                smallestY + (gameCamera.viewportHeight * 0.5f) , 0);
        gameCamera.update();
    }
    
    public void addLayer(LayerManager.Layer layer) {
        layers.addLayer(layer);
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
    
    public void setZombies(Array<Zombie> zombies) {
        this.zombies = zombies;
    }
    
    public void addUnit(Unit u) {
        playerArmy.add(u);
    }
    
    public void removeUnit(Unit u) {
        playerArmy.removeValue(u, false);
    }
}
