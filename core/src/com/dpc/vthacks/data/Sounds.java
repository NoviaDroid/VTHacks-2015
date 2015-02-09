package com.dpc.vthacks.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class Sounds {
    public static Sound explosion;
    public static Sound shot;
    
    public static void load() {
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")); 
        shot = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));
    }
    
    public static void playShot() {
        shot.play(0.15f);
    }
    
    public static void dispose() {
        explosion.dispose();
        shot.dispose();
    }
}
