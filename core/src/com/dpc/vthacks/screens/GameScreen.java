package com.dpc.vthacks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.dpc.vthacks.GameCamera;
import com.dpc.vthacks.data.Assets;
import com.dpc.vthacks.entities.Plane;
import com.dpc.vthacks.gameobject.GameObject;
import com.dpc.vthacks.input.InputSystem;

public class GameScreen implements Screen {
    private Array<GameObject> objects;
    private GameCamera camera;
    private Plane player;
    
    @Override
    public void show() {
        objects = new Array<GameObject>();
        player = new Plane(0, 0);
        
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

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
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
