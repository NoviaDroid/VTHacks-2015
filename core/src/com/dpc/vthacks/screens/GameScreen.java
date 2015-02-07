package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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
    private static final Vector2 gravity = new Vector2(0, -12);
    private Array<GameObject> objects;
    private GameCamera camera;
    private Plane player;

    @Override
    public void show() {
        AppData.onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Assets.loadGameTextures();
        
        camera = new GameCamera();
        objects = new Array<GameObject>();
        player = new Plane(AppData.width * 0.5f, AppData.height * 0.5f);
        
        InputSystem.initialize();
        InputSystem.register(player);
        
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                InputSystem.dispatchEvent(InputSystem.TOUCH_DOWN);
                
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
    }

}
