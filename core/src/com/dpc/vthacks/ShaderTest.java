package com.dpc.vthacks;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dpc.vthacks.data.Assets;

public class ShaderTest implements ApplicationListener {
    private ShaderProgram shader;
    private static SpriteBatch batch;
    
    @Override
    public void create() {
        ShaderProgram d = SpriteBatch.createDefaultShader();
        
        Assets.allocateTouchOnceScreen();
        Assets.allocateWeaponSelectionScreen();
        Assets.manager.finishLoading();
        
        batch = new SpriteBatch();

        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vert"), Gdx.files.internal("shaders/fragment.frag"));
       
        
        System.out.println(shader.isCompiled() ? "Shader compiled" : shader.getLog());
        
        //ShaderProgram.pedantic = false;
        batch.setShader(shader);
        
        shader.setUniformi("scanlinesEnabled", 1);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0.13f, 0.17f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        System.out.println(shader.getUniformLocation("scanlinesEnabled"));
        
       // batch.draw(Assets.touchOnceScreenBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        batch.end();
        
        batch.begin();
        
        batch.draw(Assets.playerIcon, 150, 150, 250, 250);
        
        batch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Assets.deallocateTouchOnceScreen();
        Assets.deallocateWeaponSelectionScreen();
        batch.dispose();
        shader.dispose();
    }
    
}
