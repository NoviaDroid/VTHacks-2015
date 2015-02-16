package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class Sounds {
    private static Sound explosion;
    private static Music shot;
    private static Music pressDown, pressUp;
    private static float shotTimer;
    
    public static void load() {
      // pressDown = Gdx.audio.newMusic(Gdx.files.internal("sounds/pressDown.wav")); 
       // pressUp = Gdx.audio.newMusic(Gdx.files.internal("sounds/pressUp.wav")); 
       // explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")); 
       // shot = Gdx.audio.newMusic(Gdx.files.internal("sounds/shot.wav"));
    }
    
    public static void playExplosion() {
    //    explosion.play();
    }
    
    public static void playPressDown() {
       // if(!pressDown.isPlaying()) {
       //     pressDown.play();
       // }
    }
    
    public static void playPressUp() {
//        if(!pressUp.isPlaying()) {
//            pressUp.play();
//        }
    }
    
    public static void playShot() {
//        shot.setVolume((float) Math.random() + 0.15f);
//        
//        if(!shot.isPlaying()) {
//            shot.play();
//        }
//        
//        shotTimer += Gdx.graphics.getDeltaTime();
//        
//        if(shotTimer >= 0.75f) {
//            shot.stop();
//            shotTimer = 0;
//        }
    }
    
    public static void dispose() {
//        explosion.dispose();
//        shot.dispose();
//        pressUp.dispose();
//        pressDown.dispose();
    }
}
