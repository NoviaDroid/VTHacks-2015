package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class Sounds {
    private static Sound explosion;
    private static Music shot;
    private static float shotTimer;
    
    public static void load() {
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")); 
        shot = Gdx.audio.newMusic(Gdx.files.internal("sounds/shot.wav"));
    }
    
    public static void playExplosion() {
        explosion.play();
    }
    
    public static void playShot() {
        shot.setVolume((float) Math.random());
        
        if(!shot.isPlaying()) {
            shot.play();
        }
        
        shotTimer += Gdx.graphics.getDeltaTime();
        
        if(shotTimer >= 0.75f) {
            shot.stop();
            shotTimer = 0;
        }
    }
    
    public static void dispose() {
        explosion.dispose();
        shot.dispose();
    }
}
