package com.dpc.vthacks;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.data.AppData;
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
    
    public Level(GameScreen context) {
        this.context = context;
        
        input = new Vector3();
        layers = new LayerManager(2);
        playerArmy = new Array<Unit>();
        zombies = new Array<Zombie>();
        
        initializeCamera();

        inputAdapter = new InputAdapter() {
            
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                gameCamera.unproject(input.set(screenX, screenY, 0));
                
                for(Zombie z : zombies) {
                    if(MathUtil.dst(input.x, input.y, z.getX(), z.getY()) <= 200f) {
                        z.setCurrentTarget(input.x, input.y);   
                    }
                    else {
                        z.setCurrentTarget(0, 50);
                    }
                }
                
                return super.mouseMoved(screenX, screenY);
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
    //    generateZombies();
    }
    
    public void generateZombies() {
        if(Math.random() < 0.12) {
            zombies.add(Factory.createZombie());
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
    
    public void onResize() {

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
}
