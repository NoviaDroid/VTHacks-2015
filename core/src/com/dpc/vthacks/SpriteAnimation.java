package com.dpc.vthacks;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class SpriteAnimation implements Disposable {
    private Animation animation;
    private AtlasRegion[] frames;
    private final float FRAME_TIME;
    private float time;
    
    public SpriteAnimation(FileHandle file, String baseFrameName, int numberOfFrames, float frameTime) {
        this.FRAME_TIME = frameTime;
        TextureAtlas atlas = new TextureAtlas(file);
        
        frames = new AtlasRegion[numberOfFrames];
        
        for(int i = 0; i < numberOfFrames; i++) {
            frames[i] = atlas.findRegion(baseFrameName + (i + 1));
        }

        animation = new Animation(FRAME_TIME, frames);
    }
    
    public SpriteAnimation(AtlasRegion[] frames, float frameTime) {
        this.frames = frames;
        this.FRAME_TIME = frameTime;
        
        animation = new Animation(FRAME_TIME, frames);
    }
    
    public TextureRegion update(float delta) {
        time += delta;
        
        return animation.getKeyFrame(time, true);
    }
    
    public void render(SpriteBatch batch, float delta) throws Exception {
        time += delta;
        batch.draw(animation.getKeyFrame(time, true), 50, 50);
        throw new Exception("not implemented completely lol");
    }
    
    public float getStateTime() {
        return time;
    }
    
    public Animation getAnimation() {
        return animation;
    }
    
    @Override
    public void dispose() {
        for(TextureRegion t : frames) {
            t.getTexture().dispose();
        }
    }

}