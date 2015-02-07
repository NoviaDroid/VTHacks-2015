package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.App;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.AppData;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.entities.Plane;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.input.InputSystem;

public class GameScreen implements Screen {
    public static final Vector2 gravity = new Vector2(0, -7);
    private Array<GameObject> objects;
    private GameCamera camera;
    private Plane player;
    
    @Override
    public void show() {
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Assets.loadGameTextures();
        
        camera = new GameCamera();
        objects = new Array<GameObject>();
        player = new Plane((AppData.width * 0.5f) - (Assets.plane.getRegionWidth() * 0.5f), 
                           (AppData.height * 0.5f) - (Assets.plane.getRegionHeight() * 0.5f));
        
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
    }

    public void update(float delta) {
        player.applyVel(gravity); // Apply gravity to the player
        player.update(delta);
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        
        App.batch.setProjectionMatrix(camera.combined);
        App.batch.begin();
        
        player.render();
        
        App.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        AppData.onResize(width, height);
        camera.resize(width, height);
    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {
        Assets.loadGameTextures();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Assets.unloadGameTextures();
        player.dispose();
    }

}
